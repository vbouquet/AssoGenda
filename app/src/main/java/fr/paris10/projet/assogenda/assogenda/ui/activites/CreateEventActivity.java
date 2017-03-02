package fr.paris10.projet.assogenda.assogenda.ui.activites;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import fr.paris10.projet.assogenda.assogenda.R;
import fr.paris10.projet.assogenda.assogenda.model.Event;

public class CreateEventActivity extends AppCompatActivity {
    protected EditText eventNameEditText;
    protected EditText eventStartTimeEditText;
    protected EditText eventStartDateEditText;
    protected EditText eventEndTimeEditText;
    protected EditText eventEndDateEditText;
    protected EditText eventDescriptionEditText;
    protected Button eventCreateButton;

    protected DateFormat dateFormatter;

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
        eventDescriptionEditText = (EditText) findViewById(R.id.activity_create_event_description);
        eventCreateButton = (Button) findViewById(R.id.activity_create_event_submit);

        eventStartTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeDialog(eventStartTimeEditText);
            }
        });

        eventStartDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog(eventStartDateEditText);
            }
        });

        eventEndTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeDialog(eventEndTimeEditText);
            }
        });

        eventEndDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog(eventEndDateEditText);
            }
        });




        eventCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String eventName = eventNameEditText.getText().toString().trim();
                final Date eventStart = eventDatesConverter(eventStartTimeEditText, eventStartDateEditText);
                final Date eventEnd = eventDatesConverter(eventEndTimeEditText, eventEndDateEditText);
                final String eventDescription = eventDescriptionEditText.getText().toString().trim();

                if (eventName.isEmpty() || eventDescription.isEmpty() || eventStart == null || eventEnd == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateEventActivity.this);
                    builder.setMessage(R.string.event_create_submit_error_message)
                            .setTitle(R.string.event_create_submit_error_title)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else{
                    database.push().setValue(new Event(eventName, eventStart, eventEnd, eventDescription).toMap());
                }
            }
        });
    }

    public void dateDialog(final EditText editText){
        Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker;
        mDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                selectedmonth = selectedmonth + 1;
                editText.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
            }
        }, mYear, mMonth, mDay);
        mDatePicker.setTitle("Select Date");
        mDatePicker.show();
    }

    public void timeDialog(final EditText editText){
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                editText.setText( selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    public Date eventDatesConverter(EditText time, EditText date){
        DateFormat dateFormatter = new SimpleDateFormat("hh:mm dd/MM/yyyy", Locale.FRANCE);
        String tmpTime = time.getText().toString().trim();
        String tmpDate = date.getText().toString().trim();

        if (tmpTime.isEmpty() || tmpDate.isEmpty()){
            return null;
        }

        try {
            return dateFormatter.parse(tmpTime + " " + tmpDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
