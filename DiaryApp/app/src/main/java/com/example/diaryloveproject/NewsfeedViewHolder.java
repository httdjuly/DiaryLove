package com.example.diaryloveproject;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.diaryloveproject.adapter.DiaryAdapter;
import com.example.diaryloveproject.model.Diary;
public class NewsfeedViewHolder extends RecyclerView.ViewHolder {
    private ImageView ivAvatar;
    private TextView tvUsername;
    private DiaryViewHolder diaryViewHolder;

    public NewsfeedViewHolder(@NonNull View itemView, DiaryAdapter.OnDiaryClickListener listener) {
        super(itemView);

        ivAvatar = itemView.findViewById(R.id.ivAvatar);
        tvUsername = itemView.findViewById(R.id.tvUsername);

        // Tái sử dụng DiaryViewHolder
        View diaryView = ((ViewGroup) itemView).getChildAt(1); // Lấy view item_diary từ include
        diaryViewHolder = new DiaryViewHolder(diaryView, listener);
    }

    public void bind(Diary diary) {
        // Bind user info
        tvUsername.setText(diary.getAuthorName());

        // Load avatar (sử dụng Glide/Picasso)
        Glide.with(itemView.getContext())
                .load(diary.getAuthorAvatar())
                .circleCrop()
                .placeholder(R.drawable.bear)
                .into(ivAvatar);

        // Bind diary content
        diaryViewHolder.bind(diary);
    }
}