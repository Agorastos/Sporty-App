package com.avagar.sporty.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "club", foreignKeys = {@ForeignKey(entity = SportEntity.class, parentColumns = "sport_id", childColumns = "club_sportId", onDelete = CASCADE, onUpdate = CASCADE)})
public class ClubEntity implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "club_id")
    private int id;

    @ColumnInfo(name = "club_name")
    private String name;

    @ColumnInfo(name = "club_stadiumName")
    private String stadiumName;

    @ColumnInfo(name = "club_homeGround")
    private String homeGround;

    @ColumnInfo(name = "club_country")
    private String country;

    @ColumnInfo(name = "club_sportId")
    private int sportId;

    @ColumnInfo(name = "club_founded")
    private Date founded;

    public ClubEntity() {

    }

    @Ignore
    public ClubEntity(String name, String stadiumName, String homeGround, String country, int sportId, Date founded) {
        this.name = name;
        this.stadiumName = stadiumName;
        this.homeGround = homeGround;
        this.country = country;
        this.sportId = sportId;
        this.founded = founded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStadiumName() {
        return stadiumName;
    }

    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
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

    public Date getFounded() {
        return founded;
    }

    public void setFounded(Date founded) {
        this.founded = founded;
    }

    @Ignore
    public void update(String name, String stadiumName, String homeGround, String country, int sportId, Date founded) {
        this.name = name;
        this.stadiumName = stadiumName;
        this.homeGround = homeGround;
        this.country = country;
        this.sportId = sportId;
        this.founded = founded;
    }

    @Override
    public String toString() {
        return "ClubEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", stadiumName='" + stadiumName + '\'' +
                ", homeGround='" + homeGround + '\'' +
                ", country='" + country + '\'' +
                ", sportId=" + sportId +
                ", founded=" + founded +
                '}';
    }
}
