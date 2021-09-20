package com.avagar.sporty.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.avagar.sporty.room.converters.DateConverters;
import com.avagar.sporty.room.dao.AthleteDao;
import com.avagar.sporty.room.dao.ClubDao;
import com.avagar.sporty.room.dao.SportDao;
import com.avagar.sporty.room.entity.AthleteEntity;
import com.avagar.sporty.room.entity.ClubEntity;
import com.avagar.sporty.room.entity.SportEntity;

@Database(entities = {AthleteEntity.class, ClubEntity.class, SportEntity.class}, version = 2, exportSchema = false)
@TypeConverters({DateConverters.class})
public abstract class DatabaseManager extends RoomDatabase {

    public abstract AthleteDao getAthleteDao();

    public abstract ClubDao getClubDao();

    public abstract SportDao getSportDao();

    private static volatile DatabaseManager instance;

    public static DatabaseManager getDatabase(final Context context) {
        if (instance == null) {
            synchronized (DatabaseManager.class) {
                if (instance == null) {
                    instance = Room
                            .databaseBuilder(context.getApplicationContext(), DatabaseManager.class, "sporty_database")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }
}
