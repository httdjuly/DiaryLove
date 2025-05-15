package com.example.diaryloveproject.model;

public class DiaryEntry {
    private String date; // ví dụ "2025-05-15"
    private int feelingEmojiResId;

    public DiaryEntry(String date, int feelingEmojiResId) {
        this.date = date;
        this.feelingEmojiResId = feelingEmojiResId;
    }

    public String getDate() {
        return date;
    }

    public int getFeelingEmojiResId() {
        return feelingEmojiResId;
    }
}
