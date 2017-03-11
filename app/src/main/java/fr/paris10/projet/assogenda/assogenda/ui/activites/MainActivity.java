package fr.paris10.projet.assogenda.assogenda.ui.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import fr.paris10.projet.assogenda.assogenda.R;
import fr.paris10.projet.assogenda.assogenda.daos.DAOUser;

public class MainActivity extends AppCompatActivity {
    private DAOUser daoUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.daoUser = DAOUser.getInstance();
        redirectIfNotLoggedIn();

        Button logoutButon = (Button) findViewById(R.id.main_logout_button);
        logoutButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loggedOut();
            }
        });

        Button associationButton = (Button) findViewById(R.id.activity_main_button_association);
        associationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AssociationDashboardActivity.class);
                startActivity(intent);
            }
        });

        Button agendaButton = (Button) findViewById(R.id.activity_main_calendar_button);
        agendaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AgendaActivity.class);
                startActivity(intent);
            }
        });

        Button listEventButton = (Button) findViewById(R.id.main_list_events_button);
        listEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ListEventsActivity.class);
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

    @Override
    protected void onStart() {
        super.onStart();
    }

    protected void loadLogInView() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    protected void redirectIfNotLoggedIn() {
        if (!daoUser.isLoggedIn())
            loadLogInView();
    }

    protected void loggedOut() {
        daoUser.signOut();
        finish();
        startActivity(getIntent());
    }
}
