package com.avagar.sporty.ui.sports;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avagar.sporty.databinding.SportItemBinding;
import com.avagar.sporty.room.entity.SportEntity;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class SportsAdapter extends RecyclerView.Adapter<SportsAdapter.SportsViewHolder> {

    private SportsAdapterListener listener;
    private Collection<SportEntity> sports;

    public SportsAdapter(SportsAdapterListener listener) {
        this.listener = listener;
        sports = new LinkedList<>();
    }

    public void setData(List<SportEntity> sports) {
        this.sports.clear();
        this.sports.addAll(sports);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SportsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SportItemBinding binding = SportItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new SportsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SportsViewHolder holder, int position) {
        holder.bind((SportEntity) ((LinkedList) sports).get(position));
    }

    @Override
    public int getItemCount() {
        return sports.size();
    }

    class SportsViewHolder extends RecyclerView.ViewHolder {
        private final SportItemBinding binding;

        SportsViewHolder(@NonNull SportItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(SportEntity sportEntity) {
            binding.getRoot().setOnClickListener(view -> listener.onClick(binding.getSport()));
            binding.setSport(sportEntity);
            binding.executePendingBindings();
        }
    }

    interface SportsAdapterListener {
        void onClick(SportEntity sportEntity);
    }
}
