package fr.paris10.projet.assogenda.assogenda.ui.activites;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import fr.paris10.projet.assogenda.assogenda.R;
import fr.paris10.projet.assogenda.assogenda.model.Event;
import fr.paris10.projet.assogenda.assogenda.ui.adapter.CustomEventAdapter;

public class EventResearchActivity extends AppCompatActivity {

    public Spinner eventResearchSpinner;
    public CustomEventAdapter eventAdapter;
    public ArrayList<Event> items;
    public ListView listView;
    public DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("events");

    protected EditText eventDateEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_research);

        items = new ArrayList<>();
        eventAdapter = new CustomEventAdapter(this, items);
        eventResearchSpinner = (Spinner) findViewById(R.id.activity_event_research_eventype_spinner);

        ArrayAdapter<CharSequence> tmpAdapterEventTypes = ArrayAdapter.createFromResource(this, R.array.event_create_event_types, android.R.layout.simple_spinner_item);
        tmpAdapterEventTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventResearchSpinner.setAdapter(tmpAdapterEventTypes);

        eventDateEditText = (EditText) findViewById(R.id.activity_event_research_start_date);
        listView = (ListView) findViewById(R.id.activity_event_research_list_view);
        listView.setAdapter(eventAdapter);

        eventDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog(eventDateEditText);
            }
        });

        Button buttonResearch = (Button) findViewById(R.id.activity_event_research_button);
        buttonResearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eventType = eventResearchSpinner.getSelectedItem().toString();
                String date = eventDateEditText.getText().toString();
                items.clear();
                eventAdapter.notifyDataSetChanged();
                loadEventsInBackground(eventType, date);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), EventInfosActivity.class);
                Event event = items.get(position);
                intent.putExtra("eventUID", event.uid);
                startActivity(intent);
            }
        });
    }

    public void loadEventsInBackground(final String eventType, final String date) {

        Query query = databaseReference;

        if (!"".equals(eventType) && "".equals(date) || !"".equals(eventType) && !"".equals(date)) {
            query = databaseReference.orderByChild("type").equalTo(eventType);
        }

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                Event event = snapshot.getValue(Event.class);
                event.uid = snapshot.getKey();
                event.seat_free = Integer.parseInt(String.valueOf(snapshot.child("seats free").getValue()));
                Date endDate = new Date();
                Date pickedDate = new Date();
                Date startDate = new Date();

                try {
                    endDate = Event.dateFormatter.parse(event.end);
                    pickedDate = Event.dateFormatter.parse("00:00 " + date);
                    startDate = Event.dateFormatter.parse(event.start);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (!"".equals(date)) {
                    if (pickedDate.before(endDate) && pickedDate.after(startDate)) {
                        items.add(event);
                        eventAdapter.notifyDataSetChanged();
                    }
                } else {
                    items.add(event);
                    eventAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.i(this.getClass().getCanonicalName(), " onChildChanged");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.i(this.getClass().getCanonicalName(), " onChildRemoved");
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.i(this.getClass().getCanonicalName(), " onChildMoved");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Error : ", "onCancelled", databaseError.toException());
            }
        });
    }

    public void dateDialog(final EditText editText) {

        Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker;
        mDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                editText.setText(String.format("%02d/%02d/%d", selectedday, selectedmonth + 1, selectedyear));
            }
        }, mYear, mMonth, mDay);
        mDatePicker.setTitle("Select Date");
        mDatePicker.show();
    }
}
