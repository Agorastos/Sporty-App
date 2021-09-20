package com.avagar.sporty.ui.matches;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avagar.sporty.databinding.MatchesItemBinding;
import com.avagar.sporty.firestore.model.Match;
import com.avagar.sporty.room.entity.ClubEntity;

import java.util.Collection;
import java.util.LinkedList;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.MatchesViewHolder> {

    private MatchesAdapterListener listener;
    private Collection<Match> matches;

    public MatchesAdapter(MatchesAdapterListener listener) {
        this.listener = listener;
        matches = new LinkedList<>();
    }

    @NonNull
    @Override
    public MatchesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MatchesItemBinding binding = MatchesItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new MatchesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchesViewHolder holder, int position) {
        holder.bind((Match) ((LinkedList)matches).get(position));
    }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    class MatchesViewHolder extends RecyclerView.ViewHolder {
        private final MatchesItemBinding binding;

        MatchesViewHolder(@NonNull MatchesItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Match match) {
            binding.getRoot().setOnClickListener(view -> listener.onClick(binding.getMatch()));
            binding.setMatch(match);
            binding.executePendingBindings();
        }
    }

    interface MatchesAdapterListener {
        void onClick(Match match);
    }
}
