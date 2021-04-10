package com.example.arbasics.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.arbasics.R;
import com.example.arbasics.db.Repository;
import com.example.arbasics.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.concurrent.Executor;

public class LoginActivity extends AppCompatActivity {

    private Repository repInstance = Repository.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void register(View view) {
        String email = ((EditText) findViewById(R.id.emailText))
                .getText().toString();
        String pass = ((EditText) findViewById(R.id.passText))
                .getText().toString();
        repInstance.createUser(email, pass, this);
        if (repInstance.getSignedIn()) {
            startActivity(new Intent(LoginActivity.this, HomepageActivity.class));
            finish();
        } else {
            Toast.makeText(
                    LoginActivity.this,
                    "Registration Failed ;(",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    public void login(View view) {
        String email = ((EditText) findViewById(R.id.emailText))
                .getText().toString();
        String pass = ((EditText) findViewById(R.id.passText))
                .getText().toString();

        repInstance.signInUser(email, pass, this);
        if (repInstance.getSignedIn()) {
            startActivity(new Intent(LoginActivity.this, HomepageActivity.class));
            finish();
        } else {
            Toast.makeText(
                    LoginActivity.this,
                    "Something Went Wrong...",
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}