package fr.paris10.projet.assogenda.assogenda.ui.activites;


import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import fr.paris10.projet.assogenda.assogenda.BuildConfig;
import fr.paris10.projet.assogenda.assogenda.R;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk=22)

/**
 * Created by wilpiron on 09/03/2017.
 */
public class CreateEventActivityTest {
    private CreateEventActivity activity;

    @Before
    public void setUp() throws Exception {
        FirebaseApp.initializeApp(RuntimeEnvironment.application.getApplicationContext());
        activity = Robolectric.buildActivity(CreateEventActivity.class).create().get();
    }

    @Test
    public void shouldNotBeNull() throws Exception {
        assertNotNull(activity);
        TextView textView = (TextView) activity.findViewById(R.id.activity_create_event_name);
        assertNotNull(textView);
        textView = (TextView) activity.findViewById(R.id.activity_create_event_description);
        assertNotNull(textView);
        textView = (TextView)  activity.findViewById(R.id.activity_create_event_seats_available);
        assertNotNull(textView);
        Button button = (Button) activity.findViewById(R.id.activity_create_event_submit);
        assertNotNull(button);
    }

    @After
    public void tearDown() throws Exception {
        activity = null;
    }

    @Test
    public void onCreateEvent() throws Exception {
        Button button = (Button) activity.findViewById(R.id.activity_create_event_submit);
        assertNotNull(button);
        assertTrue(button.performClick());
    }

}