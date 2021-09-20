package com.avagar.sporty.firestore.model;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface MatchRepository {

    void save(Match match);

    void update(Match match);

    void delete(String id);

    LiveData<List<Match>> getAll();

    LiveData<Match> getById(String id);
}
