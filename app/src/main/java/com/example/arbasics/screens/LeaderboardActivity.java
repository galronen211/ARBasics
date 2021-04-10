package com.example.arbasics.screens;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arbasics.db.Repository;
import com.example.arbasics.utils.LeaderboardAdapter;
import com.example.arbasics.R;
import com.example.arbasics.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LeaderboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);


        LeaderboardAdapter ldrAdapter = new LeaderboardAdapter(new ArrayList<>(), this);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(ldrAdapter);

        LaederboardPresenter p = new LaederboardPresenter(ldrAdapter);
    }
}