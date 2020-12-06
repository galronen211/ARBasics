package com.example.arbasics;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ProfileAcitivity extends AppCompatActivity {

    String name;
    long games, score;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_acitivity);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Users/" + Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
        final boolean[] done = {false};
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!done[0]) {
                    name = (String) dataSnapshot.child("name").getValue();
                    games = (long) dataSnapshot.child("games").getValue();
                    score = (long) dataSnapshot.child("score").getValue();

                    TextView usrName = findViewById(R.id.usernameText);
                    usrName.setText(name);
                    TextView usrGames = findViewById(R.id.gamesText);
                    usrGames.setText("Games Played: " + games);
                    TextView usrScore = findViewById(R.id.scoreText);
                    usrScore.setText("Score: " + score);
                    TextView usrRank = findViewById(R.id.userrankText);
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

                    done[0] = true;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}