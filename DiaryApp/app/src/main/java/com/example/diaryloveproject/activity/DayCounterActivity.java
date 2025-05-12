package com.example.diaryloveproject.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diaryloveproject.R;
import com.example.diaryloveproject.adapter.EventAdapter;
import com.example.diaryloveproject.dialog.EventDialog;
import com.example.diaryloveproject.model.Event;

import java.util.ArrayList;
import java.util.List;

public class DayCounterActivity extends AppCompatActivity {
    private RecyclerView recyclerEvent;
    private List<Event> eventList;
    private EventAdapter eventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_counter);

        recyclerEvent = findViewById(R.id.recyclerEvent);
        recyclerEvent.setLayoutManager(new LinearLayoutManager(this));

        eventList = new ArrayList<>();
        // TODO: Load từ cơ sở dữ liệu hoặc bộ nhớ
        eventList.add(new Event("Thi cuối kỳ", 300,"none"));
        eventList.add(new Event("Thi cuối kỳ", 300,"yearly"));
        eventList.add(new Event("Sinh nhât của em", 100, "yearly"));
        eventList.add(new Event("Thi cuối kỳ",129,"yearly"));
        eventList.add(new Event("Sinh nhât của anh", 300,"yearly"));
        eventList.add(new Event("Thi giưa kỳ",200,"yearly"));
        eventList.add(new Event("Sinh nhât của em", 100,"yearly"));
        eventList.add(new Event("Thi cuối kỳ",129,"yearly"));
        eventAdapter = new EventAdapter(eventList);
        recyclerEvent.setAdapter(eventAdapter);


        ImageButton btnCreate = findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(v -> {
            EventDialog dialog = new EventDialog();
            dialog.setListener(event -> {
                eventList.add(event);
                eventAdapter.notifyItemInserted(eventList.size() - 1);
            });
            dialog.show(getSupportFragmentManager(), "EventDialog");

        });


    }

}