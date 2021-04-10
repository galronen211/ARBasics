package com.example.arbasics.screens;

import androidx.annotation.NonNull;

import com.example.arbasics.db.Repository;
import com.example.arbasics.models.User;
import com.example.arbasics.utils.LeaderboardAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LaederboardPresenter {
    private ArrayList<User> users = new ArrayList<>();

    public LaederboardPresenter(LeaderboardAdapter ldrAdapter) {
        Repository repInstance = Repository.getInstance();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        User user = snapshot.getValue(User.class);
                        users.add(user);
                    }
                    ldrAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        repInstance.getLeaderboard().addListenerForSingleValueEvent(valueEventListener);
    }
}
