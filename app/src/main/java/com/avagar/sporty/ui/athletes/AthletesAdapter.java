package com.avagar.sporty.ui.athletes;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avagar.sporty.databinding.AthleteItemBinding;
import com.avagar.sporty.room.entity.AthleteEntity;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class AthletesAdapter extends RecyclerView.Adapter<AthletesAdapter.AthletesViewHolder> {

    private AthletesAdapterListener listener;
    private Collection<AthleteEntity> athletes;

    public AthletesAdapter(AthletesAdapterListener listener) {
        this.listener = listener;
        athletes = new LinkedList<>();
    }

    public void setData(List<AthleteEntity> athletes) {
        this.athletes.clear();
        this.athletes.addAll(athletes);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AthletesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AthleteItemBinding binding = AthleteItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new AthletesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AthletesViewHolder holder, int position) {
        holder.bind((AthleteEntity) ((LinkedList) athletes).get(position));
    }

    @Override
    public int getItemCount() {
        return athletes.size();
    }

    class AthletesViewHolder extends RecyclerView.ViewHolder {
        private final AthleteItemBinding binding;

        AthletesViewHolder(@NonNull AthleteItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(AthleteEntity athleteEntity) {
            binding.getRoot().setOnClickListener(view -> listener.onClick(binding.getAthlete()));
            binding.setAthlete(athleteEntity);
            binding.executePendingBindings();
        }
    }

    interface AthletesAdapterListener {
        void onClick(AthleteEntity athleteEntity);
    }
}
