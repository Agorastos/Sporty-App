package com.avagar.sporty.ui.athletes.add;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
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
import com.avagar.sporty.databinding.AddAthleteFragmentBinding;
import com.avagar.sporty.room.converters.DateConverters;
import com.avagar.sporty.room.entity.AthleteEntity;
import com.avagar.sporty.room.entity.SportEntity;
import com.avagar.sporty.ui.MainActivity;
import com.avagar.sporty.ui.athletes.AthletesViewModel;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.Date;
import java.util.List;

public class AddAthleteFragment extends Fragment {
    private AddAthleteFragmentBinding binding;
    private AthletesViewModel viewModel;
    MaterialDatePicker materialDatePicker;
    private final String DEFAULT_DATE_TEXT = "Select a date";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_athlete_fragment, container, false);

        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker().setTitleText("SELECT A DATE");
        materialDatePicker = materialDateBuilder.build();
        binding.athleteDate.setText(DEFAULT_DATE_TEXT);
        binding.athleteDateButton.setOnClickListener(v -> {
            binding.athleteDateButton.setEnabled(false);
            materialDatePicker.show(getParentFragmentManager(), "MATERIAL_DATE_PICKER");
        });

        materialDatePicker.addOnDismissListener(sel -> {
            binding.athleteDateButton.setEnabled(true);
        });


        materialDatePicker.addOnPositiveButtonClickListener(
                selection -> {
                    Long selectionDate = (Long) materialDatePicker.getSelection();

                    binding.athleteDate.setText(DateConverters.longToString(selectionDate));
                });

        binding.doneAthleteFab.setOnClickListener(view -> {
            String matchDate = (String) binding.athleteDate.getText();
            if (matchDate.equals(DEFAULT_DATE_TEXT) || binding.addAthleteFirstName.length() == 0 || binding.addAthleteLastName.length() == 0 || binding.addAthleteHomeGround.length() == 0 || binding.addAthleteCountry.length() == 0) {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                String firstName = binding.addAthleteFirstName.getText().toString().trim();
                String lastName = binding.addAthleteLastName.getText().toString().trim();
                String homeGround = binding.addAthleteHomeGround.getText().toString().trim();
                String country = binding.addAthleteCountry.getText().toString().trim();
                SportEntity item = (SportEntity) binding.athleteSpinner.getItems().get(binding.athleteSpinner.getSelectedIndex());

                AthleteEntity athleteEntity = new AthleteEntity(firstName, lastName, homeGround, country, item.getId(), new Date((Long) materialDatePicker.getSelection()));
                viewModel.insert(athleteEntity);
                sendPush("Created", "Athlete Created");
                Navigation.findNavController(binding.getRoot()).popBackStack();
            }
        });


        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        viewModel = new ViewModelProvider(this).get(AthletesViewModel.class);

        List<SportEntity> data = viewModel.getSports();

        if (data.isEmpty()) {

        } else {
            MaterialSpinner spinner = binding.athleteSpinner;
            spinner.setItems(data);
        }

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

    private void sendPush(String title, String description) {
        ((MainActivity)getActivity()).sendPush(title, description);
    }
}