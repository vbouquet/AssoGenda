package fr.paris10.projet.assogenda.assogenda.ui.activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import fr.paris10.projet.assogenda.assogenda.R;
import fr.paris10.projet.assogenda.assogenda.model.Event;

/**
 * Display incoming events.
 */
public class ListEventsActivity extends AppCompatActivity {
    private ListView listEvents;
    private ArrayList<HashMap<String, Object>> listValuesEvents = new ArrayList<>();
    private SimpleAdapter adapter;
    private List<Event> listeEvenements = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_events);
        loadEventInBackground();
    }

    public void launchEventPage() {
        listEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), EventInfosActivity.class);
                Event event = listeEvenements.get(position);
                intent.putExtra("eventUID", event.uid);
                startActivity(intent);
            }
        });
    }

    public Boolean convertToDate(String eventDate) {
        DateFormat dateFormatter = new SimpleDateFormat("kk:mm dd/MM/yyyy");
        Date start;
        Date today;
        Calendar c = Calendar.getInstance();
        try {
            start = dateFormatter.parse(eventDate);
            today = dateFormatter.parse(dateFormatter.format(c.getTime()));
            if (start.after(today)) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public Boolean compareDate(String event1, String event2) {
        DateFormat dateFormatter = new SimpleDateFormat("kk:mm dd/MM/yyyy");
        Date myEvent;
        Date eventNext;
        try {
            myEvent = dateFormatter.parse(event1);
            eventNext = dateFormatter.parse(event2);
            if (myEvent.before(eventNext)) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public void loadEventInBackground() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("events");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot e : dataSnapshot.getChildren()) {
                        Event event = e.getValue(Event.class);
                        if (convertToDate(event.start) || convertToDate(event.end)) {
                            event.uid = e.getKey();
                            listeEvenements.add(event);
                        }
                    }
                    for (Event event : listeEvenements) {
                        HashMap<String, Object> hashMapValuesEvent = new HashMap<>();
                        hashMapValuesEvent.put("nameEvent", event.name);
                        if (event.association == null)
                            hashMapValuesEvent.put("association", "Nom asso");
                        else
                            hashMapValuesEvent.put("association", event.association);
                        hashMapValuesEvent.put("dateEventBegin", event.start);
                        hashMapValuesEvent.put("dateEventEnd", event.end);
                        hashMapValuesEvent.put("locationEvent", event.location);
                        hashMapValuesEvent.put("tagsEvent", event.type);
                        listValuesEvents.add(hashMapValuesEvent);
                    }
                    String[] from = new String[]{"nameEvent", "association", "dateEventBegin", "dateEventEnd", "locationEvent", "tagsEvent"};
                    int[] to = new int[]{R.id.content_list_events_name_event,
                            R.id.content_list_events_name_association,
                            R.id.content_list_events_date_event_begin,
                            R.id.content_list_events_date_event_end,
                            R.id.content_list_events_location_event,
                            R.id.content_list_events_tags_event};

                    listEvents = (ListView) findViewById(R.id.activity_list_events_list);
                    adapter = new SimpleAdapter(ListEventsActivity.this, listValuesEvents, R.layout.content_list_events, from, to);
                    listEvents.setAdapter(adapter);
                    launchEventPage();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Error : ", "onCancelled", databaseError.toException());
            }
        });
    }
}
