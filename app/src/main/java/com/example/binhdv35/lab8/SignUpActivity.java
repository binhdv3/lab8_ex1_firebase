package com.example.binhdv35.lab8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ktx.Firebase;

public class SignUpActivity extends AppCompatActivity {
    private EditText inputEmail, inputPassword;
    private Button btnSignIn, btnSignUp, btnResetPassword;
//    private ProgressBar progressBar;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();

//        btnSignIn = (Button) findViewById(R.id.sign_in_button);
//        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.pass);
//        progressBar = (ProgressBar) findViewById(R.id.progressBar);
//        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);

        btnSignUp = findViewById(R.id.btn_signup);
        btnSignUp.setOnClickListener( v -> {
            String email = inputEmail.getText().toString().trim();
            String pass = inputPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Enter email adress!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(pass)) {
                Toast.makeText(this, "Enter password!",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (pass.length() < 6) {
                Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this,
                    new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Toast.makeText(SignUpActivity.this,
                            "createUserWithEmail:onComplete:" + task.isSuccessful(),
                            Toast.LENGTH_SHORT).show();

                    if (!task.isSuccessful()) {
                        Toast.makeText(SignUpActivity.this,
                                "Authentication failed." + task.getException(),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                        finish();
                    }
                }
            });
        });
    }
}