package com.avagar.sporty.firestore.model;

import java.util.Date;
import java.util.Map;

public class IndividualMatch extends Match {
    private Map<String, String> scoreAthletes;

    public IndividualMatch() {
    }

    public IndividualMatch(String id, Date date, String city, String country, int sportId, Map<String, String> scoreAthletes) {
        super(id, date, city, country, sportId, "individual");
        this.scoreAthletes = scoreAthletes;
    }

    public Map<String, String> getScoreAthletes() {
        return scoreAthletes;
    }
}
