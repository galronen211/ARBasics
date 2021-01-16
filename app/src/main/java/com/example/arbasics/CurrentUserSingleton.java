package com.example.arbasics;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.EventListener;
import java.util.Objects;

public class CurrentUserSingleton {

    private long score;
    private long games;
    private String name;
    private boolean logout = false;
    private static CurrentUserSingleton instance = new CurrentUserSingleton();

    public void ConnectSingleton() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Users/" + Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!logout) {
                    name = (String) snapshot.child("name").getValue();
                    score = (long) snapshot.child("score").getValue();
                    games = (long) snapshot.child("games").getValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setLogout(boolean logout) {
        this.logout = logout;
    }

    public static CurrentUserSingleton getInstance() {
        return instance;
    }

    public long getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public long getGames() {
        return games;
    }

    private CurrentUserSingleton() {
        ConnectSingleton();
    }

}
