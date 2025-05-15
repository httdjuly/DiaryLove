package com.example.diaryloveproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SelectEmojiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_emoji);

        setupEmojiClick(R.id.imgAngry, R.drawable.emoji_angry);
        setupEmojiClick(R.id.imgLove, R.drawable.emoji_love);
        setupEmojiClick(R.id.imgHappy, R.drawable.emoji_happy);
        setupEmojiClick(R.id.imgSad, R.drawable.emoji_sad);
        setupEmojiClick(R.id.imgProud, R.drawable.emoji_proud);
        setupEmojiClick(R.id.imgShy, R.drawable.emoji_shy);
    }

    private void setupEmojiClick(int viewId, int emojiResId) {
        findViewById(viewId).setOnClickListener(v -> {
            Intent intent = new Intent(SelectEmojiActivity.this, SelectWeatherActivity.class);
            intent.putExtra("feelingEmojiResId", emojiResId);
            startActivity(intent);
            finish();
        });
    }

}
