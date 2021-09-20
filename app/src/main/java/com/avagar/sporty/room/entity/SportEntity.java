package com.avagar.sporty.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "sport")
public class SportEntity implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "sport_id")
    private int id;

    @ColumnInfo(name = "sport_name")
    private String name;

    @ColumnInfo(name = "kind")
    private String kind;

    @ColumnInfo(name = "gender")
    private String gender;

    public SportEntity() {

    }

    @Ignore
    public SportEntity(String name, String kind, String gender) {
        this.name = name;
        this.kind = kind;
        this.gender = gender;
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

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    @Override
    public String toString() {
        return name;
    }

}
