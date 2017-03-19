package fr.paris10.projet.assogenda.assogenda.ui.activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import fr.paris10.projet.assogenda.assogenda.R;
import fr.paris10.projet.assogenda.assogenda.daos.DAOUser;

public class MainAssociationActivity extends AppCompatActivity {
    protected Boolean isAssoMember;
    protected DatabaseReference database = FirebaseDatabase.getInstance().getReference("users");
    private DAOUser daoUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_association);

        this.daoUser = DAOUser.getInstance();

        if(!daoUser.isLoggedIn()){
            redirectIfNotLoggedIn();
        }
        userIsAnAssociationMember();

        Button associationButton = (Button) findViewById(R.id.activity_main_button_association);
            associationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), AssociationDashboardActivity.class);
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
}
