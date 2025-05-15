package com.example.diaryloveproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diaryloveproject.EventViewHolder;
import com.example.diaryloveproject.R;
import com.example.diaryloveproject.model.Event;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventViewHolder> {

    private final List<Event> eventList;

    private final OnEventClickListener listener;

    public EventAdapter(List<Event> eventList, OnEventClickListener listener) {
        this.eventList = eventList;
        this.listener = listener;
    }
    public interface OnEventClickListener {
        void onEventClick(Event event, int position);
    }
    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.tvTitle.setText(event.getTitle());
        holder.itemView.setOnClickListener(v -> listener.onEventClick(event, position));
        holder.tvDays.setText(String.valueOf(event.getDay()));
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}