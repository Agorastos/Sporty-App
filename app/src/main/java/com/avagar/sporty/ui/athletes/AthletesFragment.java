package com.avagar.sporty.ui.athletes;

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
import com.avagar.sporty.databinding.AthletesFragmentBinding;
import com.avagar.sporty.room.entity.AthleteEntity;

import java.util.List;

public class AthletesFragment extends Fragment implements AthletesAdapter.AthletesAdapterListener {
    private AthletesFragmentBinding binding;
    private AthletesViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        binding = DataBindingUtil.inflate(inflater, R.layout.athletes_fragment, container, false);

        binding.athletesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.athletesRecycler.setHasFixedSize(true);
        binding.athletesRecycler.setAdapter(new AthletesAdapter(this));

        binding.athletesFab.setOnClickListener(view -> {
            if (viewModel.getSports().size() > 0) {
                Navigation.findNavController(binding.getRoot()).navigate(AthletesFragmentDirections.actionAthletesFragmentToAddAthleteFragment());
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

        viewModel = new ViewModelProvider(this).get(AthletesViewModel.class);

        List<AthleteEntity> data = viewModel.getAthletes();

        if (data.isEmpty()) {
            binding.athletesRecycler.setVisibility(View.GONE);
            binding.athletesMessage.setVisibility(View.VISIBLE);
        } else {
            binding.athletesRecycler.setVisibility(View.VISIBLE);
            binding.athletesMessage.setVisibility(View.GONE);

            ((AthletesAdapter) binding.athletesRecycler.getAdapter()).setData(data);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.athletes_oderby_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.athletesOrderByFirstName) {
            List<AthleteEntity> data = viewModel.getAthletesOrdered();

            if (data.isEmpty()) {
                binding.athletesRecycler.setVisibility(View.GONE);
                binding.athletesMessage.setVisibility(View.VISIBLE);
            } else {
                binding.athletesRecycler.setVisibility(View.VISIBLE);
                binding.athletesMessage.setVisibility(View.GONE);

                ((AthletesAdapter) binding.athletesRecycler.getAdapter()).setData(data);
            }

            return true;
        } else if(item.getItemId() == R.id.athletesDefaultOrder) {
            List<AthleteEntity> data = viewModel.getAthletes();

            if (data.isEmpty()) {
                binding.athletesRecycler.setVisibility(View.GONE);
                binding.athletesMessage.setVisibility(View.VISIBLE);
            } else {
                binding.athletesRecycler.setVisibility(View.VISIBLE);
                binding.athletesMessage.setVisibility(View.GONE);

                ((AthletesAdapter) binding.athletesRecycler.getAdapter()).setData(data);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(AthleteEntity athleteEntity) {
        Navigation.findNavController(binding.getRoot()).navigate(AthletesFragmentDirections.actionAthletesFragmentToEditAthleteFragment(athleteEntity.getId()+""));
    }
}