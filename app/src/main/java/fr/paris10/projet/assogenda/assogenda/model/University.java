package fr.paris10.projet.assogenda.assogenda.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by valentin on 22/01/17.
 */
public class University {
    public String id;
    public String name;
    public String adress;
    public String city;
    public String zipCode;

    public University() {
    }

    public University(String name, String adress, String city, String zipCode) {
        this.name = name;
        this.adress = adress;
        this.city = city;
        this.zipCode = zipCode;
    }

    /**
     * Define how data will be stored in database
     */
    @Exclude
    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("adress", adress);
        result.put("city", city);
        result.put("zipcode", zipCode);

        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("University");
        sb.append("name= ").append(name).append("\n");
        sb.append("adress= ").append(adress).append("\n");
        sb.append("city= ").append(city).append("\n");
        sb.append("zipCode= ").append(zipCode).append("\n");
        return sb.toString();
    }
}
