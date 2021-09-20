package com.avagar.sporty.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.avagar.sporty.room.entity.AthleteSport;
import com.avagar.sporty.room.entity.ClubSport;
import com.avagar.sporty.room.entity.SportEntity;

import java.util.List;

@Dao
public interface SportDao {
    @Insert
    void save(SportEntity sport);

    @Delete
    void delete(SportEntity sport);

    @Update
    void update(SportEntity sport);

    @Query("Select * from sport")
    List<SportEntity> getSports();

    @Query("Select * from sport where sport_id = :id")
    SportEntity getSportById(int id);

    @Query("select * from sport order by sport_name asc")
    List<SportEntity> getSportsOrdered();

    @Query("select * from sport join athlete on sport_id = athlete_sportId where sport_id = :id")
    List<AthleteSport> getSportAthleteById(int id);

    @Query("select * from sport join club on sport_id = club_sportId where sport_id = :id")
    List<ClubSport> getSportClubById(int id);


}
