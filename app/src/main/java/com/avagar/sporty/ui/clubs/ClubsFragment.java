package com.avagar.sporty.ui.clubs;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.avagar.sporty.R;
import com.avagar.sporty.databinding.ClubsFragmentBinding;
import com.avagar.sporty.room.entity.ClubEntity;

import java.util.List;

public class ClubsFragment extends Fragment implements ClubsAdapter.ClubsAdapterListener {
    private ClubsFragmentBinding binding;
    private ClubsViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        binding = DataBindingUtil.inflate(inflater, R.layout.clubs_fragment, container, false);

        binding.clubsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.clubsRecycler.setHasFixedSize(true);
        binding.clubsRecycler.setAdapter(new ClubsAdapter(this));

        binding.clubsFab.setOnClickListener(view -> {
            if (viewModel.getSports().size() > 0) {
                Navigation.findNavController(binding.getRoot()).navigate(ClubsFragmentDirections.actionClubsFragmentToAddClubFragment());
            } else {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Sports not found")
                        .setMessage("You need to add sports in order to add athletes")
                        .setPositiveButton("Add sport", (dialog, which) -> {
                            Navigation.findNavController(binding.getRoot()).navigate(R.id.addSportFragment);
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ClubsViewModel.class);

        List<ClubEntity> data = viewModel.getClubs();

        if (data.isEmpty()) {
            binding.clubsRecycler.setVisibility(View.GONE);
            binding.clubsMessage.setVisibility(View.VISIBLE);
        } else {
            binding.clubsRecycler.setVisibility(View.VISIBLE);
            binding.clubsMessage.setVisibility(View.GONE);

            ((ClubsAdapter) binding.clubsRecycler.getAdapter()).setData(data);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.clubs_oderby_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.clubsOrderByName) {
            List<ClubEntity> data = viewModel.getClubOrdered();

            if (data.isEmpty()) {
                binding.clubsRecycler.setVisibility(View.GONE);
                binding.clubsMessage.setVisibility(View.VISIBLE);
            } else {
                binding.clubsRecycler.setVisibility(View.VISIBLE);
                binding.clubsMessage.setVisibility(View.GONE);

                ((ClubsAdapter) binding.clubsRecycler.getAdapter()).setData(data);
            }

            return true;
        } else if(item.getItemId() == R.id.clubsDefaultOrder) {
            List<ClubEntity> data = viewModel.getClubs();

            if (data.isEmpty()) {
                binding.clubsRecycler.setVisibility(View.GONE);
                binding.clubsMessage.setVisibility(View.VISIBLE);
            } else {
                binding.clubsRecycler.setVisibility(View.VISIBLE);
                binding.clubsMessage.setVisibility(View.GONE);

                ((ClubsAdapter) binding.clubsRecycler.getAdapter()).setData(data);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(ClubEntity clubEntity) {
        Navigation.findNavController(binding.getRoot()).navigate(ClubsFragmentDirections.actionClubsFragmentToEditClubFragment(clubEntity.getId()+""));
    }
}