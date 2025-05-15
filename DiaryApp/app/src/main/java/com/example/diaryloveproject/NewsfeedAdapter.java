package com.example.diaryloveproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diaryloveproject.adapter.DiaryAdapter;
import com.example.diaryloveproject.model.Diary;

import java.util.List;
public class NewsfeedAdapter extends RecyclerView.Adapter<NewsfeedViewHolder> {
    private List<Diary> diaryList;
    private DiaryAdapter.OnDiaryClickListener listener;

    public NewsfeedAdapter(List<Diary> diaryList, DiaryAdapter.OnDiaryClickListener listener) {
        this.diaryList = diaryList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NewsfeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_newsfeed, parent, false);
        return new NewsfeedViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsfeedViewHolder holder, int position) {
        Diary diary = diaryList.get(position);
        holder.bind(diary);
    }

    @Override
    public int getItemCount() {
        return diaryList.size();
    }
}