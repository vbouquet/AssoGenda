package fr.paris10.projet.assogenda.assogenda.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Event {

    public String id;
    public String name;
    public String start;
    public String end;
    public String description;
    public String location;
    public String type;
    public float price;
    public int bail;
    public int seat_number;
    public int seat_free;
    public Association association;
    public int logo;

    public Event(){
    }

    public Event(String name, String start, String end, String type, String description, String location,
                 float price, int bail, int seat_number, Association association, int logo){
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

    public Event(String name, String start, String end, String type, String location,
                 float price, int seat_number, String description){
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
        result.put("price", price);
        result.put("seats number", seat_number);
        result.put("seats free", seat_free);
        result.put("start", start);
        result.put("end", end);
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