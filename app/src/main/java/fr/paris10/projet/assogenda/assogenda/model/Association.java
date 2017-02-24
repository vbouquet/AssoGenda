package fr.paris10.projet.assogenda.assogenda.model;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Association {
    public String id;
    public String name;
    public String description;
    public String university;
    public int logo;
    public Date startSubscription;
    public Date endSubscription;
    public User president;

    public Association() {
    }

    public Association(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Define how data will be stored in database
     */
    @Exclude
    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("university", university);
        result.put("description", description);
        result.put("logo", logo);
        result.put("start", startSubscription);
        result.put("end", endSubscription);
        result.put("president", president);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("name= ").append(name).append("\n");
        sb.append("description= ").append(description).append("\n");
        return sb.toString();
    }
}
