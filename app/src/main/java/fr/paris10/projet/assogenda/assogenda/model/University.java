package fr.paris10.projet.assogenda.assogenda.model;

import java.util.Set;

import fr.paris10.projet.assogenda.assogenda.model.Association.Association;
import fr.paris10.projet.assogenda.assogenda.model.User.User;

/**
 * Created by valentin on 22/01/17.
 */
public class University {
    private int id;
    private String name;
    private String adress;
    private String city;
    private String zipCode;
    private Set<Association> associations;
    private Set<User> users;

    public University(int id, String name, String adress, String city,
                      String zipCode, Set<Association> associations,
                      Set<User> users) {
        this.id = id;
        this.name = name;
        this.adress = adress;
        this.city = city;
        this.zipCode = zipCode;
        this.associations = associations;
        this.users = users;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Set<Association> getAssociations() {
        return associations;
    }

    public void setAssociations(Set<Association> associations) {
        this.associations = associations;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        if (!users.contains(user))
            users.add(user);
    }

    public void clearUsers() {
        users.clear();
    }
}
