package com.avagar.sporty.ui.athletes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.avagar.sporty.room.DatabaseManager;
import com.avagar.sporty.room.dao.AthleteDao;
import com.avagar.sporty.room.dao.SportDao;
import com.avagar.sporty.room.entity.AthleteEntity;
import com.avagar.sporty.room.entity.AthleteSport;
import com.avagar.sporty.room.entity.SportEntity;

import java.util.List;

public class AthletesViewModel extends AndroidViewModel {
    private AthleteDao athleteDao;
    private SportDao sportDao;

    public AthletesViewModel(@NonNull Application application) {
        super(application);

        DatabaseManager db = DatabaseManager.getDatabase(application);

        athleteDao = db.getAthleteDao();
        sportDao = db.getSportDao();
    }

    public void insert(AthleteEntity athleteEntity) {
        athleteDao.save(athleteEntity);
    }

    public List<AthleteEntity> getAthletes() {
        return athleteDao.getAthletes();
    }

    public List<AthleteEntity> getAthletesOrdered() {
        return athleteDao.getAthletesOrdered();
    }

    public List<SportEntity> getSports() {
        return sportDao.getSports();
    }

    public AthleteSport getAthleteSportById(int id) {
        return athleteDao.getSportAthleteById(id);
    }

    public void delete(AthleteEntity athleteEntity) {
        athleteDao.delete(athleteEntity);
    }

    public void update(AthleteEntity athleteEntity) {
        athleteDao.update(athleteEntity);
    }
}
