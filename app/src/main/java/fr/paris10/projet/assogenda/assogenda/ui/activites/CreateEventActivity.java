
package fr.paris10.projet.assogenda.assogenda.ui.activites;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
    protected Spinner eventTypeSpinner;
    protected EditText eventLocationEditText;
    protected EditText eventSeatsAvailableEditText;
    protected EditText eventPriceEditText;
    protected Button eventAdvancedButton;

    protected DatabaseReference database = FirebaseDatabase.getInstance().getReference("events");


    protected DatabaseReference dbAssoc = FirebaseDatabase.getInstance().getReference("associations");
    protected String eventAsso;

    protected Calendar c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        eventAsso = getIntent().getStringExtra("assoID");

        //Initialization
        c = Calendar.getInstance();
        eventNameEditText = (EditText) findViewById(R.id.activity_create_event_name);
        eventStartTimeEditText = (EditText) findViewById(R.id.activity_create_event_start_time);
        eventStartDateEditText = (EditText) findViewById(R.id.activity_create_event_start_date);
        eventEndTimeEditText = (EditText) findViewById(R.id.activity_create_event_end_time);
        eventEndDateEditText = (EditText) findViewById(R.id.activity_create_event_end_date);
        eventDescriptionEditText = (EditText) findViewById(R.id.activity_create_event_description);
        eventCreateButton = (Button) findViewById(R.id.activity_create_event_submit);
        eventTypeSpinner = (Spinner) findViewById(R.id.activity_create_event_type_spinner);
        eventLocationEditText = (EditText) findViewById(R.id.activity_create_event_location);
        eventSeatsAvailableEditText = (EditText) findViewById(R.id.activity_create_event_seats_available);
        eventPriceEditText = (EditText) findViewById(R.id.activity_create_event_price);
        eventAdvancedButton = (Button) findViewById(R.id.activity_create_event_advanced_button);

        //Hide advanced options
        eventPriceEditText.setVisibility(View.GONE);
        eventSeatsAvailableEditText.setVisibility(View.GONE);
        eventDescriptionEditText.setVisibility(View.GONE);

        //Default value (date and time)
        eventStartDateEditText.setText(new SimpleDateFormat("dd/MM/yyyy").format(c.getTime()));
        eventStartTimeEditText.setText(new SimpleDateFormat("kk:mm").format(c.getTime()));
        eventEndDateEditText.setText(new SimpleDateFormat("dd/MM/yyyy").format(c.getTime()));
        c.setTime(c.getTime());
        c.add(Calendar.HOUR, 1);
        eventEndTimeEditText.setText(new SimpleDateFormat("kk:mm").format(c.getTime()));

        //Sets the spinner's content
        ArrayAdapter<CharSequence> tmpAdapterEventTypes = ArrayAdapter.createFromResource(this,R.array.event_create_event_types, android.R.layout.simple_spinner_item);
        tmpAdapterEventTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventTypeSpinner.setAdapter(tmpAdapterEventTypes);

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

        eventAdvancedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eventPriceEditText.getVisibility() == View.GONE){
                    eventPriceEditText.setVisibility(View.VISIBLE);
                    eventSeatsAvailableEditText.setVisibility(View.VISIBLE);
                    eventDescriptionEditText.setVisibility(View.VISIBLE);
                }
                else{
                    eventPriceEditText.setVisibility(View.GONE);
                    eventSeatsAvailableEditText.setVisibility(View.GONE);
                    eventDescriptionEditText.setVisibility(View.GONE);
                }
            }
        });



        eventCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String eventName = eventNameEditText.getText().toString().trim();
                final String eventStart = eventDatesConverter(eventStartTimeEditText, eventStartDateEditText);
                final String eventEnd = eventDatesConverter(eventEndTimeEditText, eventEndDateEditText);
                final String eventType = eventTypeSpinner.getItemAtPosition(eventTypeSpinner.getSelectedItemPosition()).toString().trim();
                String eventDescription = eventDescriptionEditText.getText().toString().trim();
                final String eventLocation = eventLocationEditText.getText().toString().trim();
                String eventSeatsAvailable = eventSeatsAvailableEditText.getText().toString().trim();
                String eventPrice = eventPriceEditText.getText().toString().trim();
                final String eventAssociation = eventAsso;

                //Checking value of optional fields
                if (eventDescription.isEmpty()){
                    eventDescription = "";
                }
                //Value to -1 means there are no seats limit
                if (eventSeatsAvailable.isEmpty()){
                    eventSeatsAvailable = "-1";
                }
                //Value to -1 means there are no price, it's free
                if (eventPrice.isEmpty()){
                    eventPrice = "-1.0";
                }

                // NOTE : SEATS / PRICE / DESCRIPTION  =>  OPTIONAL

                if (eventName.isEmpty() || eventStart == null || eventEnd == null || eventType.isEmpty() || eventLocation.isEmpty() ) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateEventActivity.this);
                    builder.setMessage(R.string.event_create_submit_error_message)
                            .setTitle(R.string.event_create_submit_error_title)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else if (!firstDateAnteriorToSecond(eventStart, eventEnd)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateEventActivity.this);
                    builder.setMessage(R.string.event_create_submit_date_error_message)
                            .setTitle(R.string.event_create_submit_error_title)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else{
                    database.push()
                            .setValue(new Event( "", eventName, eventStart, eventEnd, eventType, eventAssociation, eventLocation,
                                    Float.parseFloat(eventPrice), Integer.parseInt(eventSeatsAvailable), eventDescription)
                                    .toMap());
                    loadMain();
                }
            }
        });
    }

    private boolean firstDateAnteriorToSecond(String eventStart, String eventEnd) {
        DateFormat dateFormatter = new SimpleDateFormat("kk:mm dd/MM/yyyy");
        Date start;
        Date end;

        try {
            start = dateFormatter.parse(eventStart);
            end = dateFormatter.parse(eventEnd);
            if (start.after(end)){
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        return true;
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

    public String eventDatesConverter(EditText time, EditText date){
        DateFormat dateFormatter = new SimpleDateFormat("kk:mm dd/MM/yyyy");
        String tmpTime = time.getText().toString().trim();
        String tmpDate = date.getText().toString().trim();

        if (tmpTime.isEmpty() || tmpDate.isEmpty()){
            return null;
        }

        try {
            Date tmp = dateFormatter.parse(tmpTime + " " + tmpDate);
            return dateFormatter.format(tmp);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void loadMain(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}