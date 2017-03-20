package fr.paris10.projet.assogenda.assogenda.ui.activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import fr.paris10.projet.assogenda.assogenda.R;
import fr.paris10.projet.assogenda.assogenda.model.Association;
import fr.paris10.projet.assogenda.assogenda.model.User;

public class ShowAssociationActivity extends AppCompatActivity {
    private String associationID;
    private Association association;
    private User president;
    private TextView nameAsso;
    private TextView descAsso;
    private Button createEvent;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_association);

        image = (ImageView) findViewById(R.id.activity_show_association_logo_asso);
        image.setImageResource(R.drawable.association_default_icon);
        nameAsso = (TextView) findViewById(R.id.activity_show_association_name_asso);
        descAsso = (TextView) findViewById(R.id.activity_show_association_description_asso);
        createEvent = (Button) findViewById(R.id.activity_show_association_create_event);
        associationID = (String) getIntent().getExtras().get("associationID");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("association");

        nameAsso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), association.name, Toast.LENGTH_SHORT).show();
            }
        });

        descAsso.setMovementMethod(new ScrollingMovementMethod());
        reference.child(associationID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    association = dataSnapshot.getValue(Association.class);
                    nameAsso.setText(association.name);
                    descAsso.setText(association.description);
                    DatabaseReference references = FirebaseDatabase.getInstance().getReference("users");
                    references.child(association.president).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                president = dataSnapshot.getValue(User.class);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateEventActivity.class);
                intent.putExtra("assoID", associationID);
                startActivity(intent);
            }
        });

    }
}
