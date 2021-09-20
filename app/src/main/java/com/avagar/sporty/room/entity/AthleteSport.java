package com.avagar.sporty.room.entity;

import androidx.room.Embedded;

import java.io.Serializable;

public class AthleteSport implements Serializable {
    @Embedded()
    private AthleteEntity athlete;

    @Embedded()
    private SportEntity sport;

    public AthleteSport() {
    }

    public void setAthlete(AthleteEntity athlete) {
        this.athlete = athlete;
    }

    public void setSport(SportEntity sport) {
        this.sport = sport;
    }

    public AthleteEntity getAthlete() {
        return athlete;
    }

    public SportEntity getSport() {
        return sport;
    }

    @Override
    public String toString() {
        return "AthleteSport{" +
                "athlete=" + athlete +
                ", sport=" + sport +
                '}';
    }
}
