package com.example.arbasics;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

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
        CurrentUserSingleton.getInstance().ConnectSingleton();
        CurrentUserSingleton.getInstance().setLogout(false);

        ImageView playBtn = findViewById(R.id.playPicture);
        playBtn.setOnClickListener(v -> {
            play();
        });
        ImageView leaderBtn = findViewById(R.id.leaderPicture);
        leaderBtn.setOnClickListener(v -> {
            leader();
        });
        ImageView logoutBtn = findViewById(R.id.logOutPicture);
        logoutBtn.setOnClickListener(v -> {
            logout();
        });
        ImageView profileBtn = findViewById(R.id.profilePicture);
        profileBtn.setOnClickListener(v -> {
            viewProfile();
        });
        TextView name = findViewById(R.id.usernameText);
        name.setText(Objects.requireNonNull(mAuth.getCurrentUser()).getEmail());

        ImageView infoBtn = findViewById(R.id.infoPicture);
        infoBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, info.class));
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
        CurrentUserSingleton.getInstance().setLogout(true);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    public void viewProfile() {
        startActivity(new Intent(this, ProfileAcitivity.class));
    }
}