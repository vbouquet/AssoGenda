package fr.paris10.projet.assogenda.assogenda.ui.activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fr.paris10.projet.assogenda.assogenda.R;

public class MainEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_event);

        Button agendaButton = (Button) findViewById(R.id.activity_main_calendar_button);
        agendaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AgendaGeneratorActivity.class);
                startActivity(intent);
            }
        });

        Button listEventButton = (Button) findViewById(R.id.main_list_events_button);
        listEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ListEventsActivity.class);
                startActivity(intent);
            }
        });

        Button searchEventButton = (Button) findViewById(R.id.activity_main_event_research_button);
        searchEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EventResearchActivity.class);
                startActivity(intent);
            }
        });
    }
}
