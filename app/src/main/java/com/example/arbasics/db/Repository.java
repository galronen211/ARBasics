package com.example.arbasics.db;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.arbasics.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Repo;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class Repository {
    private static Repository instance = null;
    private static FirebaseAuth mAuth;

    private Boolean signedIn;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private Query leaderboard;
    private DatabaseReference userPath;
    private User currentUser;

    private Repository(){
        signedIn = false;
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        myRef = database.getReference("Users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                leaderboard = myRef.orderByChild("score");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if (mAuth.getCurrentUser() != null) {
            userPath = FirebaseDatabase.getInstance().getReference("Users/" + Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
            userPath.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    currentUser = snapshot.getValue(User.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public static Repository getInstance() {
        if (instance == null || mAuth.getCurrentUser() == null) {
            instance = new Repository();
        }
        return instance;
    }

    public Query getLeaderboard() {
        return leaderboard;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public Boolean createUser(String email, String pass, Activity activity) {
        try {
            mAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(activity, task -> {
                        if (task.isSuccessful()) {
                            userPath = FirebaseDatabase.getInstance().getReference("Users/" + Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
                            userPath.setValue(new User(email, 0));
                            signedIn = this.signInUser(email, pass, activity);
                        }
                    });
        } catch (Exception ignored) { }
        return signedIn;
    }

    public Boolean signInUser(String email, String pass, Activity activity) {
        try {
            mAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(activity, task -> {
                        if(task.isComplete()){
                            signedIn = true;
                        }
                    });
        } catch (Exception ignored) { }
        return signedIn;
    }

    public void signOut() {
        mAuth.signOut();
        signedIn = false;
    }

    public Boolean getSignedIn() {
        return signedIn;
    }
}