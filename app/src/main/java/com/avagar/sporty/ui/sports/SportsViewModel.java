package com.avagar.sporty.ui.sports;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.avagar.sporty.room.DatabaseManager;
import com.avagar.sporty.room.dao.SportDao;
import com.avagar.sporty.room.entity.SportEntity;

import java.util.List;

public class SportsViewModel extends AndroidViewModel {
    private SportDao sportDao;

    public SportsViewModel(@NonNull Application application) {
        super(application);

        DatabaseManager db = DatabaseManager.getDatabase(application);

        sportDao = db.getSportDao();
    }

    public void insert(SportEntity sportEntity) {
        sportDao.save(sportEntity);
    }

    public void update(SportEntity sportEntity) {
        sportDao.update(sportEntity);
    }

    public void delete(SportEntity sportEntity) {
        sportDao.delete(sportEntity);
    }

    public List<SportEntity> getSports() {
        return sportDao.getSports();
    }

    public List<SportEntity> getSportsOrdered() {
        return sportDao.getSportsOrdered();
    }
}
