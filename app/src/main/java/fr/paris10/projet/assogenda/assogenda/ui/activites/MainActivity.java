package fr.paris10.projet.assogenda.assogenda.ui.activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import fr.paris10.projet.assogenda.assogenda.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(this.getClass().getCanonicalName(), "Entre dans onCreate");

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
        Log.i(this.getClass().getCanonicalName(), "Entre dans onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(this.getClass().getCanonicalName(), "Entre dans onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(this.getClass().getCanonicalName(), "Entre dans onDestroy");
    }
}