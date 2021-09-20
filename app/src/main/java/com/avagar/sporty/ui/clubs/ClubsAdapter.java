package com.avagar.sporty.ui.clubs;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avagar.sporty.databinding.ClubItemBinding;
import com.avagar.sporty.room.entity.ClubEntity;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ClubsAdapter extends RecyclerView.Adapter<ClubsAdapter.ClubsViewHolder> {

    private ClubsAdapterListener listener;
    private Collection<ClubEntity> clubs;

    public ClubsAdapter(ClubsAdapterListener listener) {
        this.listener = listener;
        clubs = new LinkedList<>();
    }

    public void setData(List<ClubEntity> clubs) {
        this.clubs.clear();
        this.clubs.addAll(clubs);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ClubsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ClubItemBinding binding = ClubItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ClubsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ClubsViewHolder holder, int position) {
        holder.bind((ClubEntity) ((LinkedList) clubs).get(position));
    }

    @Override
    public int getItemCount() {
        return clubs.size();
    }

    class ClubsViewHolder extends RecyclerView.ViewHolder {
        private final ClubItemBinding binding;

        ClubsViewHolder(@NonNull ClubItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(ClubEntity clubEntity) {
            binding.getRoot().setOnClickListener(view -> listener.onClick(binding.getClub()));
            binding.setClub(clubEntity);
            binding.executePendingBindings();
        }
    }

    interface ClubsAdapterListener {
        void onClick(ClubEntity clubEntity);
    }
}
