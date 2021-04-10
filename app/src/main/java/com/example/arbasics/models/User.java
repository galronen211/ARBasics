package com.example.arbasics.models;

public class User {
    private String name;
    private long score, games;

    public User(String name, long score) {
        this.name = name;
        this.score = score;
    }

    public User() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public long getGames() {
        return games;
    }

    public void setGames(long games) {
        this.games = games;
    }
}
