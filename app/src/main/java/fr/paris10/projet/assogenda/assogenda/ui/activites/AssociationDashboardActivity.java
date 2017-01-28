package fr.paris10.projet.assogenda.assogenda.ui.activites;

import android.app.Fragment;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import fr.paris10.projet.assogenda.assogenda.R;

public class AssociationDashboardActivity extends AppCompatActivity implements
        AssociationMainFragment.OnFragmentInteractionListener,
        CreateAssociationFragment.OnFragmentInteractionListener {

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

    @Override
    public void onCreateAssociationFragmentInteraction() {

    }
}
