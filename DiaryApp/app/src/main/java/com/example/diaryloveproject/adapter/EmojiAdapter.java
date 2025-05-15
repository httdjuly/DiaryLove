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

public class EmojiAdapter extends RecyclerView.Adapter<EmojiAdapter.EmojiViewHolder> {

    public interface OnEmojiClickListener {
        void onEmojiClick(String emojiUrl);
    }

    private List<Emoji> emojis;
    private final OnEmojiClickListener listener;

    public EmojiAdapter(List<Emoji> emojis, OnEmojiClickListener listener) {
        this.emojis = emojis;
        this.listener = listener;
    }

    public void setEmojis(List<Emoji> emojis) {
        this.emojis = emojis;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EmojiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_emoji, parent, false);
        return new EmojiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmojiViewHolder holder, int position) {
        Emoji emoji = emojis.get(position);
        Glide.with(holder.itemView.getContext()).load(emoji.getImageUrl()).into(holder.imageView);
        holder.imageView.setOnClickListener(v -> listener.onEmojiClick(emoji.getImageUrl()));
    }

    @Override
    public int getItemCount() {
        return emojis != null ? emojis.size() : 0;
    }

    static class EmojiViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public EmojiViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivEmoji);
        }
    }
}