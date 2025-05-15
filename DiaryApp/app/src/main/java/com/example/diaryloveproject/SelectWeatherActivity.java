package com.example.diaryloveproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diaryloveproject.activity.CreateNoteActivity;
import com.example.diaryloveproject.adapter.EmojiAdapter;
import com.example.diaryloveproject.api.RetrofitClient;
import com.example.diaryloveproject.model.Emoji;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectWeatherActivity extends AppCompatActivity {
    private RecyclerView rvWeather;
    private EmojiAdapter emojiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_weather);

        String feelingEmojiUrl = getIntent().getStringExtra("feelingEmojiUrl");

        rvWeather = findViewById(R.id.rvWeather);
        emojiAdapter = new EmojiAdapter(new ArrayList<>(), weatherEmojiUrl -> {
            Intent intent = new Intent(SelectWeatherActivity.this, CreateNoteActivity.class);
            intent.putExtra("feelingEmojiUrl", feelingEmojiUrl);
            intent.putExtra("weatherEmojiUrl", weatherEmojiUrl);
            startActivity(intent);
            finish();
        });

        rvWeather.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvWeather.setAdapter(emojiAdapter);

        fetchWeatherEmojis();
    }

    private void fetchWeatherEmojis() {
        RetrofitClient.getInstance().getAuthApi()
                .getEmojisByCategory("weather")
                .enqueue(new Callback<List<Emoji>>() {
                    @Override
                    public void onResponse(Call<List<Emoji>> call, Response<List<Emoji>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            emojiAdapter.setEmojis(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Emoji>> call, Throwable t) {
                        Toast.makeText(SelectWeatherActivity.this, "Lỗi khi tải thời tiết", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}