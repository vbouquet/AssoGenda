package fr.paris10.projet.assogenda.assogenda.ui.activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fr.paris10.projet.assogenda.assogenda.R;

public class AgendaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        Button agendaButton = (Button) findViewById(R.id.activity_agenda_generate_button);
        agendaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AgendaGeneratorActivity.class);
                startActivity(intent);
            }
        });
    }
}
