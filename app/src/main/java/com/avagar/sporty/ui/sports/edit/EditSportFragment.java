package com.avagar.sporty.ui.sports.edit;

import android.app.AlertDialog;
import android.content.Context;
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
import com.avagar.sporty.databinding.EditSportFragmentBinding;
import com.avagar.sporty.room.entity.SportEntity;
import com.avagar.sporty.ui.clubs.ClubsFragmentDirections;
import com.avagar.sporty.ui.sports.SportsViewModel;

public class EditSportFragment extends Fragment {
    private EditSportFragmentBinding binding;
    private SportsViewModel viewModel;
    private SportEntity sportEntity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        binding = DataBindingUtil.inflate(inflater, R.layout.edit_sport_fragment, container, false);
        sportEntity = EditSportFragmentArgs.fromBundle(getArguments()).getSport();
        binding.setSport(sportEntity);

        binding.editProductFab.setOnClickListener(view ->
                new AlertDialog.Builder(getActivity())
                        .setTitle("Edit")
                        .setMessage("Are you sure you want to edit this sport?")
                        .setPositiveButton("YES", (dialog, which) -> {
                            final String name = binding.editSportName.getText().toString().trim();
                            final String gender = binding.editSportGender.getText().toString().trim();
                            final String kind = binding.editSportKind.getText().toString().trim();

                            if (name.isEmpty() || gender.isEmpty() || kind.isEmpty()) {
                                Toast.makeText(getContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                            } else if (name.equals(sportEntity.getName()) && gender.equals(sportEntity.getGender()) && kind.equals(sportEntity.getKind())) {
                                Toast.makeText(getContext(), "No changes has been made.", Toast.LENGTH_SHORT).show();
                            } else {
                                binding.editSportKind.clearFocus();
                                binding.editSportGender.clearFocus();
                                binding.editSportName.clearFocus();

                                sportEntity.setName(name);
                                sportEntity.setGender(gender);
                                sportEntity.setKind(kind);

                                viewModel.update(sportEntity);
                                closeKeyboard();
                                Navigation.findNavController(binding.getRoot()).popBackStack();
                            }
                        })
                        .setNegativeButton("NO", null)
                        .show()
        );

        return binding.getRoot();
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(SportsViewModel.class);
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
                    .setMessage("Are you sure you want to delete this sport?")
                    .setPositiveButton("YES", (dialog, which) -> {
                        viewModel.delete(sportEntity);
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