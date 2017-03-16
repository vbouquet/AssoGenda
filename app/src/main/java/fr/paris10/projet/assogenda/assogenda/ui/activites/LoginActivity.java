package fr.paris10.projet.assogenda.assogenda.ui.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import fr.paris10.projet.assogenda.assogenda.R;
import fr.paris10.projet.assogenda.assogenda.daos.DAOUser;

public class LoginActivity extends AppCompatActivity {
    protected EditText  emailEditText;
    protected EditText  passwordEditText;
    protected Button    logInButton;
    protected TextView  signUpTextView;

    private FirebaseAuth mFirebaseAuth;
    private DAOUser daoUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        daoUser = DAOUser.getInstance();

        signUpTextView      = (TextView) findViewById(R.id.activity_login_to_activity_sign_up);
        emailEditText       = (EditText) findViewById(R.id.activity_login_email);
        passwordEditText    = (EditText) findViewById(R.id.activity_login_password);
        logInButton         = (Button) findViewById(R.id.activity_login_validate);

        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = emailEditText.getText().toString().trim();
                final String password = passwordEditText.getText().toString().trim();

                if ( !daoUser.validateUser(email, password)) {
                    showAlertDialogError(R.string.login_error_title,
                            getResources().getString(R.string.login_error_message));
                } else {
                    signIn(email, password);
                }
            }
        });
    }

    public void signIn(final String email, final String password) {
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            showAlertDialogError(R.string.login_error_title,
                                    task.getException().getMessage());
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


    protected void loadCreateEventView(){
        Intent intent = new Intent(this, CreateEventActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
