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
    public String logo;
    public Date startSubscription;
    public Date endSubscription;
    public String president;
    public Boolean followed = false;

    public Association() {
    }

    public Association(String name, String university, String description, String president, String logo) {
        this.name = name;
        this.university = university;
        this.description = description;
        this.president = president;
        this.logo = logo;
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
        sb.append("university= ").append(university).append("\n");
        sb.append("logo= ").append(logo).append("\n");
        sb.append("president= ").append(president).append("\n");
        return sb.toString();
    }
}
