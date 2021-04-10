package com.example.arbasics.screens;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.arbasics.R;
import com.example.arbasics.db.Repository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class HomepageActivity extends AppCompatActivity {

    private Repository instance = Repository.getInstance();

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onStart() {
        super.onStart();
        if (!instance.getSignedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        ImageView playBtn = findViewById(R.id.homePlayImageView);
        playBtn.setOnClickListener(v -> {
            play();
        });
        ImageView leaderBtn = findViewById(R.id.homeLeaderboardImageView);
        leaderBtn.setOnClickListener(v -> {
            showLeaderboard();
        });
        ImageView logoutBtn = findViewById(R.id.homeLogOutImageView);
        logoutBtn.setOnClickListener(v -> {
            logout();
        });
        ImageView profileBtn = findViewById(R.id.homeProfileImageView);
        profileBtn.setOnClickListener(v -> {
            viewProfile();
        });

        ImageView infoBtn = findViewById(R.id.homeInfoImageView);
        infoBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, GameInfoActivity.class));
        });

    }

    public void play() {
        startActivity(new Intent(this, GameActivity.class));
        finish();
    }

    public void showLeaderboard() {
        startActivity(new Intent(this, LeaderboardActivity.class));
    }

    public void logout() {
        instance.signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    public void viewProfile() {
        startActivity(new Intent(this, ProfileAcitivity.class));
    }
}