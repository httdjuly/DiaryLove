package com.example.diaryloveproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diaryloveproject.DiaryViewHolder;
import com.example.diaryloveproject.R;
import com.example.diaryloveproject.model.Diary;

import java.util.List;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryViewHolder> {

    private List<Diary> diaryList;
    private OnDiaryClickListener listener;

    public interface OnDiaryClickListener {
        void onDiaryClick(Diary diary);
        void onImageClick(Diary diary, int position);
    }

    public DiaryAdapter(List<Diary> diaryList, OnDiaryClickListener listener) {
        this.diaryList = diaryList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_diary, parent, false);
        return new DiaryViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryViewHolder holder, int position) {
        Diary diary = diaryList.get(position);
        holder.bind(diary);
    }

    @Override
    public int getItemCount() {
        return diaryList.size();
    }

    public void updateData(List<Diary> newDiaryList) {
        diaryList = newDiaryList;
        notifyDataSetChanged();
    }
}
