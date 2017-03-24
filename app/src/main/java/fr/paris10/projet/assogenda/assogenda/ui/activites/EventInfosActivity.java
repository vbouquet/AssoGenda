package fr.paris10.projet.assogenda.assogenda.ui.activites;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import fr.paris10.projet.assogenda.assogenda.R;
import fr.paris10.projet.assogenda.assogenda.model.Association;
import fr.paris10.projet.assogenda.assogenda.model.Event;

public class EventInfosActivity extends AppCompatActivity {
    private String eventUID;
    private Event event;
    private TextView nameEvent;
    private TextView nameAsso;
    private String name;
    private Date eventEndDate;
    private TextView participateButton;
    private TextView beginText;
    private TextView endText;
    private TextView priceText;
    private TextView placeText;
    private TextView descText;
    private TextView typeText;
    private TextView typeLabelText;
    private ImageView logo;
    private final SimpleDateFormat inputDate  = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.FRANCE);
    private final SimpleDateFormat outputDate = new SimpleDateFormat("EEEE d MMM d à HH:mm", Locale.FRANCE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_infos);

        eventUID            = (String) getIntent().getExtras().get("eventUID");
        name                = (String) getIntent().getExtras().get("eventName");
        String eventEnd     = (String) getIntent().getExtras().get("eventEndDate");
        nameEvent           = (TextView) findViewById(R.id.event_infos_activity_event_name);
        nameAsso            = (TextView) findViewById(R.id.event_infos_activity_asso_name);
        beginText           = (TextView) findViewById(R.id.event_infos_activity_begin);
        endText             = (TextView) findViewById(R.id.event_infos_activity_end);
        priceText           = (TextView) findViewById(R.id.event_infos_activity_price);
        placeText           = (TextView) findViewById(R.id.event_infos_activity_location);
        descText            = (TextView) findViewById(R.id.event_infos_activity_description);
        logo                = (ImageView) findViewById(R.id.event_infos_activity_logo_asso);
        typeText            = (TextView) findViewById(R.id.event_infos_activity_type);
        typeLabelText       = (TextView) findViewById(R.id.event_infos_activity_type_label);
        descText.setMovementMethod(new ScrollingMovementMethod());

        loadEventInfoInBackground();

        try {
            eventEndDate = Event.dateFormatter.parse(eventEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        participateButton   = (Button) findViewById(R.id.event_infos_activity_participate);
        participateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nonParticipateEvent();
            }
        });
    }

    public void updateParticipate() {
        FirebaseDatabase.getInstance().getReference("user-events")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(eventUID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            participateButton.setText(R.string.activity_event_infos_nparticipate_button);
                            participateButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        } else {
                            participateButton.setText(R.string.activity_event_infos_participate_button);
                            participateButton.setBackgroundColor(getResources().getColor(R.color.event_color_03));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("Error : ", "onCancelled", databaseError.toException());
                    }
                });
    }

    public void participateEvent() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user-events");
        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(eventUID).setValue(name);
        Toast.makeText(getApplicationContext(), getApplicationContext()
                        .getResources().getString(R.string.activity_event_infos_participate_toast) + " " + name,
                Toast.LENGTH_LONG).show();
    }

    public void nonParticipateEvent() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user-events");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Calendar c = Calendar.getInstance();
                Date today = new Date();

                try {
                    today = Event.dateFormatter.parse(Event.dateFormatter.format(c.getTime()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (!eventEndDate.before(today)) {

                    if (snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).hasChild(eventUID)) {
                        participateButton.setText(R.string.activity_event_infos_participate_button);
                        participateButton.setBackgroundColor(getResources().getColor(R.color.event_color_03));
                        snapshot.child(FirebaseAuth.getInstance().getCurrentUser()
                                .getUid()).child(eventUID).getRef().getRef().removeValue();
                        Toast.makeText(getApplicationContext(), getApplicationContext()
                                .getResources().getString(R.string.activity_event_infos_nparticipate_toast)
                                + " " + name, Toast.LENGTH_LONG).show();
                    } else {
                        participateButton.setText(R.string.activity_event_infos_nparticipate_button);
                        participateButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        participateEvent();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), getApplicationContext()
                                    .getResources().getString(R.string.activity_event_infos_participate_date_passed_toast)
                            , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Error : ", "onCancelled", databaseError.toException());
            }
        });
    }

    public void loadEventInfoInBackground() {

        FirebaseDatabase.getInstance().getReference("events")
                .child(eventUID)
                .addListenerForSingleValueEvent( new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    event = dataSnapshot.getValue(Event.class);
                    event.uid=eventUID;
                    nameEvent.setText(event.name);

                    nameEvent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getApplicationContext(), event.name, Toast.LENGTH_SHORT).show();
                        }
                    });

                    FirebaseDatabase.getInstance()
                            .getReference("association")
                            .child(event.association)
                            .addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot data) {
                            if (data.exists()) {
                                final Association association = data.getValue(Association.class);
                                nameAsso.setText(association.name);

                                if (association.logo != null) {
                                    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

                                    StorageReference imagePath = storageReference.child(association.logo);

                                    Glide.with(getApplicationContext())
                                            .using(new FirebaseImageLoader())
                                            .load(imagePath)
                                            .into(logo);
                                }

                                nameAsso.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(getApplicationContext(), association.name, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d("onCancelled", databaseError.getMessage());
                        }
                    });

                    try {
                        beginText.setText(outputDate.format(inputDate.parse(event.start)));
                        endText.setText(outputDate.format(inputDate.parse(event.end)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (event.price <= 0) {
                        priceText.setText("Gratuit");
                    } else {
                        priceText.setText(String.format("%.2f €", event.price));
                    }
                    placeText.setText(event.location);
                    descText.setText(event.description);

                    if (event.type.trim().equals("Other")) {
                        typeText.setVisibility(View.GONE);
                        typeLabelText.setVisibility(View.GONE);
                    } else {
                        typeText.setText(event.type);
                    }
                }

                updateParticipate();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Error : ", "onCancelled", databaseError.toException());
            }
        });
    }
}