package com.example.diaryloveproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.diaryloveproject.adapter.DiaryAdapter;
import com.example.diaryloveproject.model.Diary;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class NewsfeedFragment extends Fragment {
    private RecyclerView recyclerView;
    private NewsfeedAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newsfeed, container, false);

        recyclerView = view.findViewById(R.id.rvNewsfeed);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Tạo dữ liệu mẫu
        List<Diary> diaries = createSampleDiaries();

        adapter = new NewsfeedAdapter(diaries, new DiaryAdapter.OnDiaryClickListener() {
            @Override
            public void onDiaryClick(Diary diary) {
                // Xử lý khi click vào diary
            }

            @Override
            public void onImageClick(Diary diary, int position) {
                // Xử lý khi click vào hình ảnh
            }
        });

        recyclerView.setAdapter(adapter);
        return view;
    }

    private List<Diary> createSampleDiaries() {
        List<Diary> samples = new ArrayList<>();

        // Diary 1
        Diary diary1 = new Diary(
                "1",
                "Ngày đầu đến trường",
                "Hôm nay là ngày đầu tiên đi học sau kỳ nghỉ hè...",
                System.currentTimeMillis(),
                "Minh Anh",
                "https://example.com/avatar1.jpg"
        );
        diary1.addImageUrl("https://example.com/school.jpg");
        samples.add(diary1);

        // Diary 2
        Diary diary2 = new Diary(
                "2",
                "Chuyến đi Đà Lạt",
                "Đà Lạt thật tuyệt vời với không khí trong lành...",
                System.currentTimeMillis() - TimeUnit.DAYS.toMillis(2),
                "Tuấn Nguyễn",
                "https://example.com/avatar2.jpg"
        );
        samples.add(diary2);

        return samples;
    }
}