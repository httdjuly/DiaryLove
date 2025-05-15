package com.example.diaryloveproject.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.Map;

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
        setContentView(R.layout.activity_create_note);

        // Lấy userId từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("login_pref", MODE_PRIVATE);
        userId = sharedPreferences.getLong("userId", -1);

        if (userId == -1) {
            Toast.makeText(this, "Bạn chưa đăng nhập hoặc thông tin user không hợp lệ", Toast.LENGTH_SHORT).show();
            finish(); // hoặc xử lý khác
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

        // Gọi API lưu ghi chú
        RetrofitClient.getInstance().getAuthApi().createOrUpdateNote(note)
                .enqueue(new Callback<Map<String, String>>() {
                    @Override
                    public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            String msg = response.body().get("message");
                            Toast.makeText(CreateNoteActivity.this, msg != null ? msg : "Lưu thành công!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(CreateNoteActivity.this, "Lỗi khi lưu! Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Map<String, String>> call, Throwable t) {
                        Toast.makeText(CreateNoteActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}