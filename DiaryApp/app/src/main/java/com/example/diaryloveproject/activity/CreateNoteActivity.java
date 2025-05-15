package com.example.diaryloveproject.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diaryloveproject.R;
import com.example.diaryloveproject.api.RetrofitClient;
import com.example.diaryloveproject.model.DiaryNote;
import com.example.diaryloveproject.adapter.EmojiAdapter;
import com.example.diaryloveproject.model.Emoji;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateNoteActivity extends AppCompatActivity {

    private EditText editTitle, editContent;
    private RecyclerView rvEmojis;
    private EmojiAdapter emojiAdapter;
    private List<String> selectedEmojiUrls = new ArrayList<>();
    private Long userId = 1L; // Tạm thời gán cứng. Hãy thay bằng userId thực tế khi đăng nhập.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_diary);

        editTitle = findViewById(R.id.editTitle);
        editContent = findViewById(R.id.editContext);
        rvEmojis = findViewById(R.id.rvEmojis);
        Button btnSave = findViewById(R.id.btnSave);
        ImageButton btnBack = findViewById(R.id.btnBack);

        emojiAdapter = new EmojiAdapter(new ArrayList<>(), url -> {
            if (selectedEmojiUrls.contains(url)) {
                selectedEmojiUrls.remove(url);
            } else {
                selectedEmojiUrls.add(url);
            }
        });
        rvEmojis.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvEmojis.setAdapter(emojiAdapter);

        fetchEmojis();

        btnSave.setOnClickListener(view -> saveDiaryNote());

        btnBack.setOnClickListener(view -> finish());
    }

    private void fetchEmojis() {
        RetrofitClient.getInstance().getAuthApi().getAllEmojis().enqueue(new Callback<List<Emoji>>() {
            @Override
            public void onResponse(Call<List<Emoji>> call, Response<List<Emoji>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    emojiAdapter.setEmojis(response.body());
                } else {
                    Toast.makeText(CreateNoteActivity.this, "Không lấy được emoji", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Emoji>> call, Throwable t) {
                Toast.makeText(CreateNoteActivity.this, "Lỗi kết nối emoji", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveDiaryNote() {
        String title = editTitle.getText().toString().trim();
        String content = editContent.getText().toString().trim();

        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        DiaryNote note = new DiaryNote(userId, title, content, selectedEmojiUrls);

        RetrofitClient.getInstance().getAuthApi().createOrUpdateNote(note).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(CreateNoteActivity.this, "Lưu thành công!", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(CreateNoteActivity.this, "Lỗi khi lưu!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}