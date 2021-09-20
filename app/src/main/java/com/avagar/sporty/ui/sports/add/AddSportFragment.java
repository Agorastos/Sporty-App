package com.avagar.sporty.ui.sports.add;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.avagar.sporty.R;
import com.avagar.sporty.databinding.AddSportFragmentBinding;
import com.avagar.sporty.room.entity.SportEntity;
import com.avagar.sporty.ui.MainActivity;
import com.avagar.sporty.ui.sports.SportsViewModel;

public class AddSportFragment extends Fragment {

    private AddSportFragmentBinding binding;
    private SportsViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_sport_fragment, container, false);

        binding.addproductFab.setOnClickListener(view -> {
            if (binding.addSportName.length() == 0 || binding.addSportKind.length() == 0 || binding.addSportGender.length() == 0) {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                SportEntity sportEntity = new SportEntity(binding.addSportName.getText().toString().trim(), binding.addSportKind.getText().toString().trim(), binding.addSportGender.getText().toString().trim());
                viewModel.insert(sportEntity);
                sendPush("Created", "Sport Created");
                Navigation.findNavController(binding.getRoot()).popBackStack();
            }

        });
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(SportsViewModel.class);
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