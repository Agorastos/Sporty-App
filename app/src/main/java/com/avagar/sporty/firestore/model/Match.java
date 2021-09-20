package com.avagar.sporty.firestore.model;

import java.io.Serializable;
import java.util.Date;

public class Match implements Serializable {
    private String id;
    private Date date;
    private String city;
    private String country;
    private int sportId;
    private String type;

    public Match() {
    }

    public Match(String id, Date date, String city, String country, int sportId, String type) {
        this.id = id;
        this.date = date;
        this.city = city;
        this.country = country;
        this.sportId = sportId;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public int getSportId() {
        return sportId;
    }

    public String getType() {
        return type;
    }
}
