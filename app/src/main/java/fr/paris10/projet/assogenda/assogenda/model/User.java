package fr.paris10.projet.assogenda.assogenda.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class User {
    public String id;
    public String email;
    public String firstName;
    public String lastName;
    public String nickName;
    public University university;
    public Map<String, Association> following;
    public Boolean isAssoMember = false;

    /**
     * Constructor with no arguments for firebase query
     */
    public User() {
    }

    /**
     * Minimum default values for user
     * @param email
     * @param firstName
     * @param lastName
     */
    public User(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.following = new HashMap<>();
    }

    /**
     * Define how data will be stored in database
     */
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("firstname", firstName);
        result.put("lastname", lastName);
        result.put("email", email);
        result.put("nickname", nickName);
        result.put("university", university.name);
        result.put("followed", following);
        result.put("assomember", isAssoMember);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("User : ");
        sb.append("firstname= ").append(firstName).append("\n");
        sb.append("lastbame= ").append(lastName).append("\n");
        sb.append("email= ").append(email).append("\n");
        return sb.toString();
    }
}
