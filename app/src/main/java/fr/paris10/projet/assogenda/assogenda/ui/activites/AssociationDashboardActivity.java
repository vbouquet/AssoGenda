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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import fr.paris10.projet.assogenda.assogenda.R;
import fr.paris10.projet.assogenda.assogenda.daos.DAOAssociation;
import fr.paris10.projet.assogenda.assogenda.ui.fragment.AssociationMainFragment;
import fr.paris10.projet.assogenda.assogenda.ui.fragment.CreateAssociationFragment;
import fr.paris10.projet.assogenda.assogenda.util.association.UserPicture;

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
    public Uri filePath;

    public StorageReference mStorageRef;

    public String associationName;
    public String associationUniversity;
    public String associationDescription;
    public DAOAssociation daoAssociation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_association_dashboard);

        daoAssociation = DAOAssociation.getInstance();

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
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Switch to CreateAssociationFragment.
     */
    @Override
    public void onAssociationDashboardFragmentInteraction() {

        Fragment createAssociationFragment = CreateAssociationFragment.newInstance();
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

        EditText associationNameEdit = (EditText)
                findViewById(R.id.fragment_create_association_name);
        associationName = associationNameEdit.getText().toString();
        if (!daoAssociation.validateAssociationName(associationName)) {
            associationNameEdit.setError(
                    getString(R.string.fragment_create_association_form_validation_name));
        }

        EditText associationUniversiteEdit = (EditText)
                findViewById(R.id.fragment_create_association_university);
        associationUniversity = associationUniversiteEdit.getText().toString();
        if (!daoAssociation.validateAssociationUniversity(associationUniversity)) {
            associationUniversiteEdit.setError(
                    getString(R.string.fragment_create_association_form_validation_universite));
        }

        EditText associationDescriptionEdit = (EditText)
                findViewById(R.id.fragment_create_association_description);
        associationDescription = associationDescriptionEdit.getText().toString();
        if (!daoAssociation.validateAssociationDescription(associationDescription)) {
            associationDescriptionEdit.setError(
                    getString(R.string.fragment_create_association_form_validation_description));
        }

        if (daoAssociation.validateAssociation(associationName, associationUniversity, associationDescription)
                && associationNameEdit.getError() == null ) {
            Dialog formValidation = onCreateDialog();
            formValidation.show();
        }
    }

    @Override
    public void onAddImageAssociationFragmentInteraction() {

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

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_SINGLE_PICTURE && data != null) {

                //path to our image
                filePath = data.getData();

                try {

                    //Sets a Bitmap as the content of this ImageView, show imagePreview
                    imagePreview.setImageBitmap(new UserPicture(filePath, getContentResolver()).getBitmap());
                } catch (IOException e) {

                    Toast.makeText(getApplicationContext(), R.string.AssociationDashboardActivity_association_image_load_error, Toast.LENGTH_SHORT).show();
                    Log.e(MainActivity.class.getSimpleName(), "Failed to load image", e);
                }
            }
        } else {

            Log.i(MainActivity.class.getSimpleName(), "Failed to get intent data, result code is " + resultCode);
            imagePreview = null;
        }
    }

    /**
     * On positive button, add association to firebase.
     */
    public Dialog onCreateDialog() {

        String assoCreateFormat = getResources().getString(R.string.fragment_create_association_form_validation_title);
        String assoCreate = String.format(assoCreateFormat, associationName);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(assoCreate)
                .setPositiveButton(R.string.fragment_create_association_form_validation_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                try {
                                    createAssociation();
                                } catch (InterruptedException e) {
                                    Log.i(MainActivity.class.getSimpleName(), "" + e);
                                }

                                //Success toast
                                Toast.makeText(getApplicationContext(), R.string.AssociationDashboardActivity_association_creation_success, Toast.LENGTH_LONG).show();

                                //Skip upload if no image is set
                                if (imagePreview == null) {

                                    //Redirection to agenda_main_menu fragment
                                    Fragment associationMainFragment = AssociationMainFragment.newInstance();
                                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                    transaction
                                            .replace(R.id.activity_association_dashboard_fragment_container,
                                                    associationMainFragment)
                                            .commit();
                                }
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
    public void createAssociation() throws InterruptedException {

        String logo = null;

        //Delete unfortunate spaces : this "  test   test   " will become : "test test"
        associationName = associationName.trim().replaceAll(" +", " ");
        associationUniversity = associationUniversity.trim().replaceAll(" +", " ");
        associationDescription = associationDescription.trim().replaceAll(" +", " ");

        if (imagePreview != null) {
            logo = "images/" + associationName + "/logo.jpg";
            uploadImage();
        }

        daoAssociation.createAssociation(associationName, associationUniversity, associationDescription, logo);
    }

    /**
     * Upload association logo to firebase
     */
    public void uploadImage() {

        final ProgressDialog progressDialog = new ProgressDialog(AssociationDashboardActivity.this);
        progressDialog.setTitle(R.string.AssociationDashboardActivity_upload_logo);
        progressDialog.show();

        StorageReference storageReference = mStorageRef.child("images/" + associationName + "/logo.jpg");
        storageReference.putFile(filePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imagePreview = null;

                        //Redirection to agenda_main_menu fragment
                        Fragment associationMainFragment = AssociationMainFragment.newInstance();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction
                                .replace(R.id.activity_association_dashboard_fragment_container,
                                        associationMainFragment)
                                .addToBackStack(null)
                                .commit();

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
