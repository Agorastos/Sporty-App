package com.avagar.sporty.room.entity;

import androidx.room.Embedded;

import java.io.Serializable;

public class ClubSport implements Serializable {

    @Embedded()
    private ClubEntity club;

    @Embedded()
    private SportEntity sport;

    public ClubSport() {
    }

    public void setClub(ClubEntity club) {
        this.club = club;
    }

    public void setSport(SportEntity sport) {
        this.sport = sport;
    }

    public ClubEntity getClub() {
        return club;
    }

    public SportEntity getSport() {
        return sport;
    }

    @Override
    public String toString() {
        return "ClubSport{" +
                "club=" + club +
                ", sport=" + sport +
                '}';
    }
}
