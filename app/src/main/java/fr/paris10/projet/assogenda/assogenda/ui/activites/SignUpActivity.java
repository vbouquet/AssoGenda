package fr.paris10.projet.assogenda.assogenda.ui.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import fr.paris10.projet.assogenda.assogenda.R;
import fr.paris10.projet.assogenda.assogenda.daos.DAOUser;

public class SignUpActivity extends AppCompatActivity {

    protected EditText passwordEditText;
    protected EditText emailEditText;
    protected Button signUpButton;
    protected EditText firstNameEditText;
    protected EditText lastNameEditText;

    private FirebaseAuth mFirebaseAuth;
    private DAOUser daoUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        daoUser         = DAOUser.getInstance();
        mFirebaseAuth   = FirebaseAuth.getInstance();

        passwordEditText    = (EditText) findViewById(R.id.activity_sign_up_password);
        emailEditText       = (EditText) findViewById(R.id.activity_sign_up_email);
        firstNameEditText   = (EditText) findViewById(R.id.activity_sign_up_first_name);
        lastNameEditText    = (EditText) findViewById(R.id.activity_sign_up_last_name);
        signUpButton        = (Button) findViewById(R.id.activity_sign_up_validate);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String password   = passwordEditText.getText().toString().trim();
                final String email      = emailEditText.getText().toString().trim();
                final String firstName  = firstNameEditText.getText().toString().trim();
                final String lastName   = lastNameEditText.getText().toString().trim();

                if ( !daoUser.validateUser(email, password, firstName, lastName)) {
                    showAlertDialogError(R.string.signup_error_title,
                            getResources().getString(R.string.signup_error_message));
                } else {
                    signUp(email, password, firstName, lastName);
                }

            }
        });
    }

    public void showAlertDialogError(int title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setTitle(title)
                .setPositiveButton(android.R.string.ok, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void signUp(final String email, final String password,
                           final String firstName, final String lastName) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            daoUser.createUser(task.getResult().getUser().getUid(), email, firstName, lastName);
                            startActivity(intent);
                        } else {
                            showAlertDialogError(R.string.signup_error_title, task.getException().getMessage());
                        }
                    }
                });
    }


}
