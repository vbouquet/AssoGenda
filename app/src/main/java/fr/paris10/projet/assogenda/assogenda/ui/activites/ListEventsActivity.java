package fr.paris10.projet.assogenda.assogenda.ui.activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import fr.paris10.projet.assogenda.assogenda.R;

public class ListEventsActivity extends AppCompatActivity {
    private ListView listEvents;
    private ArrayList<HashMap<String,Object>> listValuesEvents = new ArrayList<>();
    private SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_events);

        // Données fictives
        HashMap<String,Object> hashMapValuesEvent1 = new HashMap<>();
        hashMapValuesEvent1.put("nameEvent","Soirée Karaoké");
        hashMapValuesEvent1.put("association","EDEN Miage");
        hashMapValuesEvent1.put("dateEvent","03/03/2017 à 20:00");
        hashMapValuesEvent1.put("locationEvent","Châtelet");
        hashMapValuesEvent1.put("tagsEvent","Soirée / Karaoke / EDEN / Miage / Panda / roux / mouton");

        HashMap<String,Object> hashMapValuesEvent2 = new HashMap<>();
        hashMapValuesEvent2.put("nameEvent","Formation Git");
        hashMapValuesEvent2.put("association","JMC");
        hashMapValuesEvent2.put("dateEvent","06/03/2017 à 18:00");
        hashMapValuesEvent2.put("locationEvent","Salle 210b bat G");
        hashMapValuesEvent2.put("tagsEvent","Formation / git / github / JMC");

        HashMap<String,Object> hashMapValuesEvent3 = new HashMap<>();
        hashMapValuesEvent3.put("nameEvent","Afterwork");
        hashMapValuesEvent3.put("association","MIP");
        hashMapValuesEvent3.put("dateEvent","09/03/2017 à 19:30");
        hashMapValuesEvent3.put("locationEvent","Châtelet");
        hashMapValuesEvent3.put("tagsEvent","afterwork / diplômés / anciens / MIP / miagistes / Paris");

        HashMap<String,Object> hashMapValuesEvent4 = new HashMap<>();
        hashMapValuesEvent4.put("nameEvent","Inter'Miage");
        hashMapValuesEvent4.put("association","EDEN Miage");
        hashMapValuesEvent4.put("dateEvent","20/05/2017 à 10:00");
        hashMapValuesEvent4.put("locationEvent","Université Nanterre");
        hashMapValuesEvent4.put("tagsEvent","inter / MIAGE / Tournoi /sport / EDEN / Miage / mouton");

        HashMap<String,Object> hashMapValuesEvent5 = new HashMap<>();
        hashMapValuesEvent5.put("nameEvent","Lancé de patate");
        hashMapValuesEvent5.put("association","La pataterie");
        hashMapValuesEvent5.put("dateEvent","30/022017 à 15:00");
        hashMapValuesEvent5.put("locationEvent","Université Nanterre");
        hashMapValuesEvent5.put("tagsEvent","Lancé / de / patate");

        // Ajout des données fictives dans la liste
        listValuesEvents.add(hashMapValuesEvent1);
        listValuesEvents.add(hashMapValuesEvent2);
        listValuesEvents.add(hashMapValuesEvent3);
        listValuesEvents.add(hashMapValuesEvent4);
        listValuesEvents.add(hashMapValuesEvent5);

        String[] from = new String[] {"nameEvent","association","dateEvent","placeEvent","tagsEvent"};
        int[] to = new int[] {R.id.content_list_events_name_event,R.id.content_list_events_name_association,R.id.content_list_events_date_event,R.id.content_list_events_location_event,R.id.content_list_events_tags_event};

        listEvents = (ListView) findViewById(R.id.activity_list_events_list);
        adapter = new SimpleAdapter(this,listValuesEvents,R.layout.content_list_events,from,to);
        listEvents.setAdapter(adapter);


       listEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),EventInfosActivity.class);
                startActivity(intent);
            }
        });
    }
}
