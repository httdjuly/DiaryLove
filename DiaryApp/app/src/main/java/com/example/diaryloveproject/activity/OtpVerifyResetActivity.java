package com.example.diaryloveproject.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.diaryloveproject.R;
import com.example.diaryloveproject.api.ApiService;
import com.example.diaryloveproject.api.RetrofitClient;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpVerifyResetActivity extends AppCompatActivity {

    private EditText etOtp;
    private Button btnVerify;
    private String email;
    private ApiService apiService;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_otp);

        // Nhận email từ ForgotPasswordActivity
        email = getIntent().getStringExtra("email");

        // Khởi tạo Retrofit
        apiService = RetrofitClient.getInstance().getAuthApi();

        // Ánh xạ view
        etOtp = findViewById(R.id.etOtp);
        btnVerify = findViewById(R.id.btnConfirm);

        // Xác minh OTP
        btnVerify.setOnClickListener(v -> verifyOtp());
    }

    private void verifyOtp() {
        String otp = etOtp.getText().toString().trim();

        if (otp.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập mã OTP", Toast.LENGTH_SHORT).show();
            return;
        }

        // Gọi API xác minh OTP
        Call<Map<String, Object>> call = apiService.verifyPasswordResetOtp(email, otp);
        call.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Map<String, Object> body = response.body();
                    Boolean success = (Boolean) body.get("success");

                    if (Boolean.TRUE.equals(success)) {
                        Toast.makeText(OtpVerifyResetActivity.this, "Xác thực thành công", Toast.LENGTH_SHORT).show();

                        // Chuyển sang trang đặt lại mật khẩu
                        Intent intent = new Intent(OtpVerifyResetActivity.this, NewPasswordActivity.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(OtpVerifyResetActivity.this,
                                body.get("message").toString(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(OtpVerifyResetActivity.this, "Lỗi server: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                Toast.makeText(OtpVerifyResetActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}