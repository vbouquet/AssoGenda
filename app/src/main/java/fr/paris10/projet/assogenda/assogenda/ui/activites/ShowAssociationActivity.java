package fr.paris10.projet.assogenda.assogenda.ui.activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import fr.paris10.projet.assogenda.assogenda.R;
import fr.paris10.projet.assogenda.assogenda.model.Association;

public class ShowAssociationActivity extends AppCompatActivity {
    private String      associationID;
    private Association association;
    private TextView    nameAsso;
    private TextView    descAsso;
    private Button      createEvent;
    private ImageView   logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_association);

        logo           = (ImageView) findViewById(R.id.activity_show_association_logo_asso);
        nameAsso        = (TextView) findViewById(R.id.activity_show_association_name_asso);
        descAsso        = (TextView) findViewById(R.id.activity_show_association_description_asso);
        createEvent     = (Button) findViewById(R.id.activity_show_association_create_event);
        associationID   = (String) getIntent().getExtras().get("associationID");
        Boolean master  = (Boolean) getIntent().getExtras().get("master");

        if (master != null && !master)
            createEvent.setVisibility(View.GONE);

        nameAsso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), association.name, Toast.LENGTH_SHORT).show();
            }
        });

        descAsso.setMovementMethod(new ScrollingMovementMethod());
        FirebaseDatabase.getInstance()
                .getReference("association")
                .child(associationID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    association = dataSnapshot.getValue(Association.class);
                    nameAsso.setText(association.name);
                    descAsso.setText(association.description);

                    if (association.logo != null) {
                        StorageReference storageReference = FirebaseStorage.getInstance().getReference();

                        StorageReference imagePath = storageReference.child(association.logo);

                        Glide.with(getApplicationContext())
                                .using(new FirebaseImageLoader())
                                .load(imagePath)
                                .into(logo);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("onCancelled", databaseError.getMessage());
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
