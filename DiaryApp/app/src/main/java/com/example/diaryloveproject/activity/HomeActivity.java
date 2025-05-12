package com.example.diaryloveproject.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diaryloveproject.R;
import com.example.diaryloveproject.adapter.DiaryAdapter;
import com.example.diaryloveproject.fragment.CalendarFragment;
import com.example.diaryloveproject.model.Diary;
import com.example.diaryloveproject.model.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerDiary;
    private List<Diary> diaryList;
    private DiaryAdapter diaryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.calendar_container, new CalendarFragment())
                .commit();

        recyclerDiary = findViewById(R.id.recyclerDiary);
        recyclerDiary.setLayoutManager(new LinearLayoutManager(this));

        diaryList = new ArrayList<>();
        // Diary 1
        Diary diary1 = new Diary(
                "1",
                "Ngày đầu đến trường",
                "Hôm nay là ngày đầu tiên đi học sau kỳ nghỉ hè. Mọi thứ thật mới mẻ và thú vị!",
                System.currentTimeMillis() - TimeUnit.DAYS.toMillis(2)
        );
        Diary diary2 = new Diary(
                "2",
                "Chuyến đi Đà Lạt",
                "Đà Lạt thật tuyệt vời với không khí trong lành và cảnh đẹp như tranh vẽ.",
                System.currentTimeMillis() - TimeUnit.DAYS.toMillis(5)
        );
        // Add diaries to the list
        diaryList.add(diary1);
        diaryList.add(diary2);
        Log.d("HomeActivity", "Diary List size: " + diaryList.size());

        diaryAdapter = new DiaryAdapter(diaryList, new DiaryAdapter.OnDiaryClickListener() {
            @Override
            public void onDiaryClick(Diary diary) {
                Log.d("DiaryClick", "Clicked diary: " + diary.getTitle());
            }

            @Override
            public void onImageClick(Diary diary, int position) {
                Log.d("ImageClick", "Clicked image at position " + position + " in diary: " + diary.getTitle());
            }
        });
        recyclerDiary.setAdapter(diaryAdapter);

    }
}



