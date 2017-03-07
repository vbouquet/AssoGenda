package fr.paris10.projet.assogenda.assogenda.daos;

import fr.paris10.projet.assogenda.assogenda.daos.firebase.DAOFirebaseAssociation;

public class DAOAssociation {

    private DAOFirebaseAssociation daoFirebaseAssociation;
    private static DAOAssociation instance = null;

    public DAOAssociation() {
        daoFirebaseAssociation = DAOFirebaseAssociation.getInstance();
    }

    public static DAOAssociation getInstance() {
        if(instance == null) {
            instance = new DAOAssociation();
        }
        return instance;
    }

    public void createAssociation(String associationName, String associationUniversity, String associationDescription, String logo) {
        daoFirebaseAssociation.createAssociation(associationName, associationUniversity, associationDescription, logo);
    }

    public boolean validateAssociationName(String associationName) {
        return daoFirebaseAssociation.validateAssociationName(associationName);
    }

    public boolean validateAssociationUniversity(String associationUniversity) {
        return daoFirebaseAssociation.validateAssociationUniversity(associationUniversity);
    }

    public boolean validateAssociationDescription(String associationDescription) {
        return daoFirebaseAssociation.validateAssociationDescription(associationDescription);
    }

    public boolean validateAssociation(String associationName, String associationUniversity, String associationDescription) {
        return daoFirebaseAssociation.validateAssociation(associationName, associationUniversity, associationDescription);
    }
}
