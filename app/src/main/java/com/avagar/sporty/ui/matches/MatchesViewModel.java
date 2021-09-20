package com.avagar.sporty.ui.matches;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.avagar.sporty.firestore.FirestoreMatchRepository;
import com.avagar.sporty.firestore.model.Match;
import com.avagar.sporty.firestore.model.MatchRepository;
import com.avagar.sporty.room.DatabaseManager;
import com.avagar.sporty.room.dao.AthleteDao;
import com.avagar.sporty.room.dao.ClubDao;
import com.avagar.sporty.room.dao.SportDao;
import com.avagar.sporty.room.entity.AthleteEntity;
import com.avagar.sporty.room.entity.ClubEntity;
import com.avagar.sporty.room.entity.SportEntity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MatchesViewModel extends AndroidViewModel {

    private SportDao sportDao;
    private ClubDao clubDao;
    private AthleteDao athleteDao;
    private MatchRepository repository;
    public MatchesViewModel(@NonNull Application application) {
        super(application);

        DatabaseManager db = DatabaseManager.getDatabase(application);
        sportDao = db.getSportDao();
        clubDao = db.getClubDao();
        athleteDao = db.getAthleteDao();

        repository = new FirestoreMatchRepository(FirebaseFirestore.getInstance());

    }

    public void insert(Match match) {

    }

    public void update(Match match) {

    }

    public void delete(Match match) {

    }

    public LiveData<List<Match>> getAll() {
        return repository.getAll();
    }

    public LiveData<Match> getMatch(String id) {
        return null;
    }

    public List<SportEntity> getSports() {
        return sportDao.getSports();
    }

    public List<AthleteEntity> getAthletes() {
        return athleteDao.getAthletes();
    }

    public List<ClubEntity> getClubs() {
        return clubDao.getClubs();
    }


}
