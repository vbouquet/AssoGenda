package fr.paris10.projet.assogenda.assogenda.ui.activites;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.alamkanak.weekview.WeekViewEvent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.paris10.projet.assogenda.assogenda.R;
import fr.paris10.projet.assogenda.assogenda.model.Event;

/**
 * Events loader.
 */
public class AgendaGeneratorActivity extends WeekViewActivity {

    public static int id;

    public int monthStart;
    public int dayStart;
    public int yearStart;
    public int hourStart;
    public int minStart;

    public int monthEnd;
    public int dayEnd;
    public int yearEnd;
    public int hourEnd;
    public int minEnd;

    public WeekViewEvent event;

    //Populate the week view with some events.
    public static List<WeekViewEvent> events = new ArrayList<>();

    @Override
    public List onMonthChange(int newYear, final int newMonth) {

        if(newMonth == Calendar.getInstance().get(Calendar.MONTH) + 1 && events.size() == 0) {
            loadEventInBackground(newMonth);
            return events;
        }
        return events;
    }

    public void loadEventInBackground(final int newMonth) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("events");
        id = 0;
        Log.i("Start ", "1");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot events : dataSnapshot.getChildren()) {
                        Event event = events.getValue(Event.class);
                        monthStart = Integer.parseInt(event.start.substring(9, 11));
                        if (monthStart == newMonth) {

                            Log.i("Start ", "2");
                            initDate(event.start, event.end);
                            setEvents(event.name, newMonth);
                        }
                    }
                    if (monthStart == newMonth) {

                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Error : ", "onCancelled", databaseError.toException());
            }
        });
    }

    public void initDate(String dateStart, String dateEnd) {

        Log.i("InititDate ", " initDate");

        dayStart = Integer.parseInt(dateStart.substring(6, 8));
        yearStart = Integer.parseInt(dateStart.substring(12, 16));
        hourStart = Integer.parseInt(dateStart.substring(0, 2));
        minStart = Integer.parseInt(dateStart.substring(3, 5));

        monthEnd = Integer.parseInt(dateEnd.substring(9, 11));
        dayEnd = Integer.parseInt(dateEnd.substring(6, 8));
        yearEnd = Integer.parseInt(dateEnd.substring(12, 16));
        hourEnd = Integer.parseInt(dateEnd.substring(0, 2));
        minEnd = Integer.parseInt(dateEnd.substring(3, 5));
    }

    public void setEvents(String title, int newMonth) {

        Log.i("SetEvents ", " setEvents");

        Calendar startTime;
        Calendar endTime;

        startTime = Calendar.getInstance();
        startTime.set(Calendar.DAY_OF_MONTH, dayStart);
        startTime.set(Calendar.MONTH, newMonth - 1);
        startTime.set(Calendar.YEAR, yearStart);
        startTime.set(Calendar.HOUR_OF_DAY, hourStart);
        startTime.set(Calendar.MINUTE, minStart);
        endTime = (Calendar) startTime.clone();
        endTime.set(Calendar.DAY_OF_MONTH, dayEnd);
        endTime.set(Calendar.MONTH, newMonth - 1);
        endTime.set(Calendar.YEAR, yearEnd);
        endTime.set(Calendar.HOUR_OF_DAY, hourEnd);
        endTime.set(Calendar.MINUTE, minEnd);
        event = new WeekViewEvent(++id, title, startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_01));
        events.add(event);
    }
}
