package fr.paris10.projet.assogenda.assogenda.ui.activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import fr.paris10.projet.assogenda.assogenda.R;

public class MainActivity extends AppCompatActivity {

    //Create a fake user to create a link between user and association
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button associationButton = (Button) findViewById(R.id.activity_main_button_association);
        associationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAssociationActivity();
            }
        });
    }

    public void startAssociationActivity() {
        Intent intent = new Intent(this, AssociationDashboardActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}