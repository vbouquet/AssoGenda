package fr.paris10.projet.assogenda.assogenda.ui.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import fr.paris10.projet.assogenda.assogenda.R;

public class EventResearchActivity extends AppCompatActivity {

    protected Spinner eventTypeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_research);

        ArrayAdapter<CharSequence> tmpAdapterEventTypes = ArrayAdapter.createFromResource(this,R.array.event_create_event_types, android.R.layout.simple_spinner_item);
        tmpAdapterEventTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventTypeSpinner.setAdapter(tmpAdapterEventTypes);
    }
}
