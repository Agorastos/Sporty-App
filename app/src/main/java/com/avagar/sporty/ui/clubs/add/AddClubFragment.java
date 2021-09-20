package com.avagar.sporty.ui.clubs.add;

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
import com.avagar.sporty.databinding.AddClubFragmentBinding;
import com.avagar.sporty.room.converters.DateConverters;
import com.avagar.sporty.room.entity.ClubEntity;
import com.avagar.sporty.room.entity.SportEntity;
import com.avagar.sporty.ui.MainActivity;
import com.avagar.sporty.ui.clubs.ClubsViewModel;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.Date;
import java.util.List;


public class AddClubFragment extends Fragment {
    private AddClubFragmentBinding binding;
    private ClubsViewModel viewModel;
    MaterialDatePicker materialDatePicker;
    private final String DEFAULT_DATE_TEXT = "Select a date";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_club_fragment, container, false);
        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker().setTitleText("SELECT A DATE");
        materialDatePicker = materialDateBuilder.build();
        binding.clubFoundedDate.setText(DEFAULT_DATE_TEXT);
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
                });

        binding.doneClubFab.setOnClickListener(view -> {
            String foundedDate = (String) binding.clubFoundedDate.getText();
            if (foundedDate.equals(DEFAULT_DATE_TEXT) || binding.addClubName.length() == 0 || binding.addClubCountry.length() == 0 || binding.addClubHomeGround.length() == 0 || binding.addClubStadiumName.length() == 0) {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                String name = binding.addClubName.getText().toString().trim();
                String country = binding.addClubCountry.getText().toString().trim();
                String homeGround = binding.addClubHomeGround.getText().toString().trim();
                String stadiumName = binding.addClubStadiumName.getText().toString().trim();
                SportEntity item = (SportEntity) binding.clubSpinner.getItems().get(binding.clubSpinner.getSelectedIndex());

                ClubEntity clubEntity = new ClubEntity(name, stadiumName, homeGround, country, item.getId(), new Date((Long) materialDatePicker.getSelection()));
                viewModel.insert(clubEntity);
                sendPush("Created", "Club Created");
                Navigation.findNavController(binding.getRoot()).popBackStack();
            }
        });


        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        viewModel = new ViewModelProvider(this).get(ClubsViewModel.class);

        List<SportEntity> data = viewModel.getSports();

        if (data.isEmpty()) {

        } else {
            MaterialSpinner spinner = binding.clubSpinner;
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