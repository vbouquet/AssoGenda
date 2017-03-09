package fr.paris10.projet.assogenda.assogenda.ui.activites;

import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import fr.paris10.projet.assogenda.assogenda.BuildConfig;
import fr.paris10.projet.assogenda.assogenda.R;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk=22)

public class LoginActivityRoboTest {
    private LoginActivity activity;
    private final String textEmail = "test@mail.fr";
    private final String textPwd = "strongpassword";

    @Before
    public void setUp() throws Exception {
        FirebaseApp.initializeApp(RuntimeEnvironment.application.getApplicationContext());
        activity = Robolectric.buildActivity(LoginActivity.class).create().get();
    }

    @Test
    public void shouldNotBeNull() throws Exception {
        assertNotNull(activity);
    }

    @Test
    public void fillFields() throws Exception {
        TextView email = (TextView) activity.findViewById(R.id.activity_login_email);
        TextView password = (TextView) activity.findViewById(R.id.activity_login_password);
        assertNotNull(email);
        assertNotNull(password);
        email.setText(textEmail);
        password.setText(textPwd);
        assertEquals(email.getText().toString(), textEmail);
        assertEquals(password.getText().toString(), textPwd);
    }

    @Test
    public void shouldNotSignedIn() throws Exception {
        activity.signIn(textEmail, textPwd);
        assertEquals(null, shadowOf(activity).peekNextStartedActivity());
        TextView email = (TextView) activity.findViewById(R.id.activity_login_email);
        TextView password = (TextView) activity.findViewById(R.id.activity_login_password);
        Button logInButton = (Button) activity.findViewById(R.id.activity_login_validate);
        assertNotNull(logInButton);
        assertTrue(logInButton.performClick());
        assertEquals(null, shadowOf(activity).peekNextStartedActivity());
    }

}
