package com.example.arbasics.screens;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.arbasics.R;
import com.example.arbasics.db.Repository;
import com.example.arbasics.models.User;

public class ProfileAcitivity extends AppCompatActivity {

    private final User instance = Repository.getInstance().getCurrentUser();
    private final String name = instance.getName();
    private final long games = instance.getGames();
    private final long score = instance.getScore();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        TextView profileUsernameTv = findViewById(R.id.homeUsernameTextView);
        profileUsernameTv.setText(name);
        TextView profileGamesTv = findViewById(R.id.profileGamesTextView);
        profileGamesTv.setText("Games Played: " + games);
        TextView profileScoreTv = findViewById(R.id.profileScoreTextView);
        profileScoreTv.setText("Score: " + score);
        TextView profileRankTv = findViewById(R.id.profileCurrentRankTextView);
        if (score < 1000) {
            profileRankTv.setText("Bronze Balloon Popper");
        } else if (score < 2000) {
            profileRankTv.setText("Silver Balloon Popper");
        } else if (score < 3000) {
            profileRankTv.setText("Gold Balloon Popper");
        } else if (score < 4000) {
            profileRankTv.setText("Platinum Balloon Popper");
        } else if (score < 5000) {
            profileRankTv.setText("Diamond Balloon Popper");
        } else {
            profileRankTv.setText("Master Balloon Popper");
        }
    }
}