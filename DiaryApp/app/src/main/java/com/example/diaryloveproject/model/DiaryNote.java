package com.example.diaryloveproject.model;

import java.time.LocalDateTime;
import java.util.List;

public class DiaryNote {
    public DiaryNote(Long userId, String title, String content, List<String> emojiUrls) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.emojiUrls = emojiUrls;
    }

    private Long Id;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    private Long userId;
    private String title;
    private String content;
    private List<String> emojiUrls;
    private User user;

    // Getter/Setter
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    private String timestamp;

    public Long getId() {
        return Id;
    }

    public void setId(Long userId) {
        this.Id = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getEmojiUrls() {
        return emojiUrls;
    }

    public void setEmojiUrls(List<String> emojiUrls) {
        this.emojiUrls = emojiUrls;
    }

}

