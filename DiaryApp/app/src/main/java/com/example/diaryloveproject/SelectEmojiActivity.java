package com.example.diaryloveproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diaryloveproject.adapter.EmojiAdapter;
import com.example.diaryloveproject.api.RetrofitClient;
import com.example.diaryloveproject.model.Emoji;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectEmojiActivity extends AppCompatActivity {
    private RecyclerView rvEmotions;
    private EmojiAdapter emojiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_emoji);

        rvEmotions = findViewById(R.id.rvEmotions);
        emojiAdapter = new EmojiAdapter(new ArrayList<>(), emojiUrl -> {
            Intent intent = new Intent(SelectEmojiActivity.this, SelectWeatherActivity.class);
            intent.putExtra("feelingEmojiUrl", emojiUrl);
            startActivity(intent);
            finish();
        });

        rvEmotions.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvEmotions.setAdapter(emojiAdapter);

        fetchEmojis();
    }

    private void fetchEmojis() {
        RetrofitClient.getInstance().getAuthApi()
                .getEmojisByCategory("emotion")
                .enqueue(new Callback<List<Emoji>>() {
                    @Override
                    public void onResponse(Call<List<Emoji>> call, Response<List<Emoji>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            emojiAdapter.setEmojis(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Emoji>> call, Throwable t) {
                        Toast.makeText(SelectEmojiActivity.this, "Lỗi khi tải cảm xúc", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

