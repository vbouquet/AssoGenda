package fr.paris10.projet.assogenda.assogenda.ui.activites;

import android.widget.ListView;
import android.widget.RelativeLayout;
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

import java.util.Calendar;

import fr.paris10.projet.assogenda.assogenda.BuildConfig;
import fr.paris10.projet.assogenda.assogenda.R;
import fr.paris10.projet.assogenda.assogenda.model.Event;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)
public class ListEventsActivityTest {

    private ListEventsActivity listEventsActivity;

    @Before
    public void setUp() throws Exception {
        FirebaseApp.initializeApp(RuntimeEnvironment.application.getApplicationContext());
        listEventsActivity = Robolectric.buildActivity(ListEventsActivity.class).create().get();
    }

    @After
    public void tearDown() throws Exception {
        listEventsActivity = null;
    }

    @Test
    public void shouldNotBeNull() throws Exception {
        assertNotNull(listEventsActivity);
        TextView textView = (TextView) listEventsActivity.findViewById(R.id.activity_list_events_title);
        assertNotNull(textView);
        ListView listView = (ListView) listEventsActivity.findViewById(R.id.activity_list_events_list);
        assertNotNull(listView);
        RelativeLayout relativeLayout = (RelativeLayout) listEventsActivity.findViewById(R.id.activity_list_events);
        assertNotNull(relativeLayout);
    }

    @Test
    public void compareDateTest() throws Exception {
        assertTrue(listEventsActivity.compareDate("10:00 05/05/2016", "10:00 20/05/2016"));
        assertTrue(listEventsActivity.compareDate("10:00 20/05/2016", "10:01 20/05/2016"));
        assertFalse(listEventsActivity.compareDate("10:01 20/05/2016", "10:00 20/05/2016"));
        assertFalse(listEventsActivity.compareDate("10:00 20/05/2016", "10:00 05/05/2016"));
    }

    @Test
    public void convertToDateTest() throws Exception {
        Calendar c = Calendar.getInstance();
        assertFalse(listEventsActivity.convertToDate(Event.dateFormatter.format(c.getTime())));
        assertFalse(listEventsActivity.convertToDate("10:00 05/05/2016"));
        c.add(Calendar.HOUR, 1);
        assertTrue(listEventsActivity.convertToDate(Event.dateFormatter.format(c.getTime())));
    }
}