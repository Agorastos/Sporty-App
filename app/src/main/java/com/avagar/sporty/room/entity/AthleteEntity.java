package com.avagar.sporty.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "athlete", foreignKeys = {@ForeignKey(entity = SportEntity.class, parentColumns = "sport_id", childColumns = "athlete_sportId", onDelete = CASCADE, onUpdate = CASCADE)})
public class AthleteEntity implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "athlete_id")
    private int id;

    @ColumnInfo(name = "athlete_firstName")
    private String firstName;

    @ColumnInfo(name = "athlete_lastName")
    private String lastName;

    @ColumnInfo(name = "athlete_homeGround")
    private String homeGround;

    @ColumnInfo(name = "athlete_country")
    private String country;

    @ColumnInfo(name = "athlete_sportId")
    private int sportId;

    @ColumnInfo(name = "athlete_date")
    private Date dateOfBirth;

    public AthleteEntity() {

    }

    @Ignore
    public AthleteEntity(String firstName, String lastName, String homeGround, String country, int sportId, Date dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.homeGround = homeGround;
        this.country = country;
        this.sportId = sportId;
        this.dateOfBirth = dateOfBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getHomeGround() {
        return homeGround;
    }

    public void setHomeGround(String homeGround) {
        this.homeGround = homeGround;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getSportId() {
        return sportId;
    }

    public void setSportId(int sportId) {
        this.sportId = sportId;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "AthleteEntity{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", homeGround='" + homeGround + '\'' +
                ", country='" + country + '\'' +
                ", sportId=" + sportId +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}
