package com.example.arbasics.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arbasics.R;
import com.example.arbasics.models.User;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Objects;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.LeaderViewHolder> {
    private ArrayList<User> users;
    private Context ctx;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public LeaderboardAdapter(ArrayList<User> users, Context ctx) {
        this.users = users;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public LeaderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ldrView = LayoutInflater.from(ctx).inflate(R.layout.item_leaderboard, parent, false);
        return new LeaderViewHolder(ldrView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull LeaderViewHolder holder, int position) {
        User currentUser = users.get(users.size() - position - 1);
        holder.name.setText(currentUser.getName());
        holder.rank.setText((position + 1) + "");
        holder.score.setText(currentUser.getScore() + " points");
        if (Objects.equals(Objects.requireNonNull(mAuth.getCurrentUser()).getEmail(), currentUser.getName())) {
            holder.layout.setBackgroundColor(Color.parseColor("#E8E8E8"));
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class LeaderViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView rank;
        public TextView score;
        public ConstraintLayout layout;

        public LeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_widget);
            rank = itemView.findViewById(R.id.rank_widget);
            score = itemView.findViewById(R.id.score_widget);
            layout = itemView.findViewById(R.id.itemConstraint);
        }
    }
}
