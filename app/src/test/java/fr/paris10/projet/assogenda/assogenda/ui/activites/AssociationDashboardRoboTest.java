package fr.paris10.projet.assogenda.assogenda.ui.activites;

import android.widget.Button;

import com.google.firebase.FirebaseApp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import fr.paris10.projet.assogenda.assogenda.BuildConfig;
import fr.paris10.projet.assogenda.assogenda.R;
import fr.paris10.projet.assogenda.assogenda.model.Association;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)

public class AssociationDashboardRoboTest {
    private AssociationDashboardActivity activity;

    @Before
    public void setUp() throws Exception {
        FirebaseApp.initializeApp(RuntimeEnvironment.application.getApplicationContext());
        activity = Robolectric.buildActivity(AssociationDashboardActivity.class).create().get();
    }

    @Test
    public void shouldNotBeNull() throws Exception {
        assertNotNull(activity);
    }

    @Test
    public void shouldHaveMainFragment() throws Exception {
        assertNotNull(activity.getFragmentManager()
                .findFragmentById(R.id.activity_association_dashboard_fragment_container));
    }

    @Test
    public void onCreateAsso() throws Exception {
        Button button = (Button) activity.findViewById(R.id.fragment_association_main_button_create_association);
        assertNotNull(button);
        assertTrue(button.performClick());
    }

}