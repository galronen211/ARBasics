package com.example.arbasics;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FinishActivity extends AppCompatActivity {

    private CurrentUserSingleton instance = CurrentUserSingleton.getInstance();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);


        Button againBtn = findViewById(R.id.playAgainButton);
        againBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

        Button homeBtn = findViewById(R.id.backHomeButton);
        homeBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, HomepageActivity.class));
            finish();
        });


        TextView score = findViewById(R.id.deathScoreTextView);
        TextView rank = findViewById(R.id.deathNewRankTextView);
        long scoreVal = instance.getScore();

        score.setText("New Score: " + scoreVal);
        if (scoreVal < 1000) {
            rank.setText("Bronze Balloon Popper");
        } else if (scoreVal < 2000) {
            rank.setText("Silver Balloon Popper");
        } else if (scoreVal < 3000) {
            rank.setText("Gold Balloon Popper");
        } else if (scoreVal < 4000) {
            rank.setText("Platinum Balloon Popper");
        } else if (scoreVal < 5000) {
            rank.setText("Diamond Balloon Popper");
        } else {
            rank.setText("Master Balloon Popper");
        }
    }
}