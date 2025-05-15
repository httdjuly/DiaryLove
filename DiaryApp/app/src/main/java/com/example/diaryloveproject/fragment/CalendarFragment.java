package com.example.diaryloveproject.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diaryloveproject.R;
import com.example.diaryloveproject.adapter.CalendarAdapter;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CalendarFragment extends Fragment implements CalendarAdapter.OnItemListener
{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;
    private LinearLayout weekLayout;

    private boolean isCalendarVisible = false;  // Biến theo dõi trạng thái hiển thị Calendar

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView);
        monthYearText = view.findViewById(R.id.monthYearTV);
        weekLayout = view.findViewById(R.id.week);
        selectedDate = LocalDate.now();
        setMonthView();

        // Gán sự kiện cho nút Previous / Next
        view.findViewById(R.id.btnBack).setOnClickListener(v -> previousMonthAction());
        view.findViewById(R.id.btnNext).setOnClickListener(v -> nextMonthAction());

        // Gán sự kiện cho TextView tháng/năm
        monthYearText.setOnClickListener(v -> toggleCalendarVisibility());

        // Ban đầu ẩn RecyclerView
        calendarRecyclerView.setVisibility(View.GONE);
        weekLayout.setVisibility(View.GONE);

        return view;
    }

    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(requireContext(), daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    private ArrayList<String> daysInMonthArray(LocalDate date) {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        // Ngày đầu tháng
        LocalDate firstOfMonth = date.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue(); // Thứ 2 = 1 ... Chủ nhật = 7

        int startOffset = dayOfWeek - 1; // Nếu thứ 2 => offset = 0 (bắt đầu ngay ô đầu)

        for (int i = 0; i < daysInMonth + startOffset; i++) {
            if (i < startOffset) {
                // Không thêm các ô trống vào daysInMonthArray
                daysInMonthArray.add("");
            } else {
                daysInMonthArray.add(String.valueOf(i - startOffset + 1));
            }
        }
        return daysInMonthArray;
    }



    private String monthYearFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public void previousMonthAction() {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction() {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, String dayText) {
        if (!dayText.isEmpty()) {
            String message = "Selected Date " + dayText + " " + monthYearFromDate(selectedDate);
            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        }
    }

    // Phương thức chuyển đổi hiển thị Calendar
    private void toggleCalendarVisibility() {
        if (isCalendarVisible) {
            calendarRecyclerView.setVisibility(View.GONE);
            weekLayout.setVisibility(View.GONE);
        } else {
            calendarRecyclerView.setVisibility(View.VISIBLE);
            weekLayout.setVisibility(View.VISIBLE);
        }
        isCalendarVisible = !isCalendarVisible;
    }
}
