package fr.paris10.projet.assogenda.assogenda.ui.activites;

import android.content.Intent;
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
import fr.paris10.projet.assogenda.assogenda.model.Agenda;

/**
 * Events loader.
 */
public class AgendaGeneratorActivity extends WeekViewActivity {

    public static int id;
    public int monthStart;
    public Agenda agenda;
    public WeekViewEvent event;

    //Populate the week view with some events.
    public static List<WeekViewEvent> events = new ArrayList<>();

    public static boolean isDisplayed = false;

    @Override
    public List onMonthChange(int newYear, final int newMonth) {

        if (events.size() == 0 && !isDisplayed) {
            loadEventInBackground(newMonth);
        }
        if(isDisplayed) {
            isDisplayed = false;
            return events;
        }
        return new ArrayList<>();
    }

    public void loadEventInBackground(final int newMonth) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("events");
        id = 0;
        isDisplayed = false;
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot events : dataSnapshot.getChildren()) {
                        Event event = events.getValue(Event.class);
                        monthStart = Integer.parseInt(event.start.substring(9, 11));
                        if (monthStart == newMonth) {

                            agenda = new Agenda();
                            agenda.initDate(event.start, event.end);
                            setEvents(event.name);
                        }
                    }
                    isDisplayed = true;

                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Error : ", "onCancelled", databaseError.toException());
            }
        });
    }

    public void setEvents(String title) {

        Calendar startTime;
        Calendar endTime;

        startTime = Calendar.getInstance();
        startTime.set(Calendar.DAY_OF_MONTH, agenda.dayStart);
        startTime.set(Calendar.MONTH, monthStart - 1);
        startTime.set(Calendar.YEAR, agenda.yearStart);
        startTime.set(Calendar.HOUR_OF_DAY, agenda.hourStart);
        startTime.set(Calendar.MINUTE, agenda.minStart);
        endTime = (Calendar) startTime.clone();
        endTime.set(Calendar.DAY_OF_MONTH, agenda.dayEnd);
        endTime.set(Calendar.MONTH, monthStart - 1);
        endTime.set(Calendar.YEAR, agenda.yearEnd);
        endTime.set(Calendar.HOUR_OF_DAY, agenda.hourEnd);
        endTime.set(Calendar.MINUTE, agenda.minEnd);
        endTime.set(Calendar.HOUR_OF_DAY, agenda.hourEnd);
        event = new WeekViewEvent(++id, title, startTime, endTime);

        if (id % 4 == 0) {
            event.setColor(getResources().getColor(R.color.event_color_01));
        } else if (id % 4 == 1) {
            event.setColor(getResources().getColor(R.color.event_color_02));
        } else if (id % 4 == 2) {
            event.setColor(getResources().getColor(R.color.event_color_03));
        } else if (id % 4 == 3) {
            event.setColor(getResources().getColor(R.color.event_color_04));
        } else if (id % 4 == 4) {
            event.setColor(getResources().getColor(R.color.event_color_05));
        }
        events.add(event);
    }
}
