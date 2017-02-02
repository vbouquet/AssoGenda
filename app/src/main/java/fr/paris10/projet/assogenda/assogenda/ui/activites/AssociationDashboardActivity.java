package fr.paris10.projet.assogenda.assogenda.ui.activites;

import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

import fr.paris10.projet.assogenda.assogenda.R;
import fr.paris10.projet.assogenda.assogenda.model.Association;

public class AssociationDashboardActivity extends AppCompatActivity implements
        AssociationMainFragment.OnFragmentInteractionListener,
        CreateAssociationFragment.OnFragmentInteractionListener {

    private static final int SELECT_SINGLE_PICTURE = 101;
    public static final String IMAGE_TYPE = "image/*";
    private ImageView imagePreview;
    private DatabaseReference database;

    private String associationName;
    private String associationUniversity;
    private String associationDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_association_dashboard);
        Log.i(this.getClass().getCanonicalName(), "Entre dans onCreate");

        database = FirebaseDatabase.getInstance().getReference("association");
        Fragment associationMainFragment = new AssociationMainFragment();
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

    @Override
    public void onAssociationDashboardFragmentInteraction() {
        Log.i(this.getClass().getCanonicalName(),
                "Entre dans onAssociationDashboardFragmentInteraction");

        Fragment createAssociationFragment = new CreateAssociationFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction
                .replace(R.id.activity_association_dashboard_fragment_container,
                        createAssociationFragment)
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

        if (imagePreview == null) {
            Log.i(this.getClass().getCanonicalName(),
                    "Pas d'image");
        } else {
            Log.i(this.getClass().getCanonicalName(),
                    "Image");
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

        //Execute onActivityResult method
        Intent intent = new Intent();
        intent.setType(IMAGE_TYPE);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent,
                "Select picture"), SELECT_SINGLE_PICTURE);

        //Display image preview in association create form
        imagePreview = (ImageView) findViewById(R.id.fragment_create_association_logo_imageView);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(this.getClass().getCanonicalName(),
                "Entre dans onActivityResult");
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_SINGLE_PICTURE) {

                Uri selectedImageUri = data.getData();
                try {
                    imagePreview.setImageBitmap(new UserPicture(selectedImageUri, getContentResolver()).getBitmap());
                } catch (IOException e) {
                    Log.e(MainActivity.class.getSimpleName(), "Failed to load image", e);
                }
            }
        } else {
            Log.d(MainActivity.class.getSimpleName(), "Failed to get intent data, result code is " + resultCode);
        }
    }

    public Dialog onCreateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.fragment_create_association_form_validation_title)
                .setPositiveButton(R.string.fragment_create_association_form_validation_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                createAssociation();

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

    public void createAssociation() {
        associationName.trim().replaceAll(" +", " ");
        associationUniversity.trim().replaceAll(" +", " ");
        associationDescription.trim().replaceAll(" +", " ");

        Association association = new Association(associationName, associationUniversity, associationDescription);
        database.push().setValue(association);
    }
}
