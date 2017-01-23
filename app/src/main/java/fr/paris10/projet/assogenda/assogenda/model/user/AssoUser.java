package fr.paris10.projet.assogenda.assogenda.model.user;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import fr.paris10.projet.assogenda.assogenda.model.association.Association;
import fr.paris10.projet.assogenda.assogenda.model.University;

public class AssoUser extends BasicUser {
    private Map<Association, Role> associations;

    public AssoUser(int id, String firstname, String lastname, String email,
                    String username, University university, Preference preference,
                    Map<Association, Role> associations) {
        super(id, firstname, lastname, email, username, university, preference);
        this.associations = associations;
    }

    public AssoUser(int id, String firstname, String lastname, String email,
                    String username, University university, Preference preference,
                    Association association, String role, String nickname) throws AssoMemberException {
        super(id, firstname, lastname, email, username, university, preference);
        this.associations = new HashMap<>();
        if (RoleENUM.PRESIDENT.name().equalsIgnoreCase(role)) {
            Role r = new Role(RoleENUM.PRESIDENT, nickname);
            associations.put(association, r);
        }
        if (RoleENUM.BASICMEMBER.name().equalsIgnoreCase(role)) {
            Role r = new Role(RoleENUM.BASICMEMBER, nickname);
            associations.put(association, r);
        }
        if (associations.isEmpty())
            throw new AssoMemberException(
                    String.format("Role %s of associative member %s %s not recognised",
                            role, firstname, lastname));
    }

    public Set<Association> getAssociations() {
        return associations.keySet();
    }

    public void addAssociation(Association association, RoleENUM role, String nickName) {
        if (!associations.containsKey(association)) {
            Role r = new Role(role, nickName);
            associations.put(association, r);
        }
    }

    public boolean isAMemberOf(Association association) {
        return associations.containsKey(association);
    }
}
