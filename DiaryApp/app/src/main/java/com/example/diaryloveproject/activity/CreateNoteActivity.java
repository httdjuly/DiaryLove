package com.example.diaryloveproject.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.diaryloveproject.R;
import com.example.diaryloveproject.api.RetrofitClient;
import com.example.diaryloveproject.model.DiaryNote;
import com.example.diaryloveproject.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateNoteActivity extends AppCompatActivity {

    private EditText editTitle, editContent;
    private ImageView ivFeelingEmoji, ivWeatherEmoji;
    private String feelingEmojiUrl, weatherEmojiUrl;
    private Long userId; // Lấy từ Intent

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_diary);

        // Nhận userId từ Intent
        userId = getIntent().getLongExtra("userId", -1L);
        if (userId == -1L) {
            Toast.makeText(this, "Không tìm thấy thông tin user, vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        editTitle = findViewById(R.id.editTitle);
        editContent = findViewById(R.id.editContext);
        ivFeelingEmoji = findViewById(R.id.ivFeelingEmoji);
        ivWeatherEmoji = findViewById(R.id.ivWeatherEmoji);
        Button btnSave = findViewById(R.id.btnSave);
        ImageButton btnBack = findViewById(R.id.btnBack);

        Intent intent = getIntent();
        feelingEmojiUrl = intent.getStringExtra("feelingEmojiUrl");
        weatherEmojiUrl = intent.getStringExtra("weatherEmojiUrl");

        // Hiển thị emoji đã chọn nếu có
        if (feelingEmojiUrl != null && !feelingEmojiUrl.isEmpty()) {
            Glide.with(this).load(feelingEmojiUrl).into(ivFeelingEmoji);
        }
        if (weatherEmojiUrl != null && !weatherEmojiUrl.isEmpty()) {
            Glide.with(this).load(weatherEmojiUrl).into(ivWeatherEmoji);
        }

        btnSave.setOnClickListener(v -> saveDiaryNote());
        btnBack.setOnClickListener(v -> finish());
    }

    private void saveDiaryNote() {
        String title = editTitle.getText().toString().trim();
        String content = editContent.getText().toString().trim();

        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo danh sách emojiUrls
        List<String> emojiUrls = new ArrayList<>();
        if (feelingEmojiUrl != null) emojiUrls.add(feelingEmojiUrl);
        if (weatherEmojiUrl != null) emojiUrls.add(weatherEmojiUrl);

        // Khởi tạo DiaryNote với constructor 4 tham số
        DiaryNote note = new DiaryNote(userId, title, content, emojiUrls);

        note.setTimestamp(LocalDateTime.now());

        // Gọi API lưu ghi chú
        RetrofitClient.getInstance().getAuthApi().createOrUpdateNote(note)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(CreateNoteActivity.this, "Lưu thành công!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(CreateNoteActivity.this, "Lỗi khi lưu! Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(CreateNoteActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}