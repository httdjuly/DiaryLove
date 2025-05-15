package com.example.diaryloveproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.diaryloveproject.adapter.EmojiAdapter;
import com.example.diaryloveproject.adapter.ImageAdapter;
import com.example.diaryloveproject.fragment.HistoryFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CreateDiaryActivity extends AppCompatActivity {

    private List<Integer> selectedEmojis = new ArrayList<>();
    private EmojiAdapter emojiAdapter;
    private final int REQUEST_SELECT_EMOJI = 1;
    private static final int REQUEST_SELECT_IMAGE = 100;
    private List<String> imageUrls = new ArrayList<>();
    private ImageAdapter imageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_diary);

        RecyclerView rvEmojis = findViewById(R.id.rvEmojis);
        rvEmojis.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ImageButton btnAddImg = findViewById(R.id.btnAddImg);
        RecyclerView rvImages = findViewById(R.id.rvImages);

        rvImages.setLayoutManager(new GridLayoutManager(this, 4));
        imageAdapter = new ImageAdapter(imageUrls, this); // cần viết Adapter
        rvImages.setAdapter(imageAdapter);
        btnAddImg.setOnClickListener(v -> openImagePicker());


        // Nhận emoji từ intent
        int feelingEmojiResId = getIntent().getIntExtra("feelingEmojiResId", -1);
        int weatherEmojiResId = getIntent().getIntExtra("weatherEmojiResId", -1);

        if (feelingEmojiResId != -1) selectedEmojis.add(feelingEmojiResId);
        if (weatherEmojiResId != -1) selectedEmojis.add(weatherEmojiResId);

        emojiAdapter = new EmojiAdapter(selectedEmojis);
        rvEmojis.setAdapter(emojiAdapter);
        ImageButton btnBack = findViewById(R.id.btnBack);
        // Sau khi đã nhận được emoji
        SharedPreferences prefs = getSharedPreferences("diary_emojis", MODE_PRIVATE);
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        if (feelingEmojiResId != -1) {
            prefs.edit().putInt(today, feelingEmojiResId).apply();
        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay về HistoryActivity
                Intent intent = new Intent(CreateDiaryActivity.this, HistoryFragment.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Xóa các activity phía trên
                startActivity(intent);
                finish(); // Kết thúc CreateDiaryActivity
            }
        });
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_SELECT_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SELECT_EMOJI && resultCode == RESULT_OK && data != null) {
            int emojiResId = data.getIntExtra("emojiResId", -1);
            if (emojiResId != -1) {
                selectedEmojis.add(emojiResId);
                emojiAdapter.notifyItemInserted(selectedEmojis.size() - 1);
            }
        }
        if (requestCode == REQUEST_SELECT_IMAGE && resultCode == RESULT_OK && data != null) {
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    imageUrls.add(imageUri.toString());  // CHUYỂN THÀNH STRING
                }
            } else if (data.getData() != null) {
                Uri imageUri = data.getData();
                imageUrls.add(imageUri.toString());  // CHUYỂN THÀNH STRING
            }
            imageAdapter.notifyDataSetChanged();
        }



    }


}