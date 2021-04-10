package com.example.arbasics.screens;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.arbasics.R;
import com.example.arbasics.db.Repository;
import com.example.arbasics.models.User;

public class FinishActivity extends AppCompatActivity {

    private User instance = Repository.getInstance().getCurrentUser();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);


        Button againBtn = findViewById(R.id.playAgainButton);
        againBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, GameActivity.class));
            finish();
        });

        Button homeBtn = findViewById(R.id.backHomeButton);
        homeBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, HomepageActivity.class));
            finish();
        });


        TextView death_score_text_view = findViewById(R.id.deathScoreTextView);
        TextView deathNewRankTV = findViewById(R.id.deathNewRankTextView);
        long scoreVal = instance.getScore();

        death_score_text_view.setText("New Score: " + scoreVal);
        if (scoreVal < 1000) {
            deathNewRankTV.setText("Bronze Balloon Popper");
        } else if (scoreVal < 2000) {
            deathNewRankTV.setText("Silver Balloon Popper");
        } else if (scoreVal < 3000) {
            deathNewRankTV.setText("Gold Balloon Popper");
        } else if (scoreVal < 4000) {
            deathNewRankTV.setText("Platinum Balloon Popper");
        } else if (scoreVal < 5000) {
            deathNewRankTV.setText("Diamond Balloon Popper");
        } else {
            deathNewRankTV.setText("Master Balloon Popper");
        }
    }
}