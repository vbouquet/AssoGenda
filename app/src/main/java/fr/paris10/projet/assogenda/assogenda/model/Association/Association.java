package fr.paris10.projet.assogenda.assogenda.model.Association;

import java.util.Date;
import java.util.Set;

import fr.paris10.projet.assogenda.assogenda.model.University;
import fr.paris10.projet.assogenda.assogenda.model.User.User;

public class Association {
    private int id;
    private String name;
    private String description;
    private String logo; // Path to image on server
    private Date startSubscription;
    private Date endSubscription;
    private User president;
    private Set<University> universities;
    private Set<User> members;
    private AssociationState state;

    public Association(int id, String name, String description,
                       String logo, Date startSubscription, Date endSubscription,
                       User president, Set<University> universities, Set<User> members) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.logo = logo;
        this.startSubscription = startSubscription;
        this.endSubscription = endSubscription;
        this.president = president;
        this.universities = universities;
        this.members = members;

        // Init state, depends on current subscription
        if (!verifSubscription())
            this.state = new SubOffState();
        else
            this.state = new SubOnState();

    }

    private boolean verifSubscription() {
        return new Date().after(endSubscription);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Date getStartSubscription() {
        return startSubscription;
    }

    public void setStartSubscription(Date startSubscription) {
        this.startSubscription = startSubscription;
    }

    public Date getEndSubscription() {
        return endSubscription;
    }

    public void setEndSubscription(Date endSubscription) {
        this.endSubscription = endSubscription;
    }

    public User getPresident() {
        return president;
    }

    public void setPresident(User president) {
        this.president = president;
    }

    public Set<University> getUniversities() {
        return universities;
    }

    public void setUniversities(Set<University> universities) {
        this.universities = universities;
    }

    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }
}
