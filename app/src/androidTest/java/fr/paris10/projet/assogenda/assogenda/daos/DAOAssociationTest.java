package fr.paris10.projet.assogenda.assogenda.daos;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DAOAssociationTest {

    private DAOAssociation daoAssociation;

    @Before
    public void setUp() throws Exception {
        this.daoAssociation = DAOAssociation.getInstance();
    }

    @After
    public void tearDown() throws Exception {
        this.daoAssociation = null;
    }

    @Test
    public void testInitDAO() throws Exception {
        assertNotNull(daoAssociation);
    }

    @Test
    public void testValidateAssociation() throws Exception {
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