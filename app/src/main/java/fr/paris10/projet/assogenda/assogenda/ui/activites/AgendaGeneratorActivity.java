package fr.paris10.projet.assogenda.assogenda.ui.activites;

import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

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
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Events loader.
 */
public class AgendaGeneratorActivity extends WeekViewActivity implements Callback<List<Event>> {

    public int monthStart;
    public Agenda agenda;
    public WeekViewEvent event;
    public Menu menu;

    private List<WeekViewEvent> events = new ArrayList<>();
    public boolean isDisplayed = false;
    public static int color;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {

        if (!isDisplayed) {
            loadEventInBackground();
            isDisplayed = true;
        }
        List<WeekViewEvent> matchedEvents = new ArrayList<>();
        for (WeekViewEvent event : events) {
            if (eventMatches(event, newYear, newMonth - 1)) {
                matchedEvents.add(event);
            }
        }
        return matchedEvents;
    }

    private boolean eventMatches(WeekViewEvent event, int year, int month) {
        return (event.getStartTime().get(Calendar.YEAR) == year && event.getStartTime().get(Calendar.MONTH) == month) || (event.getEndTime().get(Calendar.YEAR) == year && event.getEndTime().get(Calendar.MONTH) == month);
    }

    @Override
    public void success(List<Event> events, Response response) {
        events.clear();
        for (Event event : events) {
            events.add(event);
        }
        getWeekView().notifyDatasetChanged();
    }

    @Override
    public void failure(RetrofitError error) {
        error.printStackTrace();
        Toast.makeText(this, "Error while loading events", Toast.LENGTH_SHORT).show();
    }

    public void loadEventInBackground() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("events");
        isDisplayed = false;
        color = 0;
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot events : dataSnapshot.getChildren()) {
                        Event event = events.getValue(Event.class);
                        monthStart = Integer.parseInt(event.start.substring(9, 11));
                        agenda = new Agenda();
                        agenda.initDate(event.start, event.end);
                        setEvents(event.name);
                    }
                }
                //Refresh after all events are ready
                onOptionsItemSelected(menu.findItem(R.id.action_today));
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

        event = new WeekViewEvent(color++, title, startTime, endTime);

        //Deprecated depuis API 23
        if (color % 5 == 1) {
            event.setColor(getResources().getColor(R.color.event_color_01));
        } else if (color % 5 == 2) {
            event.setColor(getResources().getColor(R.color.event_color_02));
        } else if (color % 5 == 3) {
            event.setColor(getResources().getColor(R.color.event_color_03));
        } else if (color % 5 == 4) {
            event.setColor(getResources().getColor(R.color.event_color_04));
        } else {
            event.setColor(getResources().getColor(R.color.event_color_05));
        }
        events.add(event);
    }
}
