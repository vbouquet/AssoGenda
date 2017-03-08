package fr.paris10.projet.assogenda.assogenda.ui.activites;

import android.content.Intent;
import android.widget.Button;

import com.google.firebase.FirebaseApp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import fr.paris10.projet.assogenda.assogenda.BuildConfig;
import fr.paris10.projet.assogenda.assogenda.R;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)

public class AgendaActivityRoboTest {

    private AgendaActivity agendaActivity;

    @Before
    public void setUp() throws Exception {
        FirebaseApp.initializeApp(RuntimeEnvironment.application.getApplicationContext());
        agendaActivity = Robolectric.buildActivity(AgendaActivity.class).create().get();
    }

    @After
    public void tearDown() throws Exception {
        agendaActivity = null;
    }

    @Test
    public void shouldNotBeNull() throws Exception {
        assertNotNull(agendaActivity);
    }

    @Test
    public void onAgendaGeneration() throws Exception {
        Button button = (Button) agendaActivity.findViewById(R.id.activity_agenda_generate_button);
        assertNotNull(button);
        assertTrue(button.performClick());
    }

    @Test
    public void onActivityChange() throws Exception {
        Button button = (Button) agendaActivity.findViewById(R.id.activity_agenda_generate_button);
        assertNotNull(button);
        ShadowActivity shadowActivity = shadowOf(agendaActivity);
        button.performClick();
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        assertThat(startedIntent.getComponent().getClassName(),
                equalTo(AgendaGeneratorActivity.class.getName()));
    }
}