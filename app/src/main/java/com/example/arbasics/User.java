package com.example.arbasics;

public class User {
    private String name;
    private long games;
    private long score;

    public User() {
    }

    public User(String name, long games, long score) {
        this.name = name;
        this.games = games;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getGames() {
        return games;
    }

    public void setGames(long games) {
        this.games = games;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }
}
