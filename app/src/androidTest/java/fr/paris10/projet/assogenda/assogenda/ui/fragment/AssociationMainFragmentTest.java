package fr.paris10.projet.assogenda.assogenda.ui.fragment;

import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import fr.paris10.projet.assogenda.assogenda.R;
import fr.paris10.projet.assogenda.assogenda.ui.activites.AssociationDashboardActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

public class AssociationMainFragmentTest {

    @Rule
    public IntentsTestRule<AssociationDashboardActivity> mActivityRule = new IntentsTestRule<>(AssociationDashboardActivity.class);

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCreateAssociationButton() {

        onView(withId(R.id.fragment_association_main_button_create_association)).check(matches(notNullValue() ));
        onView(withId(R.id.fragment_association_main_button_create_association)).perform(click());
        onView(withId(R.id.fragment_create_association_name_title))
                .check(matches(withText((R.string.fragment_create_association_name_title))));
    }
}