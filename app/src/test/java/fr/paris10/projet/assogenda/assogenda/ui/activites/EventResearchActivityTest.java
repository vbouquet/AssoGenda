package fr.paris10.projet.assogenda.assogenda.ui.activites;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
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

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)
public class EventResearchActivityTest {

    private EventResearchActivity eventResearchActivity;

    @Before
    public void setUp() throws Exception {
        FirebaseApp.initializeApp(RuntimeEnvironment.application.getApplicationContext());
        eventResearchActivity = Robolectric.buildActivity(EventResearchActivity.class).create().get();
    }

    @After
    public void tearDown() throws Exception {
        eventResearchActivity = null;
    }

    @Test
    public void shouldNotBeNull() throws Exception {
        assertNotNull(eventResearchActivity);
        TextView textView = (TextView) eventResearchActivity.findViewById(R.id.activity_event_research_title_info);
        assertNotNull(textView);
        EditText editText = (EditText) eventResearchActivity.findViewById(R.id.activity_event_research_start_date);
        assertNotNull(editText);
        Spinner spinner = (Spinner) eventResearchActivity.findViewById(R.id.activity_event_research_eventype_spinner);
        assertNotNull(spinner);
        ListView listView = (ListView) eventResearchActivity.findViewById(R.id.activity_event_research_list_view);
        assertNotNull(listView);
    }

    @Test
    public void onSearchEvent() throws Exception {
        Button button = (Button) eventResearchActivity.findViewById(R.id.activity_event_research_button);
        assertNotNull(button);
        assertTrue(button.performClick());
    }
}