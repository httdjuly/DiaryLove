package com.example.diaryloveproject.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diaryloveproject.R;
import com.example.diaryloveproject.adapter.EventAdapter;
import com.example.diaryloveproject.dialog.EventDialog;
import com.example.diaryloveproject.model.Event;

import java.util.ArrayList;
import java.util.List;

public class DayCounterFragment extends Fragment {
    private RecyclerView recyclerEvent;
    private List<Event> eventList;
    private EventAdapter eventAdapter;

    public DayCounterFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_counter, container, false);

        recyclerEvent = view.findViewById(R.id.recyclerEvent);
        recyclerEvent.setLayoutManager(new LinearLayoutManager(getContext()));

        eventList = new ArrayList<>();
        eventList.add(new Event("Ngày Kỷ Niệm ", 52, "yearly"));
        eventList.add(new Event("Chuyến đi Nha Trang", 65, "none"));
        eventList.add(new Event("Sinh nhật Hbao", 70, "yearly"));
        eventList.add(new Event("Du lịch Đà Lạt", 95, "none"));
        eventList.add(new Event("Sinh nhật em", 120, "yearly"));
        eventList.add(new Event("Chuyến đi Phú Quốc", 130, "none"));



        eventAdapter = new EventAdapter(eventList, (event, position) -> {
            showEditDialog(event, position);
        });
        recyclerEvent.setAdapter(eventAdapter);

        ImageButton btnCreate = view.findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(v -> {
            EventDialog dialog = new EventDialog();
            dialog.setListener(new EventDialog.EventDialogListener() {
                @Override
                public void onEventCreated(Event event) {
                    eventList.add(event);
                    eventAdapter.notifyItemInserted(eventList.size() - 1);
                }

                @Override
                public void onEventDeleted(Event event) {
                    // Không cần xoá ở đây
                }
            });
            dialog.show(getParentFragmentManager(), "EventDialog");
        });

        return view;
    }

    private void showEditDialog(Event event, int position) {
        EventDialog dialog = new EventDialog();
        dialog.setEditingEvent(event);
        dialog.setListener(new EventDialog.EventDialogListener() {
            @Override
            public void onEventCreated(Event updatedEvent) {
                eventList.set(position, updatedEvent);
                eventAdapter.notifyItemChanged(position);
            }

            @Override
            public void onEventDeleted(Event deletedEvent) {
                int pos = eventList.indexOf(deletedEvent);
                if (pos != -1) {
                    eventList.remove(pos);
                    eventAdapter.notifyItemRemoved(pos);
                }
            }
        });
        dialog.show(getParentFragmentManager(), "EditEventDialog");
    }
}
