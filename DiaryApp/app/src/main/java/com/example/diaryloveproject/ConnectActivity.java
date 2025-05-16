package com.example.diaryloveproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ConnectActivity extends AppCompatActivity {
    ImageButton btnBack ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_connect);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnBack = findViewById(R.id.btnBack);

        Button btnAddFr = findViewById(R.id.btnAddFr);

        btnAddFr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(ConnectActivity.this);
                View dialogView = inflater.inflate(R.layout.dialog_addfriend, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(ConnectActivity.this);
                builder.setView(dialogView);

                AlertDialog dialog = builder.create();

                // Cho phép click ra ngoài để đóng dialog
                dialog.setCanceledOnTouchOutside(true);

                // Khi dialog bị đóng (dù click ra ngoài hay bấm nút), chuyển về HomeActivity
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        Intent intent = new Intent(ConnectActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish(); // nếu muốn kết thúc Activity hiện tại
                    }
                });

                dialog.show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}