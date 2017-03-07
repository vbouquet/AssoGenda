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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.paris10.projet.assogenda.assogenda.R;
import fr.paris10.projet.assogenda.assogenda.model.Event;

public class ListEventsActivity extends AppCompatActivity {
    private ListView listEvents;
    private ArrayList<HashMap<String,Object>> listValuesEvents = new ArrayList<>();
    private SimpleAdapter adapter;
    private List<Event> listeEvenements = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_events);
        loadEventInBackground();
    }

    public void launchEventPage(){
        listEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),EventInfosActivity.class);
                Event event = listeEvenements.get(position);
                //Log.i("TEST : ",""+event.uid);

                intent.putExtra("eventUID", event.uid);
                startActivity(intent);
            }
        });
    }

    public void loadEventInBackground() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("events");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot e : dataSnapshot.getChildren()) {
                        Event event = e.getValue(Event.class);
                        //Log.i("TEST KEY : ",""+e.getKey());
                        event.uid=e.getKey();
                        listeEvenements.add(event);
                        HashMap<String,Object> hashMapValuesEvent = new HashMap<>();
                        hashMapValuesEvent.put("nameEvent",event.name);
                        hashMapValuesEvent.put("association",event.association);
                        hashMapValuesEvent.put("dateEvent",event.start);
                        hashMapValuesEvent.put("locationEvent",event.location);
                        hashMapValuesEvent.put("tagsEvent",event.type);
                        listValuesEvents.add(hashMapValuesEvent);

                    }
                    String[] from = new String[] {"nameEvent","association","dateEvent","placeEvent","tagsEvent"};
                    int[] to = new int[] {R.id.content_list_events_name_event,R.id.content_list_events_name_association,R.id.content_list_events_date_event,R.id.content_list_events_location_event,R.id.content_list_events_tags_event};

                    listEvents = (ListView) findViewById(R.id.activity_list_events_list);
                    adapter = new SimpleAdapter(ListEventsActivity.this,listValuesEvents,R.layout.content_list_events,from,to);
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
