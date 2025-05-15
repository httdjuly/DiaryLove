package com.example.diaryloveproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diaryloveproject.R;
import com.example.diaryloveproject.model.DiaryNote;
import com.example.diaryloveproject.model.Emoji;

import java.util.ArrayList;
import java.util.List;

public class DiaryNoteAdapter extends RecyclerView.Adapter<DiaryNoteAdapter.NoteViewHolder> {

    private List<DiaryNote> noteList;

    public DiaryNoteAdapter(List<DiaryNote> noteList) {
        this.noteList = noteList;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_diary, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        DiaryNote note = noteList.get(position);
        holder.tvTitle.setText(note.getTitle());
        holder.tvContext.setText(note.getContent());
        holder.tvTime.setText(note.getTimestamp().toString()); // time có thể là String đã định dạng sẵn

        // Adapter emoji
        List<String> emojiUrls = note.getEmojiUrls();  // List<String>
        List<Emoji> emojiList = new ArrayList<>();

        for (String url : emojiUrls) {
            Emoji emoji = new Emoji();
            emoji.setImageUrl(url);
            emojiList.add(emoji);
        }

        EmojiAdapter emojiAdapter = new EmojiAdapter(emojiList, emojiUrl -> {});
        holder.rvEmojis.setAdapter(emojiAdapter);
        holder.rvEmojis.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));

    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvTime, tvContext;
        RecyclerView rvEmojis;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvContext = itemView.findViewById(R.id.tvContext);
            rvEmojis = itemView.findViewById(R.id.rvEmojis);
        }
    }
}