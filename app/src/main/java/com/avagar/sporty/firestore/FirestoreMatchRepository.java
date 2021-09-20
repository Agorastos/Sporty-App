package com.avagar.sporty.firestore;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.avagar.sporty.firestore.model.IndividualMatch;
import com.avagar.sporty.firestore.model.Match;
import com.avagar.sporty.firestore.model.MatchRepository;
import com.avagar.sporty.firestore.model.TeamMatch;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.LinkedList;
import java.util.List;

public class FirestoreMatchRepository implements MatchRepository {

    private FirebaseFirestore db;
    private MutableLiveData<List<Match>> allMatches;
    private MutableLiveData<Match> match;

    public FirestoreMatchRepository(FirebaseFirestore db) {
        this.db = db;

    }

    @Override
    public void save(Match match) {
        db.collection("matches")
                .document(match.getId())
                .set(match);
    }

    @Override
    public void update(Match match) {
        db.collection("matches")
                .document(match.getId())
                .set(match);
    }

    @Override
    public void delete(String id) {
        db.collection("matches")
                .document(id)
                .delete();
    }

    @Override
    public LiveData<List<Match>> getAll() {
        if (allMatches == null) {
            allMatches = new MutableLiveData<>();
        }
        getAllMatches();
        return allMatches;
    }


    private void getAllMatches() {
        db.collection("matches")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        LinkedList<Match> list = new LinkedList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String type = document.getData().get("type").toString();
                            if (type.equals("individual")) {
                                list.push(document.toObject(IndividualMatch.class));
                            }

                            if (type.equals("teams")) {
                                list.push(document.toObject(TeamMatch.class));
                            }
                        }
                        allMatches.setValue(list);
                    } else {
                        //do something
                    }
                });
    }

    @Override
    public LiveData<Match> getById(String id) {
        if (match == null) {
            match = new MutableLiveData<>();
        }
        getMatchesById(id);
        return match;
    }

    private void getMatchesById(String id) {
        db.collection("matches")
                .document(id)
                .get()
                .addOnSuccessListener(document -> {
                    if (document.exists()) {
                        String type = document.get("type").toString();
                        if (type.equals("individual")) {
                            match.setValue(document.toObject(IndividualMatch.class));
                        }

                        if (type.equals("teams")) {
                            match.setValue(document.toObject(TeamMatch.class));
                        }
                    }
                });
    }
}
