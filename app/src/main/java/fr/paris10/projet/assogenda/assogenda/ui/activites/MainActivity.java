package fr.paris10.projet.assogenda.assogenda.ui.activites;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import fr.paris10.projet.assogenda.assogenda.R;
import fr.paris10.projet.assogenda.assogenda.model.User;

public class MainActivity extends AppCompatActivity {

    //TODO Delete this when connect/signup is integrated (+import)
    //Create a fake user to create a link between user and association
    private FirebaseAuth mFirebaseAuth;
    private String email = "123@123.fr";
    private String password = "test123";
    private String firstName = "Pascal";
    private String lastName = "Aubistro";

    public void createFakeUser() {

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            User newUserToInsert = new User(email, firstName, lastName);
                            DatabaseReference base = FirebaseDatabase.getInstance().getReference("users");
                            base.child(task.getResult().getUser().getUid()).setValue(newUserToInsert);
                            startActivity(intent);
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setMessage(task.getException().getMessage())
                                    .setTitle(R.string.login_error_title)
                                    .setPositiveButton(android.R.string.ok, null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                });
    }

    private void connectFakeUser() {

        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setMessage(task.getException().getMessage())
                                    .setTitle(R.string.login_error_title)
                                    .setPositiveButton(android.R.string.ok, null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                });
    }
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(this.getClass().getCanonicalName(), "Entre dans onCreate");

        //TODO Delete this when connect/signup is integrated
        //createFakeUser();

        mFirebaseAuth = FirebaseAuth.getInstance();
        //mFirebaseAuth.signOut();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // User is signed in
            Log.i(this.getClass().getCanonicalName(), "User connected : " + user.getUid());
            Button associationButton = (Button) findViewById(R.id.activity_main_button_association);
            associationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startAssociationActivity();
                }
            });
        } else {
            // No user is signed in
            connectFakeUser();
            Log.i(this.getClass().getCanonicalName(), "User not connected");
        }
        //
    }

    public void startAssociationActivity() {
        Intent intent = new Intent(this, AssociationDashboardActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(this.getClass().getCanonicalName(), "Entre dans onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(this.getClass().getCanonicalName(), "Entre dans onResume");
    }
}