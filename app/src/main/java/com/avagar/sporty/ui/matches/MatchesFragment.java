package com.avagar.sporty.ui.matches;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.avagar.sporty.R;
import com.avagar.sporty.databinding.MatchesFragmentBinding;
import com.avagar.sporty.firestore.FirestoreMatchRepository;
import com.avagar.sporty.firestore.model.IndividualMatch;
import com.avagar.sporty.firestore.model.Match;
import com.avagar.sporty.firestore.model.MatchRepository;
import com.avagar.sporty.firestore.model.TeamMatch;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MatchesFragment extends Fragment implements MatchesAdapter.MatchesAdapterListener {

    private MatchesFragmentBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.matches_fragment, container, false);
//        binding.matchFab.setOnClickListener(view -> Navigation.findNavController(binding.getRoot()).navigate(MatchesFragmentDirections.actionMatchesFragmentToMapFragment("veroia")));

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(Match match) {

    }
}