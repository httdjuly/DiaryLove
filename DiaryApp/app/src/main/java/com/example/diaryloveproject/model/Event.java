package com.example.diaryloveproject.model;

public class Event {
    String title;
    int day;
    String repeatType;  // "none", "monthly", "yearly"



    public Event(String title, int day, String repeatType) {
        this.title = title;
        this.day = day;
        this.repeatType = repeatType;

    }
    public void setRepeatType(String repeatType) {
        this.repeatType = repeatType;
    }
    public String getRepeatType() {
        return repeatType;
    }
    public String getTitle() {
        return title;
    }



    public int getDay() {
        return day;
    }



    public void setTitle(String title) {
        this.title = title;
    }



    public void setDay(int day) {
        this.day = day;
    }


}