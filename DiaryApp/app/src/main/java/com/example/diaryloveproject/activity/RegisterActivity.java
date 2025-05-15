package com.example.diaryloveproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.diaryloveproject.R;
import com.example.diaryloveproject.api.ApiService;
import com.example.diaryloveproject.api.RetrofitClient;
import com.example.diaryloveproject.model.Signup;
import com.example.diaryloveproject.model.SignupRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername, etEmail, etPassword, etConfirmPassword;
    private Button btnRegister;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Khởi tạo Retrofit service
        apiService = RetrofitClient.getInstance().getAuthApi();

        // Ánh xạ view
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> register());
    }

    private void register() {
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        // Kiểm tra dữ liệu đầu vào
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        // Gọi API đăng ký
        SignupRequest request = new SignupRequest(username,email,password,confirmPassword);
        Call<Signup> call = apiService.signup(request);
        call.enqueue(new Callback<Signup>() {
            @Override
            public void onResponse(Call<Signup> call, Response<Signup> response) {
                if (response.isSuccessful()) {
                    Signup signupResponse = response.body();
                    if (signupResponse != null) {
                        // Nếu API trả về thành công (status code 2xx) và có dữ liệu
                        // thì coi như đăng ký thành công
                        Toast.makeText(RegisterActivity.this,
                                "Đăng ký thành công. Vui lòng kiểm tra email để xác thực OTP",
                                Toast.LENGTH_SHORT).show();

                        // Chuyển sang màn hình xác thực OTP
                        try {
                            Intent intent = new Intent(RegisterActivity.this, OtpVerifyActivity.class);
                            intent.putExtra("email", email);
                            intent.putExtra("username", username);
                            intent.putExtra("password", password);
                            startActivity(intent);
                            Log.d("NAVIGATION", "OtpVerifyActivity started successfully");
                        } catch (Exception e) {
                            Log.e("NAVIGATION", "Failed to start activity", e);
                        }

                    }
                } else {
                    // Xử lý khi response không thành công (status code 4xx, 5xx)
                    Toast.makeText(RegisterActivity.this,
                            "Đăng ký thất bại. Vui lòng thử lại",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Signup> call, Throwable t) {
                Toast.makeText(RegisterActivity.this,
                        "Lỗi kết nối: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}