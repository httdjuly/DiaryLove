package com.example.diaryloveproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diaryloveproject.R;

import java.util.List;

public class EmojiAdapter extends RecyclerView.Adapter<EmojiAdapter.EmojiViewHolder> {
    private final List<Integer> emojiResIds;

    public EmojiAdapter(List<Integer> emojiResIds) {
        this.emojiResIds = emojiResIds;
    }

    @NonNull
    @Override
    public EmojiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_emoji, parent, false);
        return new EmojiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmojiViewHolder holder, int position) {
        int resId = emojiResIds.get(position);
        holder.ivEmoji.setImageResource(resId); // dùng resource trực tiếp
    }

    @Override
    public int getItemCount() {
        return emojiResIds.size();
    }

    static class EmojiViewHolder extends RecyclerView.ViewHolder {
        ImageView ivEmoji;

        public EmojiViewHolder(@NonNull View itemView) {
            super(itemView);
            ivEmoji = itemView.findViewById(R.id.ivEmoji);
        }
    }
}


