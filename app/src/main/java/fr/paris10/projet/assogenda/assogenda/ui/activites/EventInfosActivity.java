package fr.paris10.projet.assogenda.assogenda.ui.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import fr.paris10.projet.assogenda.assogenda.R;

public class EventInfosActivity extends AppCompatActivity {
    private ListView listInfos;
    private ArrayList<HashMap<String,Object>> listValues = new ArrayList<>();
    private SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_infos);

        // Données fictives
        HashMap<String,Object> hashMapValueDateBegin = new HashMap<>();
        hashMapValueDateBegin.put("title_info","Date de début : ");
        hashMapValueDateBegin.put("content_info","03/03/2017 à 18:00");
        HashMap<String,Object> hashMapValueDateEnd = new HashMap<>();
        hashMapValueDateEnd.put("title_info","Date de fin : ");
        hashMapValueDateEnd.put("content_info","03/03/2017 à 23:59");
        HashMap<String,Object> hashMapValuePrice = new HashMap<>();
        hashMapValuePrice.put("title_info","Prix : ");
        hashMapValuePrice.put("content_info","Entrée gratuite");
        HashMap<String,Object> hashMapValueSpace = new HashMap<>();
        hashMapValueSpace.put("title_info","Places disponible : ");
        hashMapValueSpace.put("content_info","Pas de limite de place");
        HashMap<String,Object> hashMapValueBail = new HashMap<>();
        hashMapValueBail.put("title_info","Caution : ");
        hashMapValueBail.put("content_info","Pas de caution");
        HashMap<String,Object> hashMapValuePlace = new HashMap<>();
        hashMapValuePlace.put("title_info","Lieu : ");
        hashMapValuePlace.put("content_info","Châtelet");
        HashMap<String,Object> hashMapValueDescription = new HashMap<>();
        hashMapValueDescription.put("title_info","Description : ");
        hashMapValueDescription.put("content_info","Viens chanter tes titres préférés et saigner des oreilles !!");
        HashMap<String,Object> hashMapValueCategorie = new HashMap<>();
        hashMapValueCategorie.put("title_info","Categorie : ");
        hashMapValueCategorie.put("content_info","soirée");

        listValues.add(hashMapValueDateBegin);
        listValues.add(hashMapValueDateEnd);
        listValues.add(hashMapValuePrice);
        listValues.add(hashMapValueSpace);
        listValues.add(hashMapValueBail);
        listValues.add(hashMapValuePlace);
        listValues.add(hashMapValueDescription);
        listValues.add(hashMapValueCategorie);

        String[] from = new String[] {"title_info","content_info"};
        int[] to = new int[] {R.id.content_infos_event_title_info,R.id.content_infos_event_content_info};

        listInfos = (ListView) findViewById(R.id.activity_event_infos_list);
        adapter = new SimpleAdapter(this,listValues,R.layout.content_infos_event,from,to);
        listInfos.setAdapter(adapter);

    }
}
