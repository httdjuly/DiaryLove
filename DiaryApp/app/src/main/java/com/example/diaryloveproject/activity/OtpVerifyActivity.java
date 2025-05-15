package com.example.diaryloveproject.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.diaryloveproject.R;
import com.example.diaryloveproject.api.ApiService;
import com.example.diaryloveproject.api.RetrofitClient;
import com.example.diaryloveproject.model.Signup;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpVerifyActivity extends AppCompatActivity {

    private EditText etOtp;
    private Button btnVerify, btnResend;
    private String email, username, password;
    private ApiService apiService;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_otp);

        // Nhận dữ liệu từ RegisterActivity
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");

        // Khởi tạo Retrofit service
        apiService = RetrofitClient.getInstance().getAuthApi();

        // Ánh xạ view
        etOtp = findViewById(R.id.etOtp);
        btnVerify = findViewById(R.id.btnConfirm);
        btnResend = findViewById(R.id.btnResend);

        btnVerify.setOnClickListener(v -> verifyOtp());
        btnResend.setOnClickListener(v -> resendOtp());
    }

    private void verifyOtp() {
        String otp = etOtp.getText().toString().trim();

        if (otp.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập mã OTP", Toast.LENGTH_SHORT).show();
            return;
        }

        // Gọi API xác thực OTP
        Call<Signup> call = apiService.verifyOtp(email, otp, username, password);
        call.enqueue(new Callback<Signup>() {
            @Override
            public void onResponse(Call<Signup> call, Response<Signup> response) {
                if (response.isSuccessful()) {
                    Signup responseBody = response.body();
                    if (responseBody != null) {
                        Toast.makeText(OtpVerifyActivity.this,
                                "Xác thực thành công",
                                Toast.LENGTH_SHORT).show();

                        // Chuyển về màn hình đăng nhập
                        Intent intent = new Intent(OtpVerifyActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(OtpVerifyActivity.this, "Xác thực thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(OtpVerifyActivity.this, "Lỗi server: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Signup> call, Throwable t) {
                Toast.makeText(OtpVerifyActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void resendOtp() {
        // Gọi API gửi lại OTP
        Call<Map<String, Object>> call = apiService.resendOtp(email);
        call.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (response.isSuccessful()) {
                    Map<String, Object> responseBody = response.body();
                    if (responseBody != null && Boolean.TRUE.equals(responseBody.get("success"))) {
                        Toast.makeText(OtpVerifyActivity.this,
                                "Đã gửi lại mã OTP",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        String errorMessage = responseBody != null ?
                                responseBody.get("message").toString() : "Gửi lại OTP thất bại";
                        Toast.makeText(OtpVerifyActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(OtpVerifyActivity.this, "Lỗi server: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                Toast.makeText(OtpVerifyActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}