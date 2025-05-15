package com.example.diaryloveproject.model;

public class User {
    private long id;
    private String username;
    private String email;

    public User(long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    // Getter và Setter
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
