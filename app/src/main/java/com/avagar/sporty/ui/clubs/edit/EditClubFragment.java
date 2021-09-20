package com.avagar.sporty.ui.clubs.edit;

import android.app.AlertDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.avagar.sporty.R;
import com.avagar.sporty.databinding.EditClubFragmentBinding;
import com.avagar.sporty.room.converters.DateConverters;
import com.avagar.sporty.room.entity.ClubEntity;
import com.avagar.sporty.room.entity.ClubSport;
import com.avagar.sporty.room.entity.SportEntity;
import com.avagar.sporty.ui.clubs.ClubsViewModel;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class EditClubFragment extends Fragment {
    private EditClubFragmentBinding binding;
    private ClubsViewModel viewModel;
    private ClubEntity entity;
    private MaterialDatePicker materialDatePicker;
    private boolean dateSelected;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        binding = DataBindingUtil.inflate(inflater, R.layout.edit_club_fragment, container, false);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ClubsViewModel.class);

        this.dateSelected = false;
        int clubId = Integer.parseInt(EditClubFragmentArgs.fromBundle(getArguments()).getClubId());
        ClubSport clubSport = viewModel.getClubSportById(clubId);
        entity = clubSport.getClub();

        if (entity != null) {
            binding.setClub(entity);
            List<SportEntity> sports = viewModel.getSports();

            if (sports.isEmpty()) {

            } else {
                MaterialSpinner spinner = binding.clubSpinner;
                spinner.setItems(sports);
                SportEntity selectedSport = (SportEntity) spinner.getItems().stream().filter(entity -> ((SportEntity) entity).getId() == clubSport.getSport().getId()).findAny().orElse(null);
                spinner.setSelectedIndex(spinner.getItems().indexOf(selectedSport));
            }

            binding.clubFoundedDate.setText(entity.getFounded().toString());

            MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("SELECT A DATE")
                    .setSelection(entity.getFounded().getTime());
            materialDatePicker = materialDateBuilder.build();

            binding.clubDateButton.setOnClickListener(v -> {
                binding.clubDateButton.setEnabled(false);
                materialDatePicker.show(getParentFragmentManager(), "MATERIAL_DATE_PICKER");
            });

            materialDatePicker.addOnDismissListener(sel -> {
                binding.clubDateButton.setEnabled(true);
            });


            materialDatePicker.addOnPositiveButtonClickListener(
                    selection -> {
                        Long selectionDate = (Long) materialDatePicker.getSelection();

                        binding.clubFoundedDate.setText(DateConverters.longToString(selectionDate));

                        if (selectionDate != entity.getFounded().getTime()) {
                            this.dateSelected = true;
                        }
                    });

            binding.doneClubFab.setOnClickListener(view ->
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Edit")
                            .setMessage("Are you sure you want to edit this club?")
                            .setPositiveButton("YES", (dialog, which) -> {
                                final String name = binding.addClubName.getText().toString().trim();
                                final String country = binding.addClubCountry.getText().toString().trim();
                                final String homeGround = binding.addClubHomeGround.getText().toString().trim();
                                final String stadiumName = binding.addClubStadiumName.getText().toString().trim();
                                final int itemId = ((SportEntity) binding.clubSpinner.getItems().get(binding.clubSpinner.getSelectedIndex())).getId();

                                if (name.isEmpty() || stadiumName.isEmpty() || homeGround.isEmpty() || country.isEmpty()) {
                                    Toast.makeText(getContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                                } else if (name.equals(entity.getName()) && stadiumName.equals(entity.getStadiumName()) && homeGround.equals(entity.getHomeGround()) && country.equals(entity.getCountry())
                                        && itemId == entity.getSportId() && !dateSelected) {

                                    Toast.makeText(getContext(), "No changes has been made.", Toast.LENGTH_SHORT).show();
                                } else {
                                    binding.addClubName.clearFocus();
                                    binding.addClubCountry.clearFocus();
                                    binding.addClubHomeGround.clearFocus();
                                    binding.addClubStadiumName.clearFocus();
                                    binding.clubSpinner.clearFocus();

                                    entity.update(name, stadiumName, homeGround, country, itemId, dateSelected ? new Date((Long) materialDatePicker.getSelection()) : entity.getFounded());

                                    viewModel.update(entity);
                                    closeKeyboard();
                                    Navigation.findNavController(binding.getRoot()).popBackStack();
                                }
                            })
                            .setNegativeButton("NO", null)
                            .show()
            );

            binding.openOnMaps.setOnClickListener(view -> {
                Geocoder geocoder = new Geocoder(getContext());
                List<Address> list = new ArrayList<>();
                try {
                    list = geocoder.getFromLocationName(entity.getHomeGround(), 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (list.size() > 0) {
                    Navigation.findNavController(binding.getRoot()).navigate(EditClubFragmentDirections.actionEditClubFragmentToMapFragment(entity.getHomeGround()));
                } else {
                    Toast.makeText(getContext(), "Invalid Home Ground.", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.edit_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_delete) {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Delete")
                    .setMessage("Are you sure you want to delete this club?")
                    .setPositiveButton("YES", (dialog, which) -> {
                        viewModel.delete(entity);
                        Navigation.findNavController(binding.getRoot()).popBackStack();
                    })
                    .setNegativeButton("NO", null)
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop() {
        super.onStop();
        closeKeyboard();
    }

    private void closeKeyboard() {
        if (getActivity() != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

            if (imm != null) {
                imm.hideSoftInputFromWindow(binding.getRoot().getWindowToken(), 0);
            }
        }
    }

}