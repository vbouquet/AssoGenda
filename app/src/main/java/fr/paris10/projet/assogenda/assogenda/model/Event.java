package fr.paris10.projet.assogenda.assogenda.model;

import com.google.firebase.database.Exclude;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wilpiron on 28/02/2017.
 */

public class Event {
    public String id;
    public String name;
    public Date start;
    public Date end;
    public String description;
    public String location;
    public String type;
    public int price;
    public int bail;
    public int seat_number;
    public int seat_free;
    public Association association;
    public int logo;

    public static DateFormat dateFormat =
            DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);

    public Event(){
    }

    public Event(String name, Date start, Date end, String type, String description, String location,
                 int price, int bail, int seat_number, Association association, int logo){
        this.name=name;
        this.start=start;
        this.end=end;
        this.type=type;
        this.description=description;
        this.location=location;
        this.price=price;
        this.bail=bail;
        this.seat_number=seat_number;
        this.seat_free=seat_number;
        this.logo=logo;
        this.association=association;
    }

    public Event(String name, Date start, Date end, String type, String location,
                 int price, int seat_number, String description){
        this.name=name;
        this.start=start;
        this.end=end;
        this.type=type;
        this.location=location;
        this.price=price;
        this.seat_free=seat_number;
        this.seat_number=seat_number;
        this.description=description;
    }

    /**
     * Two methods to control the number of seats available
     */

    public void reserveSeat(){
        seat_free -= seat_free;
    }

    public void freeSeat(){
        seat_free += seat_free;
    }

    /**
     * Define how data will be stored in database
     */
    @Exclude
    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("description", description);
        result.put("type", type);
        //result.put("association", association.name);
        result.put("location", location);
        result.put("seats number", seat_number);
        result.put("seats free", seat_free);
        result.put("start", dateFormat.format(start));
        result.put("end",dateFormat.format(end));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("name= ").append(name).append("\n");
        //sb.append("association= ").append(association.name).append("\n");
        sb.append("start= ").append(start).append("\n");
        sb.append("end= ").append(end).append("\n");
        return sb.toString();
    }
}
