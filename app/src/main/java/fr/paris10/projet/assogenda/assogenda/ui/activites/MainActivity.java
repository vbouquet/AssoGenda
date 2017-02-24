package fr.paris10.projet.assogenda.assogenda.ui.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import fr.paris10.projet.assogenda.assogenda.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAuth mFirebaseAuth;
        FirebaseUser mFirebaseUser;

        // Initialize Firebase Auth and Database Reference
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        Button logout = (Button) findViewById(R.id.main_logout_button);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(getIntent());
            }
        });

        if (mFirebaseUser == null) {
            // Not logged in, launch the Log In activity
            loadLogInView();
        }
        /*
        Insert an Else statement later
        */
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    private void loadLogInView() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
