package com.example.diaryloveproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.diaryloveproject.activity.CreateNoteActivity;

public class SelectWeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_weather); // hoặc layout riêng nếu có

        int feelingEmojiResId = getIntent().getIntExtra("feelingEmojiResId", -1);

        setupEmojiClick(R.id.imgCloud, R.drawable.emoji_cloud, feelingEmojiResId);
        setupEmojiClick(R.id.imgLight, R.drawable.emoji_light, feelingEmojiResId);
        setupEmojiClick(R.id.imgRain, R.drawable.emoji_rain, feelingEmojiResId);
        setupEmojiClick(R.id.imgSun, R.drawable.emoji_sun, feelingEmojiResId);
        setupEmojiClick(R.id.imgRainbow, R.drawable.emoji_rainbow, feelingEmojiResId);
        setupEmojiClick(R.id.imgWind, R.drawable.emoji_wind, feelingEmojiResId);
    }

    private void setupEmojiClick(int viewId, int weatherEmojiResId, int feelingEmojiResId) {
        findViewById(viewId).setOnClickListener(v -> {
            Intent intent = new Intent(SelectWeatherActivity.this, CreateNoteActivity.class);
            intent.putExtra("feelingEmojiResId", feelingEmojiResId);
            intent.putExtra("weatherEmojiResId", weatherEmojiResId);
            startActivity(intent);
            finish(); // đóng activity
        });
    }

}