package com.example.diaryloveproject;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.diaryloveproject.fragment.DayCounterFragment;
import com.example.diaryloveproject.fragment.HistoryFragment;
import com.example.diaryloveproject.fragment.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Sử dụng layout bạn đã cung cấp

        // Ánh xạ views

        bottomNavigation = findViewById(R.id.bottom_navigation);



        // Xử lý sự kiện BottomNavigationView
        bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            // Thay thế bằng ID menu và Fragment của bạn
            if (item.getItemId() == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (item.getItemId() == R.id.nav_daycounter) {
                selectedFragment = new DayCounterFragment();
            } else if (item.getItemId() == R.id.nav_history) {
                selectedFragment = new HistoryFragment();
            }   else if (item.getItemId() == R.id.nav_newsfeed) {
                selectedFragment = new NewsfeedFragment();}

            // Thay thế fragment
            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_mainContent, selectedFragment)
                        .commit();
            }

            return true;
        });

        // Load fragment mặc định khi khởi động
        if (savedInstanceState == null) {
            bottomNavigation.setSelectedItemId(R.id.nav_home); // Chọn item mặc định
        }
    }
}