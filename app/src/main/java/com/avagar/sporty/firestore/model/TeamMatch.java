package com.avagar.sporty.firestore.model;

import java.util.Date;

public class TeamMatch extends Match {
    private String score;
    private int team1;
    private int team2;

    public TeamMatch() {
    }

    public TeamMatch(String id, Date date, String city, String country, int sportId, String score, int team1, int team2) {
        super(id, date, city, country, sportId, "teams");
        this.score = score;
        this.team1 = team1;
        this.team2 = team2;
    }

    public String getScore() {
        return score;
    }

    public int getTeam1() {
        return team1;
    }

    public int getTeam2() {
        return team2;
    }

    public void setTeam1(int team1) {
        this.team1 = team1;
    }
}
