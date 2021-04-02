package com.example.testingfire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    EditText name, firstPass, secundPass, email;
    Button register;
    TextView login;
    ProgressBar progress;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.emailofthesession);
        firstPass = findViewById(R.id.passwordLogin);
        secundPass = findViewById(R.id.secundpass);
        email = findViewById(R.id.emailLogin);
        login = findViewById(R.id.createNew);
        progress = findViewById(R.id.progressBar);
        register = findViewById(R.id.loginButton);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String email1 = email.getText().toString().trim();
                String pass = firstPass.getText().toString().trim();


                if (TextUtils.isEmpty(email1)) {
                    email.setText("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(pass)) {
                    firstPass.setText("Password is required");
                    return;
                }

                if (!firstPass.getText().toString().equals(secundPass.getText().toString())) {

                    firstPass.setHint("has to be the same");
                    //firstPass.setText("Password has to be the same");
                    secundPass.setHint("has to be the same");
                    return;
                }

                if (pass.length() < 8) {
                    firstPass.setText("Password has to be longer");
                    return;
                }

                progress.setVisibility(View.VISIBLE);

                firebaseAuth.createUserWithEmailAndPassword(email1, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "User created!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        } else {
                            Toast.makeText(Register.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    }
                });


            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));


            }
        });


    }
}