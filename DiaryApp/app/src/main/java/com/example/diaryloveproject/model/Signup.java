package com.example.diaryloveproject.model;

public class Signup {
    private String username;
    private String email;
    private int id;


    public Signup(int id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    // Getters/setters nếu cần
}
