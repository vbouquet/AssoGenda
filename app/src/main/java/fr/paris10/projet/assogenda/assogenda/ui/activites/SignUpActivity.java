package fr.paris10.projet.assogenda.assogenda.ui.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import fr.paris10.projet.assogenda.assogenda.R;
import fr.paris10.projet.assogenda.assogenda.model.User;

public class SignUpActivity extends AppCompatActivity {

    protected EditText passwordEditText;
    protected EditText emailEditText;
    protected Button signUpButton;
    protected EditText firstNameEditText;
    protected EditText lastNameEditText;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize FirebaseAuth
        mFirebaseAuth = FirebaseAuth.getInstance();

        Log.i(this.getClass().getCanonicalName(),"Demarrage du Signup");

        passwordEditText = (EditText)findViewById(R.id.signup_password);
        emailEditText = (EditText)findViewById(R.id.signup_email);
        firstNameEditText = (EditText) findViewById(R.id.first_name);
        lastNameEditText = (EditText) findViewById(R.id.last_name);
        signUpButton = (Button)findViewById(R.id.signup_validate);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = passwordEditText.getText().toString();
                String email = emailEditText.getText().toString();

                password = password.trim();
                email = email.trim();

                if (password.isEmpty() || email.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                    builder.setMessage(R.string.signup_error_message)
                            .setTitle(R.string.signup_error_title)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        User newUserToInsert = new User(emailEditText.getText().toString(), firstNameEditText.getText().toString(), lastNameEditText.getText().toString());
                                        database.child(task.getResult().getUser().getUid()).setValue(newUserToInsert);
                                        startActivity(intent);
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                                        builder.setMessage(task.getException().getMessage())
                                                .setTitle(R.string.login_error_title)
                                                .setPositiveButton(android.R.string.ok, null);
                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                    }
                                }
                            });

                }
            }
        });
    }

}
