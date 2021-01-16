package com.example.arbasics;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileAcitivity extends AppCompatActivity {

    private final CurrentUserSingleton instance = CurrentUserSingleton.getInstance();
    private final String name = instance.getName();
    private final long games = instance.getGames();
    private final long score = instance.getScore();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile_acitivity);

        TextView usrName = findViewById(R.id.homeUsernameTextView);
        usrName.setText(name);
        TextView usrGames = findViewById(R.id.profileGamesTextView);
        usrGames.setText("Games Played: " + games);
        TextView usrScore = findViewById(R.id.profileScoreTextView);
        usrScore.setText("Score: " + score);
        TextView usrRank = findViewById(R.id.profileCurrentRankTextView);
        if (score < 1000) {
            usrRank.setText("Bronze Balloon Popper");
        } else if (score < 2000) {
            usrRank.setText("Silver Balloon Popper");
        } else if (score < 3000) {
            usrRank.setText("Gold Balloon Popper");
        } else if (score < 4000) {
            usrRank.setText("Platinum Balloon Popper");
        } else if (score < 5000) {
            usrRank.setText("Diamond Balloon Popper");
        } else {
            usrRank.setText("Master Balloon Popper");
        }
    }
}