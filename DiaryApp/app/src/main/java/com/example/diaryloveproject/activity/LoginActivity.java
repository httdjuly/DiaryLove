package com.example.diaryloveproject.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.diaryloveproject.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            // Áp dụng thêm padding XML của bạn + padding hệ thống
            v.setPadding(
                    systemBars.left + 32,   // hoặc thay bằng: v.getPaddingLeft() + systemBars.left
                    systemBars.top + 48,
                    systemBars.right + 32,
                    systemBars.bottom + 48
            );

            return insets;
        });
    }
}