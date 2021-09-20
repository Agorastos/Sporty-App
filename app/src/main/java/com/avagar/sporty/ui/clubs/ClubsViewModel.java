package com.avagar.sporty.ui.clubs;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.avagar.sporty.room.DatabaseManager;
import com.avagar.sporty.room.dao.ClubDao;
import com.avagar.sporty.room.dao.SportDao;
import com.avagar.sporty.room.entity.ClubEntity;
import com.avagar.sporty.room.entity.ClubSport;
import com.avagar.sporty.room.entity.SportEntity;

import java.util.List;

public class ClubsViewModel extends AndroidViewModel {
    private SportDao sportDao;
    private ClubDao clubDao;

    public ClubsViewModel(@NonNull Application application) {
        super(application);

        DatabaseManager db = DatabaseManager.getDatabase(application);

        sportDao = db.getSportDao();
        clubDao = db.getClubDao();
    }

    public void insert(ClubEntity entity) {
        clubDao.save(entity);
    }

    public List<ClubEntity> getClubs() {
        return clubDao.getClubs();
    }

    public List<ClubEntity> getClubOrdered() {
        return clubDao.getClubsOrdered();
    }

    public List<SportEntity> getSports() {
        return sportDao.getSports();
    }

    public ClubSport getClubSportById(int id) {
        return clubDao.getSportClubById(id);
    }

    public void delete(ClubEntity entity) {
        clubDao.delete(entity);
    }

    public void update(ClubEntity entity) {
        clubDao.update(entity);
    }
}
