package fr.paris10.projet.assogenda.assogenda.ui.activites;

import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import fr.paris10.projet.assogenda.assogenda.R;

public class AssociationDashboardActivity extends AppCompatActivity implements
        AssociationMainFragment.OnFragmentInteractionListener,
        CreateAssociationFragment.OnFragmentInteractionListener {

    public static final String IMAGE_TYPE = "image/*";
    private static final int SELECT_SINGLE_PICTURE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_association_dashboard);
        Log.i(this.getClass().getCanonicalName(), "Entre dans onCreate");

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
        String associationName = associationNameEdit.getText().toString();
        if (associationName.length() == 0) {
            associationNameEdit.setError(
                    getString(R.string.fragment_create_association_form_validation_name));
            validate = false;
        }

        EditText associationUniversiteEdit = (EditText)
                findViewById(R.id.fragment_create_association_university);
        String associationUniversite = associationUniversiteEdit.getText().toString();
        if (associationUniversite.length() == 0) {
            associationUniversiteEdit.setError(
                    getString(R.string.fragment_create_association_form_validation_universite));
            validate = false;
        }

        EditText associationDescriptionEdit = (EditText)
                findViewById(R.id.fragment_create_association_description);
        String associationDescription = associationDescriptionEdit.getText().toString();
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
        Intent intent = new Intent();
        intent.setType(IMAGE_TYPE);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent,
                "Select picture"), SELECT_SINGLE_PICTURE);
    }

    public Dialog onCreateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.fragment_create_association_form_validation_title)
                .setPositiveButton(R.string.fragment_create_association_form_validation_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                /* //TODO Faire connexion avec la base
                                associationName.trim().replaceAll(" +", " ");
                                associationUniversite.trim().replaceAll(" +", " ");
                                associationDescription.trim().replaceAll(" +", " ");
                                */

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
}
