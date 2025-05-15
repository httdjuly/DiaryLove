package com.example.diaryloveproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.diaryloveproject.activity.LoginActivity;
import com.example.diaryloveproject.adapter.ImageAdapter;
import com.example.diaryloveproject.fragment.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    private final int REQUEST_SELECT_EMOJI = 1;
    private static final int REQUEST_SELECT_IMAGE = 100;

    private ImageAdapter imageAdapter;
    private ImageView imgAvatar;

    private List<String> imageUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        imgAvatar = findViewById(R.id.imgAvatar);

        imageAdapter = new ImageAdapter(imageUrls, this); // cần viết Adapter

        ImageButton btnUpload = findViewById(R.id.btnAddImg) ;
        btnUpload.setOnClickListener(v -> openImagePicker());
        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển về LoginActivity
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                // Xóa toàn bộ lịch sử activity để không quay lại được bằng nút back
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

        if (requestCode == REQUEST_SELECT_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = null;

            if (data.getClipData() != null && data.getClipData().getItemCount() > 0) {
                selectedImageUri = data.getClipData().getItemAt(0).getUri(); // lấy ảnh đầu tiên
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    imageUrls.add(imageUri.toString());
                }
            } else if (data.getData() != null) {
                selectedImageUri = data.getData();
                imageUrls.add(selectedImageUri.toString());
            }

            if (selectedImageUri != null) {
                // Đổi ảnh imgAvatar thành ảnh đã chọn
                imgAvatar.setImageURI(selectedImageUri);
                // Hoặc dùng Glide:
                // Glide.with(this).load(selectedImageUri).into(imgAvatar);
            }

            imageAdapter.notifyDataSetChanged();
        }
    }
}
