package com.avagar.sporty.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.avagar.sporty.room.entity.ClubEntity;
import com.avagar.sporty.room.entity.ClubSport;

import java.util.List;

@Dao
public interface ClubDao {
    @Insert
    void save(ClubEntity club);

    @Delete
    void delete(ClubEntity club);

    @Update
    void update(ClubEntity club);

    @Query("select * from club")
    List<ClubEntity> getClubs();

    @Query("select * from club where club_id = :id")
    ClubEntity getClubById(int id);

    @Query("select * from club order by club_name asc")
    List<ClubEntity> getClubsOrdered();

    @Query("select * from club where club_id in (:clubs)")
    List<ClubEntity> getClubs(List<Integer> clubs);

    @Query("select * from sport join club on club_sportId = sport_id where club_id = :id")
    ClubSport getSportClubById(int id);

}
