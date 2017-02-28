package fr.paris10.projet.assogenda.assogenda.ui.activites;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.support.v4.app.DialogFragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;

import fr.paris10.projet.assogenda.assogenda.R;
import fr.paris10.projet.assogenda.assogenda.ui.fragments.TimePickerFragment;

public class CreateEventActivity extends AppCompatActivity {
    protected EditText eventNameEditText;
    protected EditText eventStartTimeEditText;
    protected EditText eventStartDateEditText;
    protected EditText eventEndTimeEditText;
    protected EditText eventEndDateEditText;
    protected Button eventCreateButton;

    protected DatabaseReference database = FirebaseDatabase.getInstance().getReference("events");

    protected static DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        eventNameEditText = (EditText) findViewById(R.id.activity_create_event_name);
        eventStartTimeEditText = (EditText) findViewById(R.id.activity_create_event_start_time);
        eventStartDateEditText = (EditText) findViewById(R.id.activity_create_event_start_date);
        eventEndTimeEditText = (EditText) findViewById(R.id.activity_create_event_end_time);
        eventEndDateEditText = (EditText) findViewById(R.id.activity_create_event_end_date);
        eventCreateButton = (Button) findViewById(R.id.activity_create_event_submit);

        eventStartTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v);
            }
        });




        eventCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String eventName = eventNameEditText.getText().toString().trim();
                //final Date eventStart = dateFormat.format(eventStartEditText);
                //final Date eventEnd = eventNameEditText.getText().toString().trim();

                if (eventName.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateEventActivity.this);
                    builder.setMessage(R.string.event_create_submit_error_message)
                            .setTitle(R.string.event_create_submit_error_title)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else{
                    //database.push(new Event(eventName, eventStart, eventEnd));
                }
            }
        });
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }
}
