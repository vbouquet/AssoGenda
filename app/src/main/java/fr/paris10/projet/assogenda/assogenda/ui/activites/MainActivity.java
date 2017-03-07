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



        Button logoutButton = (Button) findViewById(R.id.activity_main_logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loggedOut();
            }
        });

        Button goToEventCreation = (Button) findViewById(R.id.activity_main_create_event_button);
        //if (true) {
        //
        //}
        //else{
            goToEventCreation.setVisibility(View.VISIBLE);
            goToEventCreation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadCreateEventView();
                }
            });
        //}


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

    protected void loadCreateEventView(){
        Intent intent = new Intent(this, CreateEventActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
