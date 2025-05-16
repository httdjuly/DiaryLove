package com.example.diaryloveproject.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diaryloveproject.CalendarViewHolder;
import com.example.diaryloveproject.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>
{
    private final ArrayList<String> daysOfMonth;
    private final OnItemListener onItemListener;
    private final Context context;


    public CalendarAdapter(Context context, ArrayList<String> daysOfMonth, OnItemListener onItemListener) {
        this.context = context;
        this.daysOfMonth = daysOfMonth;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);

        // Thay vì tính chiều cao cố định, chỉ cần dùng wrap_content hoặc chiều cao mặc định.
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;  // Dùng chiều cao tự động
        view.setLayoutParams(layoutParams);

        return new CalendarViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        String day = daysOfMonth.get(position);

        if (day.isEmpty()) {
            holder.itemView.setVisibility(View.GONE);
        } else {
            holder.itemView.setVisibility(View.VISIBLE);
            holder.dayOfMonth.setText(day);

            // ⚠️ Xác định ngày hiện tại trong tháng
            int dayInt = Integer.parseInt(day);
            SharedPreferences calendarPrefs = context.getSharedPreferences("calendar_state", Context.MODE_PRIVATE);
            int currentMonth = calendarPrefs.getInt("selected_month", -1);
            int currentYear = calendarPrefs.getInt("selected_year", -1);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH, currentMonth);
            calendar.set(Calendar.YEAR, currentYear);

            // ⚠️ Nếu là ngày 15/05/2025 thì hiển thị emoji
            if (dayInt == 11 && currentMonth == 4 && currentYear == 2025) { // 4 = May
                holder.imvEmoji.setVisibility(View.VISIBLE);
                holder.imvEmoji.setImageResource(R.drawable.emoji_proud); // bạn có thể thay bằng emoji khác
            }else if (dayInt == 12 && currentMonth == 4 && currentYear == 2025) { // 4 = May
                holder.imvEmoji.setVisibility(View.VISIBLE);
                holder.imvEmoji.setImageResource(R.drawable.emoji_angry); // bạn có thể thay bằng emoji khác
            }else if (dayInt == 13 && currentMonth == 4 && currentYear == 2025) { // 4 = May
                holder.imvEmoji.setVisibility(View.VISIBLE);
                holder.imvEmoji.setImageResource(R.drawable.emoji_love);
            }else if (dayInt == 14 && currentMonth == 4 && currentYear == 2025) { // 4 = May
                holder.imvEmoji.setVisibility(View.VISIBLE);
                holder.imvEmoji.setImageResource(R.drawable.emoji_angry); // bạn có thể thay bằng emoji khác
            }else if (dayInt == 15 && currentMonth == 4 && currentYear == 2025) { // 4 = May
                holder.imvEmoji.setVisibility(View.VISIBLE);
                holder.imvEmoji.setImageResource(R.drawable.emoji_happy); // bạn có thể thay bằng emoji khác
            }else if (dayInt == 16 && currentMonth == 4 && currentYear == 2025) { // 4 = May
                holder.imvEmoji.setVisibility(View.VISIBLE);
                holder.imvEmoji.setImageResource(R.drawable.emoji_angry); // bạn có thể thay bằng emoji khác
            }else {
                // Hoặc lấy từ SharedPreferences nếu có
                calendar.set(Calendar.DAY_OF_MONTH, dayInt);
                String dateKey = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.getTime());
                SharedPreferences prefs = context.getSharedPreferences("diary_emojis", Context.MODE_PRIVATE);
                int emojiResId = prefs.getInt(dateKey, -1);

                if (emojiResId != -1) {
                    holder.imvEmoji.setVisibility(View.VISIBLE);
                    holder.imvEmoji.setImageResource(emojiResId);
                } else {
                    holder.imvEmoji.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    @Override
    public int getItemCount()
    {
        return daysOfMonth.size();
    }

    public interface  OnItemListener
    {
        void onItemClick(int position, String dayText);
    }
}