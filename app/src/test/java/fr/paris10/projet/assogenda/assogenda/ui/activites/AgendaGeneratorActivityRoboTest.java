package fr.paris10.projet.assogenda.assogenda.ui.activites;

import android.view.MenuItem;

import com.google.firebase.FirebaseApp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.fakes.RoboMenuItem;
import org.robolectric.shadows.ShadowActivity;

import fr.paris10.projet.assogenda.assogenda.BuildConfig;
import fr.paris10.projet.assogenda.assogenda.R;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)

public class AgendaGeneratorActivityRoboTest {

    private AgendaGeneratorActivity agendaGeneratorActivity;

    @Before
    public void setUp() throws Exception {
        FirebaseApp.initializeApp(RuntimeEnvironment.application.getApplicationContext());
        agendaGeneratorActivity = Robolectric.buildActivity(AgendaGeneratorActivity.class).create().get();
    }

    @After
    public void tearDown() throws Exception {
        agendaGeneratorActivity = null;
    }

    @Test
    public void itemsMenuNotNull() {
        MenuItem menuItem = new RoboMenuItem(R.id.action_today);
        assertTrue(agendaGeneratorActivity.onOptionsItemSelected(menuItem));

        menuItem = new RoboMenuItem(R.id.action_day_view);
        assertTrue(agendaGeneratorActivity.onOptionsItemSelected(menuItem));

        menuItem = new RoboMenuItem(R.id.action_three_day_view);
        assertTrue(agendaGeneratorActivity.onOptionsItemSelected(menuItem));

        menuItem = new RoboMenuItem(R.id.action_week_view);
        assertTrue(agendaGeneratorActivity.onOptionsItemSelected(menuItem));
    }
}