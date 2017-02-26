package fr.paris10.projet.assogenda.assogenda.daos;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.paris10.projet.assogenda.assogenda.model.User;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class DAOUserTest {
    private Context appContext;
    private DAOUser daoUser;

    @Before
    public void setUp() throws Exception {
        this.appContext  = InstrumentationRegistry.getTargetContext();
        this.daoUser     = DAOUser.getInstance();
    }

    @After
    public void tearDown() throws Exception {
        this.appContext  = null;
        this.daoUser     = null;
    }

    @Test
    public void testInitDAO() throws Exception {
        assertNotNull(daoUser);
    }

    @Test
    public void testValidateUser() throws Exception {
        final String userCreate     = "User should have been created";
        final String userNotCreate  = "User should have not been created";
        User user = new User("test@mail.fr", "test", "supertest");
        assertFalse(userNotCreate, daoUser.validateUser(null, null, null, null));
        assertTrue(userCreate, daoUser.validateUser(user, "superpassword"));
        assertFalse(userNotCreate, daoUser.validateUser(user, null));
        user.email="test.fr";
        assertFalse(userNotCreate, daoUser.validateUser(user, "superpassword"));
        user.email=null;
        assertFalse(userNotCreate, daoUser.validateUser(user, "superpassword"));
        assertFalse(userNotCreate, daoUser.validateUser((String) null, null));
        assertFalse(userNotCreate, daoUser.validateUser("fakeemail.fr", "password"));
        assertFalse(userNotCreate, daoUser.validateUser("test@mail.fr", "123"));
        assertTrue(userCreate, daoUser.validateUser("test@mail.fr", "password"));
    }

    @Test
    public void testSignInAndOut() throws Exception {
        if (daoUser.isLoggedIn())
            daoUser.signOut();
        assertFalse(daoUser.isLoggedIn());
    }

}