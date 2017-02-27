package fr.paris10.projet.assogenda.assogenda.daos;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.paris10.projet.assogenda.assogenda.model.Association;

import static org.junit.Assert.*;

public class DAOAssociationTest {

    private Context appContext;
    private DAOAssociation daoAssociation;

    @Before
    public void setUp() throws Exception {
        this.appContext  = InstrumentationRegistry.getTargetContext();
        this.daoAssociation = DAOAssociation.getInstance();
    }

    @After
    public void tearDown() throws Exception {
        this.appContext = null;
        this.daoAssociation = null;
    }

    @Test
    public void testInitDAO() throws Exception {
        assertNotNull(daoAssociation);
    }

    //String name, String university, String description, String president, String logo
    @Test
    public void testValidateAssociation() throws Exception {
        Association association = new Association("Name", "University", "Description", "President", "logo");
        assertFalse(daoAssociation.validateAssociation(null, null, null));
        assertFalse(daoAssociation.validateAssociationName(null));

        //Test if association name contains more than 2 caracters
        assertFalse(daoAssociation.validateAssociationName("Na"));

        assertFalse(daoAssociation.validateAssociationUniversity(null));
        assertFalse(daoAssociation.validateAssociationUniversity("Un"));

        assertFalse(daoAssociation.validateAssociationDescription(null));
        assertFalse(daoAssociation.validateAssociationDescription("De"));

        assertTrue(daoAssociation.validateAssociation("Name", "University", "Description"));
        assertTrue(daoAssociation.validateAssociationName("Name"));
        assertTrue(daoAssociation.validateAssociationUniversity("University"));
        assertTrue(daoAssociation.validateAssociationDescription("Description"));
    }
}