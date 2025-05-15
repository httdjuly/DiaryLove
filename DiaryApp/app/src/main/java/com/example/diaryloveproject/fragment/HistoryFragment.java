package com.example.diaryloveproject.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diaryloveproject.R;
import com.example.diaryloveproject.adapter.DiaryAdapter;
import com.example.diaryloveproject.model.Diary;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HistoryFragment extends Fragment {
    private RecyclerView recyclerDiary;
    private List<Diary> diaryList;
    private DiaryAdapter diaryAdapter;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate layout
        View view = inflater.inflate(R.layout.activity_history, container, false);

        // Edge-to-edge padding
        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getChildFragmentManager().beginTransaction()
                .replace(R.id.calendar_container, new CalendarFragment())
                .commit();
        // Setup RecyclerView
        recyclerDiary = view.findViewById(R.id.recyclerDiary);
        recyclerDiary.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Tạo dữ liệu mẫu phù hợp với model Diary mới
        diaryList = createSampleDiaries();
        Log.d("HistoryFragment", "Diary List size: " + diaryList.size());

        diaryAdapter = new DiaryAdapter(diaryList, new DiaryAdapter.OnDiaryClickListener() {
            @Override
            public void onDiaryClick(Diary diary) {
                Log.d("DiaryClick", "Clicked diary: " + diary.getTitle() +
                        " by " + diary.getAuthorName());
            }

            @Override
            public void onImageClick(Diary diary, int position) {
                Log.d("ImageClick", "Clicked image at position " + position +
                        " in diary: " + diary.getTitle());
            }
        });
        recyclerDiary.setAdapter(diaryAdapter);

        return view;
    }

    private List<Diary> createSampleDiaries() {
        List<Diary> samples = new ArrayList<>();

        // Diary 1
        Diary diary1 = new Diary(
                "1",
                "Ngày đầu đến trường",
                "Hôm nay là ngày đầu tiên đi học sau kỳ nghỉ hè. Mọi thứ thật mới mẻ và thú vị!",
                System.currentTimeMillis() - TimeUnit.DAYS.toMillis(2),
                "Minh Anh",
                "https://example.com/avatars/minhanh.jpg"
        );
        diary1.addImageUrl("https://example.com/images/school1.jpg");
        diary1.addEmoji("😊");
        samples.add(diary1);

        // Diary 2
        Diary diary2 = new Diary(
                "2",
                "Chuyến đi Đà Lạt",
                "Đà Lạt thật tuyệt vời với không khí trong lành và cảnh đẹp như tranh vẽ.",
                System.currentTimeMillis() - TimeUnit.DAYS.toMillis(5),
                "Tuấn Nguyễn",
                "https://example.com/avatars/tuannguyen.jpg"
        );
        diary2.addImageUrl("https://example.com/images/dalat1.jpg");
        diary2.addImageUrl("https://example.com/images/dalat2.jpg");
        diary2.addEmoji("🌄");
        samples.add(diary2);

        // Diary 3
        Diary diary3 = new Diary(
                "3",
                "Buổi tiệc sinh nhật",
                "Hôm nay là sinh nhật tôi, bạn bè tổ chức bất ngờ quá!",
                System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1),
                "Hồng Nhung",
                "https://example.com/avatars/hongnhung.jpg"
        );
        diary3.addEmoji("🎂");
        diary3.addEmoji("🎉");
        samples.add(diary3);

        return samples;
    }
}