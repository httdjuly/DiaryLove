package com.example.diaryloveproject.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.diaryloveproject.api.ApiService;

import com.example.diaryloveproject.MainActivity;
import com.example.diaryloveproject.R;
import com.example.diaryloveproject.api.RetrofitClient;
import com.example.diaryloveproject.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView forgetText;
    private TextView registerText;
    private ScrollView mainLayout;
    private CheckBox checkboxRemember;
    private SharedPreferences sharedPreferences;
    private static final String KEY_USER_ID = "userId";
    private static final String PREF_NAME = "login_pref";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_REMEMBER = "remember";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Ánh xạ view
        mainLayout = findViewById(R.id.main);
        etEmail = findViewById(R.id.etEmail);
        forgetText = findViewById(R.id.txtForget);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        registerText = findViewById(R.id.registerText);
        checkboxRemember = findViewById(R.id.checkboxRemember);
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        // Load dữ liệu nếu đã chọn "Ghi nhớ"
        boolean isRemembered = sharedPreferences.getBoolean(KEY_REMEMBER, false);
        if (isRemembered) {
            etEmail.setText(sharedPreferences.getString(KEY_EMAIL, ""));
            etPassword.setText(sharedPreferences.getString(KEY_PASSWORD, ""));
            checkboxRemember.setChecked(true);
        }
        forgetText.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });

        // Xử lý click "Đăng ký ngay"
        registerText.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        // Xử lý đăng nhập
        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty()) {
                etEmail.setError("Vui lòng nhập email");
                etEmail.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                etPassword.setError("Vui lòng nhập mật khẩu");
                etPassword.requestFocus();
                return;
            }

            // Gọi API đăng nhập
            ApiService apiService = RetrofitClient.getInstance().getAuthApi();
            Call<User> call = apiService.login(email, password);

            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        User user = response.body();
                        if (user != null) {
                            Toast.makeText(LoginActivity.this,
                                    "Xin chào " + user.getUsername(), Toast.LENGTH_SHORT).show();

                            // Ghi nhớ đăng nhập nếu cần
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            if (checkboxRemember.isChecked()) {
                                editor.putString(KEY_EMAIL, email);
                                editor.putString(KEY_PASSWORD, password);
                                editor.putBoolean(KEY_REMEMBER, true);
                            } else {
                                editor.clear();
                            }
                            editor.putLong(KEY_USER_ID, user.getId());
                            editor.apply();

                            // Chuyển sang MainActivity
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this,
                                "Email hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(LoginActivity.this,
                            "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}