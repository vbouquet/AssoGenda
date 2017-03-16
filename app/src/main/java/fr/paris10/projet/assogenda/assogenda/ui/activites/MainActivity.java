package fr.paris10.projet.assogenda.assogenda.ui.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import fr.paris10.projet.assogenda.assogenda.R;
import fr.paris10.projet.assogenda.assogenda.daos.DAOUser;

public class MainActivity extends AppCompatActivity {
    private DAOUser daoUser;

    protected Boolean isAssoMember;

    protected DatabaseReference database = FirebaseDatabase.getInstance().getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.daoUser = DAOUser.getInstance();

        if(!daoUser.isLoggedIn()){
            redirectIfNotLoggedIn();
        }
        else {

            //Sets isAssoMember to true or false
            userIsAnAssociationMember();

            Button logoutButton = (Button) findViewById(R.id.activity_main_logout_button);
            logoutButton.setOnClickListener(new View.OnClickListener() {
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

            Button listAssociationsButton = (Button) findViewById(R.id.main_activity_list_asso_button);
            listAssociationsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ListAssociationActivity.class);
                    intent.putExtra("followed", false);
                    startActivity(intent);
                }
            });

            Button followedAssoButton = (Button) findViewById(R.id.main_activity_followed_asso_button);
            followedAssoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ListAssociationActivity.class);
                    intent.putExtra("followed", true);
                    startActivity(intent);
                }
            });


        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onResume(){
        /*Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);*/
        super.onResume();
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

    //Updates isAssoMember variable according to association appatenance of the current user
    protected void userIsAnAssociationMember(){
        isAssoMember = false;

        database.child(daoUser.getCurrentUserId()).child("isAssoMember").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    isAssoMember = dataSnapshot.getValue(Boolean.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
