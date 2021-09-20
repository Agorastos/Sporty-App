package com.avagar.sporty.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.avagar.sporty.room.entity.AthleteEntity;
import com.avagar.sporty.room.entity.AthleteSport;
import com.avagar.sporty.room.entity.ClubEntity;

import java.util.List;

@Dao
public interface AthleteDao {
    @Insert
    void save(AthleteEntity athlete);

    @Delete
    void delete(AthleteEntity athlete);

    @Update
    void update(AthleteEntity athlete);

    @Query("select * from athlete")
    List<AthleteEntity> getAthletes();

    @Query("select * from athlete where athlete_id = :id")
    AthleteEntity getAthleteById(int id);

    @Query("select * from athlete order by athlete_firstName asc")
    List<AthleteEntity> getAthletesOrdered();

    @Query("select * from athlete where athlete_id in (:athletes)")
    List<AthleteEntity> getAthletes(List<Integer> athletes);

    @Query("select * from sport join athlete on athlete_sportId = sport_id where athlete_id = :id")
    AthleteSport getSportAthleteById(int id);
}
