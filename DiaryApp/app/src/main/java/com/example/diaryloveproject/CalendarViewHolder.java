package com.example.diaryloveproject;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diaryloveproject.adapter.CalendarAdapter;

public class CalendarViewHolder extends RecyclerView.ViewHolder {
    public final TextView dayOfMonth;
    public final ImageView imvEmoji; // Thêm reference tới ImageView emoji
    private final CalendarAdapter.OnItemListener onItemListener;

    public CalendarViewHolder(@NonNull View itemView, CalendarAdapter.OnItemListener onItemListener) {
        super(itemView);
        dayOfMonth = itemView.findViewById(R.id.cellDayText);
        imvEmoji = itemView.findViewById(R.id.imv_emoji); // Ánh xạ ImageView

        this.onItemListener = onItemListener;

        itemView.setOnClickListener(v -> {
            if (!dayOfMonth.getText().toString().isEmpty()) {
                onItemListener.onItemClick(getAdapterPosition(), dayOfMonth.getText().toString());
            }
        });
    }
//    @Override
//    public void onClick(View view)
//    {
//        onItemListener.onItemClick(getAdapterPosition(), (String) dayOfMonth.getText());
//    }

    // Thêm phương thức để set emoji
    public void setEmoji(int emojiResId) {
        if (emojiResId != -1) {
            imvEmoji.setVisibility(View.VISIBLE);
            imvEmoji.setImageResource(emojiResId);
        } else {
            imvEmoji.setVisibility(View.GONE);
        }
    }

}