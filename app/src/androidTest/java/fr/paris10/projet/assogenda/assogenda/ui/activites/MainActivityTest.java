package fr.paris10.projet.assogenda.assogenda.ui.activites;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.paris10.projet.assogenda.assogenda.R;
import fr.paris10.projet.assogenda.assogenda.daos.DAOUser;

import static org.junit.Assert.*;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testLogOutButton() {
        DAOUser daoUser = DAOUser.getInstance();
        if (daoUser.isLoggedIn()) {
            onView(withId(R.id.activity_main_logout_button)).perform(click());
            assertFalse(daoUser.isLoggedIn());
        }
    }
}