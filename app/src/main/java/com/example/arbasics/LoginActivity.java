package com.example.arbasics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference database;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("Users/");
    }

    public void register(View view) {
        String email = ((EditText) findViewById(R.id.emailText))
                .getText().toString();
        String pass = ((EditText) findViewById(R.id.passText))
                .getText().toString();

        try {
            mAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            database = FirebaseDatabase.getInstance().getReference("Users/" + Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
                            database.setValue(new User(email, 0));
                            startActivity(new Intent(LoginActivity.this, HomepageActivity.class));
                            finish();
                        } else {
                            Toast.makeText(
                                    LoginActivity.this,
                                    "Registration Failed ;(",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    });
        } catch (Exception e) {
            Toast.makeText(
                    LoginActivity.this,
                    "Something went wrong... Try again",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    public void login(View view) {
        String email = ((EditText) findViewById(R.id.emailText))
                .getText().toString();
        String pass = ((EditText) findViewById(R.id.passText))
                .getText().toString();

        try {
            mAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(LoginActivity.this, HomepageActivity.class));
                            finish();
                        } else {
                            // login failed
                            Toast.makeText(
                                    LoginActivity.this,
                                    "Email/Password Incorrect",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    });
        } catch (Exception e) {
            Toast.makeText(
                    LoginActivity.this,
                    "Something went wrong... Try again",
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}