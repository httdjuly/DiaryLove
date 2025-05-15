package com.example.diaryloveproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.diaryloveproject.R;
import com.example.diaryloveproject.model.Emoji;

import java.util.List;

public class EmojiNoteAdapter extends RecyclerView.Adapter<EmojiNoteAdapter.EmojiViewHolder> {

    private List<Emoji> emojiList;

    public EmojiNoteAdapter(List<Emoji> emojiList) {
        this.emojiList = emojiList;
    }

    @NonNull
    @Override
    public EmojiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_emoji, parent, false);
        return new EmojiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmojiViewHolder holder, int position) {
        Emoji emoji = emojiList.get(position);
        Glide.with(holder.itemView.getContext())
                .load(emoji.getImageUrl())
                .into(holder.imgEmoji);
    }

    @Override
    public int getItemCount() {
        return emojiList.size();
    }

    public static class EmojiViewHolder extends RecyclerView.ViewHolder {
        ImageView imgEmoji;

        public EmojiViewHolder(@NonNull View itemView) {
            super(itemView);
            imgEmoji = itemView.findViewById(R.id.ivEmoji);
        }
    }
}
