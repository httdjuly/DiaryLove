package com.example.diaryloveproject;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diaryloveproject.adapter.DiaryAdapter;
import com.example.diaryloveproject.adapter.ImageAdapter;
import com.example.diaryloveproject.model.Diary;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DiaryViewHolder extends RecyclerView.ViewHolder {
    private TextView tvTitle, tvTime, tvContent;
    private RecyclerView rvEmojis, rvImages;
    private DiaryAdapter.OnDiaryClickListener listener;
    public DiaryViewHolder(@NonNull View itemView, DiaryAdapter.OnDiaryClickListener listener) {
        super(itemView);

        tvTitle = itemView.findViewById(R.id.tvTitle);
        tvTime = itemView.findViewById(R.id.tvTime);
        tvContent = itemView.findViewById(R.id.tvContext);
        rvEmojis = itemView.findViewById(R.id.rvEmojis);
        rvImages = itemView.findViewById(R.id.rvImages);
    }

    public void bind(Diary diary) {
        // Bind basic data
        tvTitle.setText(diary.getTitle());
        tvContent.setText(diary.getContent());
        tvTime.setText(formatDate(diary.getTimestamp()));

        // Handle click on entire item
        itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDiaryClick(diary);
            }
        });
    }

    private String formatDate(long timestamp) {
        return new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault())
                .format(new Date(timestamp));
    }
}
