package com.example.arbasics;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomepageActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        mAuth = FirebaseAuth.getInstance();

        Button playBtn = findViewById(R.id.playButton);
        playBtn.setOnClickListener(v -> {
            play();
        });
        Button leaderBtn = findViewById(R.id.leaderButton);
        leaderBtn.setOnClickListener(v -> {
            leader();
        });
        Button logoutBtn = findViewById(R.id.logoutButton);
        logoutBtn.setOnClickListener(v -> {
            logout();
        });
        ImageView profileBtn = findViewById(R.id.profilePicture);
        profileBtn.setOnClickListener(v -> {
            viewProfile();
        });

    }

    public void play() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void leader() {
        startActivity(new Intent(this, LeaderboardActivity.class));
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    public void viewProfile() {
        startActivity(new Intent(this, ProfileAcitivity.class));
    }
}