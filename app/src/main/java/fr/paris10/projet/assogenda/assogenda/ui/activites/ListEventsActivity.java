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
import fr.paris10.projet.assogenda.assogenda.model.Association;
import fr.paris10.projet.assogenda.assogenda.model.Event;

public class ListEventsActivity extends AppCompatActivity {
    private ListView listEvents;
    private ArrayList<HashMap<String, Object>> listValuesEvents = new ArrayList<>();
    private SimpleAdapter adapter;
    private List<Event> listeEvenements = new ArrayList<>();
    private List<Event> listEventSort = new ArrayList<>();
    private HashMap<String, String> listAssociation= new HashMap<>();

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
                Event event = listEventSort.get(position);

                intent.putExtra("eventUID", event.uid);
                intent.putExtra("eventName", event.name);
                intent.putExtra("eventEndDate", event.end);
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
        DatabaseReference references = FirebaseDatabase.getInstance().getReference("association");
        references.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataS) {
                if (dataS.exists()) {
                    for (DataSnapshot e : dataS.getChildren()) {
                        Association a = e.getValue(Association.class);
                        a.id = e.getKey();
                        if (listAssociation.get(a.id) == null)
                            listAssociation.put(a.id, a.name);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("events");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int tailleList;
                int nbEvent;
                int eventRestant;

                if (dataSnapshot.exists()) {
                    for (DataSnapshot e : dataSnapshot.getChildren()) {
                        Event event = e.getValue(Event.class);
                        event.uid = e.getKey();
                        if (convertToDate(event.start) || convertToDate(event.end)) {
                            listeEvenements.add(event);
                        }
                    }
                    eventRestant = listeEvenements.size();
                    while (eventRestant > 0) {
                        tailleList = listeEvenements.size();
                        for (int i = 0; i < tailleList; i++) {
                            nbEvent = 1;
                            for (int j = 0; j < tailleList; j++) {
                                if (i != j && compareDate(listeEvenements.get(i).start, listeEvenements.get(j).start)) {
                                    nbEvent++;
                                }
                            }
                            if (nbEvent == eventRestant) {
                                listEventSort.add(listeEvenements.get(i));
                                eventRestant--;
                            }
                        }
                    }



                    for (Event event : listEventSort) {
                        HashMap<String, Object> hashMapValuesEvent = new HashMap<>();
                        hashMapValuesEvent.put("nameEvent", event.name);
                        hashMapValuesEvent.put("association", listAssociation.get(event.association));
                        hashMapValuesEvent.put("dateEventBegin", event.start);
                        hashMapValuesEvent.put("dateEventEnd", event.end);
                        hashMapValuesEvent.put("locationEvent", event.location);
                        hashMapValuesEvent.put("tagsEvent", event.type);
                        listValuesEvents.add(hashMapValuesEvent);
                    }
                    String[] from = new String[]{"nameEvent", "association", "dateEventBegin", "dateEventEnd", "locationEvent", "tagsEvent"};
                    int[] to = new int[]{R.id.content_list_events_name_event, R.id.content_list_events_name_association, R.id.content_list_events_date_event_begin, R.id.content_list_events_date_event_end, R.id.content_list_events_location_event, R.id.content_list_events_tags_event};

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
