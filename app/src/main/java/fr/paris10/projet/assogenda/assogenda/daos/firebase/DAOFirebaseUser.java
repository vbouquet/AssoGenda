package fr.paris10.projet.assogenda.assogenda.daos.firebase;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import fr.paris10.projet.assogenda.assogenda.model.User;


public class DAOFirebaseUser {
    private static DAOFirebaseUser instance;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference database;

    private DAOFirebaseUser() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference("users");
    }

    public static DAOFirebaseUser getInstance() {
        if (instance == null)
            instance = new DAOFirebaseUser();
        return instance;
    }

    public boolean isLoggedIn() {
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        return mFirebaseUser != null;
    }

    public void signOut() {
        if (isLoggedIn())
            mFirebaseAuth.signOut();
    }

    public void createUser(String uid, String email, String firstName, String lastName) {
        User user = new User(email, firstName, lastName);
        database.child(uid).setValue(user);
        isLoggedIn();
    }

    public boolean validateUser(User user, String password) {
        return validateUser(user.email, password, user.firstName, user.lastName);
    }

    public boolean validateUser(final String email, final String password,
                                final String firstName, final String lastName) {

        return validateEmail(email) && validatePassword(password)
                && validateFirstName(firstName) && validateLastName(lastName);
    }

    public boolean validateUser(final String email, final String password) {
        return validateEmail(email) && validatePassword(password);
    }

    private boolean validatePassword(String password) {
        return password != null && !password.isEmpty() && password.length() >= 6;
    }

    private boolean validateEmail(String email) {
        return email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean validateFirstName(String firstName) {
        return firstName != null && !firstName.isEmpty() && firstName.length() >= 3;
    }

    private boolean validateLastName(String lastName) {
        return lastName != null && !lastName.isEmpty() && lastName.length() >= 3;
    }

    public String getCurrentUserId() {
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        return mFirebaseUser.getUid();
    }
}
