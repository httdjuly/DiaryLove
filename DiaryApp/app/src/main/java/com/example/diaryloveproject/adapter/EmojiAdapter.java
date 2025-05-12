package com.example.diaryloveproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.diaryloveproject.R;

import java.util.List;

// EmojiAdapter.java
public class EmojiAdapter extends RecyclerView.Adapter<EmojiAdapter.EmojiViewHolder> {
    private final List<String> emojiImageUrls; // List đường dẫn ảnh emoji

    public EmojiAdapter(List<String> emojiImageUrls) {
        this.emojiImageUrls = emojiImageUrls;
    }

    @NonNull
    @Override
    public EmojiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_emoji, parent, false);
        return new EmojiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmojiViewHolder holder, int position) {
        String emojiUrl = emojiImageUrls.get(position);
        Glide.with(holder.itemView.getContext())
                .load(emojiUrl)
                .placeholder(R.drawable.ic_load)// icon tạm
                .error(R.drawable.ic_error) // icon lỗi
                .into(holder.ivEmoji);
    }

    @Override
    public int getItemCount() {
        return emojiImageUrls.size();
    }

    static class EmojiViewHolder extends RecyclerView.ViewHolder {
        ImageView ivEmoji;

        public EmojiViewHolder(@NonNull View itemView) {
            super(itemView);
            ivEmoji = itemView.findViewById(R.id.ivEmoji);
        }
    }
}
