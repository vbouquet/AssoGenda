package fr.paris10.projet.assogenda.assogenda.ui.fragment;

import org.junit.Test;

import fr.paris10.projet.assogenda.assogenda.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.notNullValue;

public class AssociationMainFragmentTest {

    @Test
    public void testCreateAssociationButton() {

        onView(withId(R.id.fragment_association_main_button_create_association)).check(matches(notNullValue() ));
        onView(withId(R.id.fragment_association_main_button_create_association)).perform(click());
        onView(withId(R.id.fragment_create_association_name_title))
                .check(matches(withText((R.string.fragment_create_association_name_title))));
    }
}