package com.avagar.sporty.ui.sports;

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
import com.avagar.sporty.databinding.SportsFragmentBinding;
import com.avagar.sporty.room.entity.SportEntity;

import java.util.List;

public class SportsFragment extends Fragment implements SportsAdapter.SportsAdapterListener {
    private SportsFragmentBinding binding;
    private SportsViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        binding = DataBindingUtil.inflate(inflater, R.layout.sports_fragment, container, false);

        binding.sportsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.sportsRecycler.setHasFixedSize(true);
        binding.sportsRecycler.setAdapter(new SportsAdapter(this));

        binding.sportsFab.setOnClickListener(view -> Navigation.findNavController(binding.getRoot()).navigate(SportsFragmentDirections.actionSportsFragmentToAddSportFragment()));

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(SportsViewModel.class);

        List<SportEntity> data = viewModel.getSports();

        if (data.isEmpty()) {
            binding.sportsRecycler.setVisibility(View.GONE);
            binding.sportsMessage.setVisibility(View.VISIBLE);
        } else {
            binding.sportsRecycler.setVisibility(View.VISIBLE);
            binding.sportsMessage.setVisibility(View.GONE);

            ((SportsAdapter) binding.sportsRecycler.getAdapter()).setData(data);
        }

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.sporst_oderby_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.sportsOrderByName) {
            List<SportEntity> data = viewModel.getSportsOrdered();

            if (data.isEmpty()) {
                binding.sportsRecycler.setVisibility(View.GONE);
                binding.sportsMessage.setVisibility(View.VISIBLE);
            } else {
                binding.sportsRecycler.setVisibility(View.VISIBLE);
                binding.sportsMessage.setVisibility(View.GONE);

                ((SportsAdapter) binding.sportsRecycler.getAdapter()).setData(data);
            }

            return true;
        } else if(item.getItemId() == R.id.sportsDefaultOrder) {
            List<SportEntity> data = viewModel.getSports();

            if (data.isEmpty()) {
                binding.sportsRecycler.setVisibility(View.GONE);
                binding.sportsMessage.setVisibility(View.VISIBLE);
            } else {
                binding.sportsRecycler.setVisibility(View.VISIBLE);
                binding.sportsMessage.setVisibility(View.GONE);

                ((SportsAdapter) binding.sportsRecycler.getAdapter()).setData(data);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(SportEntity sportEntity) {
        Navigation.findNavController(binding.getRoot()).navigate(SportsFragmentDirections.actionSportsFragmentToEditSportFragment(sportEntity));
    }
}