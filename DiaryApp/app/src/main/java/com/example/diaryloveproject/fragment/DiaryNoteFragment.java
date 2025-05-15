package com.example.diaryloveproject.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diaryloveproject.R;
import com.example.diaryloveproject.SelectEmojiActivity;
import com.example.diaryloveproject.adapter.DiaryNoteAdapter;
import com.example.diaryloveproject.api.ApiService;
import com.example.diaryloveproject.api.RetrofitClient;
import com.example.diaryloveproject.model.DiaryNote;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiaryNoteFragment extends Fragment {

    private RecyclerView recyclerDiary;
    private DiaryNoteAdapter adapter;
    private List<DiaryNote> noteList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_history, container, false);

        // Lấy SharedPreferences
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        long userId = sharedPreferences.getLong("userId", -1);

        if (userId == -1) {
            Toast.makeText(getContext(), "Chưa đăng nhập hoặc không lấy được userId", Toast.LENGTH_SHORT).show();
            // Xử lý nếu chưa đăng nhập, ví dụ quay lại màn hình login
        } else {
            // Tiếp tục dùng userId gọi API lấy note
            fetchNotes(userId, view);
        }
        // Hiển thị CalendarFragment ở đầu
        getChildFragmentManager().beginTransaction()
                .replace(R.id.calendar_container, new CalendarFragment())
                .commit();
        ImageButton btnCreate = view.findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), SelectEmojiActivity.class);
            startActivity(intent);
        });

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        long userId = sharedPreferences.getLong("userId", -1);
        if (userId != -1 && getView() != null) {
            fetchNotes(userId, getView());
        }
    }
    private void fetchNotes(long userId, View view) {
        RecyclerView recyclerDiary = view.findViewById(R.id.recyclerDiary);
        recyclerDiary.setLayoutManager(new LinearLayoutManager(getContext()));

        ApiService apiService = RetrofitClient.getInstance().getAuthApi();
        apiService.getAllNotes(userId).enqueue(new Callback<List<DiaryNote>>() {
            @Override
            public void onResponse(Call<List<DiaryNote>> call, Response<List<DiaryNote>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<DiaryNote> noteList = response.body();
                    DiaryNoteAdapter adapter = new DiaryNoteAdapter(noteList);
                    recyclerDiary.setAdapter(adapter);
                } else {
                    // Log lỗi chi tiết
                    System.out.println("Response code: " + response.code());
                    try {
                        System.out.println("Error body: " + response.errorBody().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getContext(), "Lỗi khi tải dữ liệu note", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DiaryNote>> call, Throwable t) {
                Log.e("DiaryNoteFragment", "API call failed", t);
                Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
}
