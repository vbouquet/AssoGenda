package fr.paris10.projet.assogenda.assogenda.ui.activites;

import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import fr.paris10.projet.assogenda.assogenda.R;
import fr.paris10.projet.assogenda.assogenda.model.Association;

/**
 * Activity for associations management.
 * Associations are managed via fragments.
 */
public class AssociationDashboardActivity extends AppCompatActivity implements
        AssociationMainFragment.OnFragmentInteractionListener,
        CreateAssociationFragment.OnFragmentInteractionListener {

    public static final int SELECT_SINGLE_PICTURE = 101;
    public static final String IMAGE_TYPE = "image/*";
    public ImageView imagePreview;
    public DatabaseReference database;
    public StorageReference mStorageRef;
    public Uri filePath;
    public String associationName;
    public String associationUniversity;
    public String associationDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_association_dashboard);
        Log.i(this.getClass().getCanonicalName(), "Entre dans onCreate");

        //create firebase database association reference
        database = FirebaseDatabase.getInstance().getReference("association");

        //create firebase storage reference
        mStorageRef = FirebaseStorage.getInstance().getReference();

        //Main fragment which allow us to manage other fragments
        Fragment associationMainFragment = AssociationMainFragment.newInstance();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction
                .replace(R.id.activity_association_dashboard_fragment_container,
                        associationMainFragment)
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(this.getClass().getCanonicalName(), "Entre dans onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(this.getClass().getCanonicalName(), "Entre dans onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(this.getClass().getCanonicalName(), "Entre dans onDestroy");
    }

    /**
     * Switch to CreateAssociationFragment.
     */
    @Override
    public void onAssociationDashboardFragmentInteraction() {
        Log.i(this.getClass().getCanonicalName(),
                "Entre dans onAssociationDashboardFragmentInteraction");

        Fragment createAssociationFragment = new CreateAssociationFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction
                .replace(R.id.activity_association_dashboard_fragment_container,
                        createAssociationFragment)
                .addToBackStack(null)
                .commit();
    }

    /**
     * Form validator for association creation.
     */
    @Override
    public void onCreateAssociationFragmentInteraction() {
        Log.i(this.getClass().getCanonicalName(),
                "Entre dans onCreateAssociationFragmentInteraction");
        boolean validate = true;

        EditText associationNameEdit = (EditText)
                findViewById(R.id.fragment_create_association_name);
        associationName = associationNameEdit.getText().toString();
        if (associationName.length() == 0) {
            associationNameEdit.setError(
                    getString(R.string.fragment_create_association_form_validation_name));
            validate = false;
        }

        EditText associationUniversiteEdit = (EditText)
                findViewById(R.id.fragment_create_association_university);
        associationUniversity = associationUniversiteEdit.getText().toString();
        if (associationUniversity.length() == 0) {
            associationUniversiteEdit.setError(
                    getString(R.string.fragment_create_association_form_validation_universite));
            validate = false;
        }

        EditText associationDescriptionEdit = (EditText)
                findViewById(R.id.fragment_create_association_description);
        associationDescription = associationDescriptionEdit.getText().toString();
        if (associationDescription.length() == 0) {
            associationDescriptionEdit.setError(
                    getString(R.string.fragment_create_association_form_validation_description));
            validate = false;
        }

        if (validate) {
            Dialog formValidation = onCreateDialog();
            formValidation.show();
        }
    }

    @Override
    public void onAddImageAssociationFragmentInteraction() {
        Log.i(this.getClass().getCanonicalName(),
                "Entre dans onAddImageAssociationFragmentInteraction");

        //open picture selection
        Intent intent = new Intent();
        intent.setType(IMAGE_TYPE);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent,
                "Select picture"), SELECT_SINGLE_PICTURE);

        //Initializing our imageView
        imagePreview = (ImageView) findViewById(R.id.fragment_create_association_logo_imageView);
    }

    /**
     * Attach selected image to imagePreview.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(this.getClass().getCanonicalName(),
                "Entre dans onActivityResult");
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_SINGLE_PICTURE && data != null) {

                //path to our image
                filePath = data.getData();

                Log.i(this.getClass().getCanonicalName(), "Path : " + data.getData().getPath());

                try {

                    //Sets a Bitmap as the content of this ImageView, show imagePreview
                    imagePreview.setImageBitmap(new UserPicture(filePath, getContentResolver()).getBitmap());
                } catch (IOException e) {

                    Toast.makeText(getApplicationContext(), R.string.AssociationDashboardActivity_association_image_load_error, Toast.LENGTH_SHORT).show();
                    Log.e(MainActivity.class.getSimpleName(), "Failed to load image", e);
                }
            }
        } else {

            Log.d(MainActivity.class.getSimpleName(), "Failed to get intent data, result code is " + resultCode);
            imagePreview = null;
        }
    }

    /**
     * On positive button, add association to firebase.
     */
    public Dialog onCreateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.fragment_create_association_form_validation_title)
                .setPositiveButton(R.string.fragment_create_association_form_validation_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                createAssociation();

                                //Success toast
                                Toast.makeText(getApplicationContext(), R.string.AssociationDashboardActivity_association_creation_success, Toast.LENGTH_LONG).show();

                                //Redirection to main fragment
                                Fragment associationMainFragment = new AssociationMainFragment();
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                transaction
                                        .replace(R.id.activity_association_dashboard_fragment_container,
                                                associationMainFragment)
                                        .commit();
                            }
                        })
                .setNegativeButton(R.string.fragment_create_association_form_validation_cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
        return builder.create();
    }

    /**
     * Create association on firebase database and association logo on firebase storage.
     */
    public void createAssociation() {

        //Delete unfortunate spaces : this "  test   test   " will become : "test test"
        associationName = associationName.trim().replaceAll(" +", " ");
        associationUniversity = associationUniversity.trim().replaceAll(" +", " ");
        associationDescription = associationDescription.trim().replaceAll(" +", " ");

        //Upload only if an image is set
        if (imagePreview != null) {
            uploadFile();
        }

        Association association = new Association(associationName, associationUniversity, associationDescription);
        database.push().setValue(association);
    }

    /**
     * Upload association logo to firebase
     * //TODO only images can be uploaded (not random files). Create FileValidator class, voir https://www.mkyong.com/regular-expressions/how-to-validate-image-file-extension-with-regular-expression/
     * //TODO manage image names (actually for all association only logo.jpg is created)
     */
    public void uploadFile() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(R.string.AssociationDashboardActivity_upload_logo);
        progressDialog.show();

        //reference creation (where our image will be stored)
        StorageReference riversRef = mStorageRef.child("images/logo/logo.jpg");
        riversRef.putFile(filePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imagePreview = null;
                        progressDialog.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        progressDialog.dismiss();

                        //Error toast
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        //calculating progress percentage
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                        //displaying percentage in progress dialog
                        progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                    }
                });
    }
}
