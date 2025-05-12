package com.example.diaryloveproject.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Diary {
    private String id;
    private String title;
    private String content;
    private List<String> imageUrls;
    private long timestamp;
    private List<String> emojis;

    // Constructor cơ bản
    public Diary(String id, String title, String content, long timestamp) {
        this(id, title, content, new ArrayList<>(), timestamp, new ArrayList<>());
    }

    // Constructor đầy đủ
    public Diary(String id, String title, String content, List<String> imageUrls,
                 long timestamp, List<String> emojis) {
        this.id = Objects.requireNonNull(id, "ID cannot be null");
        this.title = Objects.requireNonNull(title, "Title cannot be null");
        this.content = Objects.requireNonNull(content, "Content cannot be null");
        this.imageUrls = imageUrls != null ? new ArrayList<>(imageUrls) : new ArrayList<>();
        this.timestamp = timestamp;
        this.emojis = emojis != null ? new ArrayList<>(emojis) : new ArrayList<>();
    }

    // Getter methods
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public long getTimestamp() { return timestamp; }
    public List<String> getEmojis() { return new ArrayList<>(emojis); }
    public List<String> getImageUrls() { return new ArrayList<>(imageUrls); }

    // Setter methods
    public void setId(String id) { this.id = Objects.requireNonNull(id); }
    public void setTitle(String title) { this.title = Objects.requireNonNull(title); }
    public void setContent(String content) { this.content = Objects.requireNonNull(content); }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    // Quản lý emojis
    public void addEmoji(String emoji) {
        if (emoji != null && !emoji.trim().isEmpty()) {
            this.emojis.add(emoji.trim());
        }
    }

    public boolean removeEmoji(String emoji) {
        return this.emojis.remove(emoji);
    }

    public void clearEmojis() {
        this.emojis.clear();
    }

    public void setEmojis(List<String> emojis) {
        this.emojis = emojis != null ? new ArrayList<>(emojis) : new ArrayList<>();
    }

    // Quản lý image URLs
    public void addImageUrl(String imageUrl) {
        if (imageUrl != null && !imageUrl.trim().isEmpty()) {
            this.imageUrls.add(imageUrl.trim());
        }
    }

    public boolean removeImageUrl(String imageUrl) {
        return this.imageUrls.remove(imageUrl);
    }

    public void clearImageUrls() {
        this.imageUrls.clear();
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls != null ? new ArrayList<>(imageUrls) : new ArrayList<>();
    }

    // Phương thức tiện ích
    public boolean hasImages() {
        return !imageUrls.isEmpty();
    }

    public boolean hasEmojis() {
        return !emojis.isEmpty();
    }
}