package com.avagar.sporty.ui.athletes.edit;

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
import com.avagar.sporty.databinding.EditAthleteFragmentBinding;
import com.avagar.sporty.room.converters.DateConverters;
import com.avagar.sporty.room.entity.AthleteEntity;
import com.avagar.sporty.room.entity.AthleteSport;
import com.avagar.sporty.room.entity.SportEntity;
import com.avagar.sporty.ui.athletes.AthletesViewModel;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EditAthleteFragment extends Fragment {
    private EditAthleteFragmentBinding binding;
    private AthletesViewModel viewModel;
    private AthleteEntity entity;
    MaterialDatePicker materialDatePicker;
    private boolean dateSelected;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        binding = DataBindingUtil.inflate(inflater, R.layout.edit_athlete_fragment, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AthletesViewModel.class);

        this.dateSelected = false;
        int athleteId = Integer.parseInt(EditAthleteFragmentArgs.fromBundle(getArguments()).getAthleteId());
        AthleteSport athleteSport = viewModel.getAthleteSportById(athleteId);
        entity = athleteSport.getAthlete();

        if (entity != null) {
            binding.setAthlete(athleteSport.getAthlete());
            List<SportEntity> sports = viewModel.getSports();

            if (sports.isEmpty()) {

            } else {
                MaterialSpinner spinner = binding.athleteSpinner;
                spinner.setItems(sports);
                SportEntity se = (SportEntity) spinner.getItems().stream().filter(entity -> ((SportEntity) entity).getId() == athleteSport.getSport().getId()).findAny().orElse(null);
                spinner.setSelectedIndex(spinner.getItems().indexOf(se));
            }

            binding.athleteDate.setText(entity.getDateOfBirth().toString());

            MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("SELECT A DATE")
                    .setSelection(entity.getDateOfBirth().getTime());
            materialDatePicker = materialDateBuilder.build();

            binding.athleteDateButton.setOnClickListener(v -> {
                binding.athleteDateButton.setEnabled(false);
                materialDatePicker.show(getParentFragmentManager(), "MATERIAL_DATE_PICKER");
            });

            materialDatePicker.addOnDismissListener(sel -> {
                binding.athleteDateButton.setEnabled(true);
            });

            materialDatePicker.addOnPositiveButtonClickListener(selection -> {
                Long selectionDate = (Long) materialDatePicker.getSelection();

                binding.athleteDate.setText(DateConverters.longToString(selectionDate));

                if (selectionDate != entity.getDateOfBirth().getTime()) {
                    this.dateSelected = true;
                }
            });

            binding.doneAthleteFab.setOnClickListener(v ->
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Edit")
                            .setMessage("Are you sure you want to edit this athlete?")
                            .setPositiveButton("YES", (dialog, which) -> {
                                final String firstName = binding.editAthleteFirstName.getText().toString().trim();
                                final String lastName = binding.editAthleteLastName.getText().toString().trim();
                                final String homeGround = binding.editAthleteHomeGround.getText().toString().trim();
                                final String country = binding.editAthleteCountry.getText().toString().trim();
                                final SportEntity item = (SportEntity) binding.athleteSpinner.getItems().get(binding.athleteSpinner.getSelectedIndex());
                                if (firstName.isEmpty() || lastName.isEmpty() || homeGround.isEmpty() || country.isEmpty()) {
                                    Toast.makeText(getContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                                } else if (firstName.equals(entity.getFirstName()) && lastName.equals(entity.getLastName()) && homeGround.equals(entity.getHomeGround()) && country.equals(entity.getCountry())
                                        && item.getId() == entity.getSportId() && !dateSelected) {
                                    Toast.makeText(getContext(), "No changes has been made.", Toast.LENGTH_SHORT).show();
                                } else {
                                    binding.editAthleteFirstName.clearFocus();
                                    binding.editAthleteLastName.clearFocus();
                                    binding.editAthleteHomeGround.clearFocus();
                                    binding.editAthleteCountry.clearFocus();
                                    entity.setFirstName(firstName);
                                    entity.setLastName(lastName);
                                    entity.setCountry(country);
                                    entity.setHomeGround(homeGround);

                                    if (dateSelected) {
                                        entity.setDateOfBirth(new Date((Long) materialDatePicker.getSelection()));
                                    }
                                    entity.setSportId(item.getId());
                                    viewModel.update(entity);
                                    closeKeyboard();
                                    Navigation.findNavController(binding.getRoot()).popBackStack();
                                }
                            })
                            .setNegativeButton("NO", null)
                            .show()
            );

            binding.openOnMaps.setOnClickListener(v -> {
                Geocoder geocoder = new Geocoder(getContext());
                List<Address> list = new ArrayList<>();
                try {
                    list = geocoder.getFromLocationName(entity.getHomeGround(), 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (list.size() > 0) {
                    Navigation.findNavController(binding.getRoot()).navigate(EditAthleteFragmentDirections.actionEditAthleteFragmentToMapFragment(entity.getHomeGround()));
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
                    .setMessage("Are you sure you want to delete this athlete?")
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