package com.example.binhdv35.lab8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private Button btnSignup, btnLogin, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

//        if (auth.getCurrentUser() != null) {
//            startActivity(new Intent(LoginActivity.this,
//                    MainActivity.class));
//            finish();
//        }

        inputEmail = (EditText) findViewById(R.id.lg_email);
        inputPassword = (EditText) findViewById(R.id.lg_password);
        btnSignup = (Button) findViewById(R.id.btn_lg_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);

        btnReset.setOnClickListener(v -> {
            startActivity(new Intent(this, ResetPasswordActivity.class));
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,
                        SignUpActivity.class));
            }
        });

        btnLogin.setOnClickListener(v -> {
            String email = inputEmail.getText().toString().trim();
            final String pass = inputPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getApplicationContext(), "Enter email" +
                        "address!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(pass)) {
                Toast.makeText(getApplicationContext(), "Enter password!",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this,
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                if (pass.length() < 6) {
                                    inputPassword.setError(getString(R.string.minimum_password));
                                }else {
                                    Toast.makeText(LoginActivity.this,
                                            getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                }
                            }else {
                                Intent intent = new
                                        Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
        });
    }
}