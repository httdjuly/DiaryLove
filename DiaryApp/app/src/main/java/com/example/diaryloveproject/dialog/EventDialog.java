package com.example.diaryloveproject.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.diaryloveproject.R;
import com.example.diaryloveproject.model.Event;
import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EventDialog extends DialogFragment {

    public interface EventDialogListener {
        void onEventCreated(Event event);
    }

    private EventDialogListener listener;

    public void setListener(EventDialogListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_event, null);

        EditText editTitle = view.findViewById(R.id.editTitle);
        EditText editDays = view.findViewById(R.id.editDate);
        Button btnSave = view.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> {
            Spinner spinnerRepeat = view.findViewById(R.id.spinnerRepeat);
            String repeatOptionText = spinnerRepeat.getSelectedItem().toString();

            String repeatType;
            switch (repeatOptionText) {
                case "Lặp lại hàng tháng":
                    repeatType = "monthly";
                    break;
                case "Lặp lại hàng năm":
                    repeatType = "yearly";
                    break;
                default:
                    repeatType = "none";
                    break;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            sdf.setLenient(false);
            String title = editTitle.getText().toString().trim();

            String dateStr = editDays.getText().toString().trim();
            try {
                Date inputDate = sdf.parse(dateStr);
                Date currentDate = new Date();

                Calendar inputCal = Calendar.getInstance();
                inputCal.setTime(inputDate);
                Calendar currentCal = Calendar.getInstance();
                currentCal.setTime(currentDate);

                // Nếu có chọn lặp lại
                if (!repeatType.equals("none") && inputDate.before(currentDate)) {
                    if (repeatType.equals("yearly")) {
                        // Đặt cùng ngày/tháng nhưng năm là năm hiện tại hoặc năm sau
                        inputCal.set(Calendar.YEAR, currentCal.get(Calendar.YEAR));
                        // Nếu ngày đã qua trong năm nay, chuyển sang năm sau
                        if (inputCal.before(currentCal)) {
                            inputCal.add(Calendar.YEAR, 1);
                        }
                    } else if (repeatType.equals("monthly")) {
                        // Đặt cùng ngày nhưng tháng/năm là hiện tại hoặc sau
                        inputCal.set(Calendar.YEAR, currentCal.get(Calendar.YEAR));
                        inputCal.set(Calendar.MONTH, currentCal.get(Calendar.MONTH));
                        // Nếu ngày đã qua trong tháng này, chuyển sang tháng sau
                        if (inputCal.before(currentCal)) {
                            inputCal.add(Calendar.MONTH, 1);
                        }
                    }
                    inputDate = inputCal.getTime();
                }

                // Tính số ngày chênh lệch
                long diffMillis = inputDate.getTime() - currentDate.getTime();
                int days = (int) (diffMillis / (1000 * 60 * 60 * 24));

                if (listener != null) {
                    listener.onEventCreated(new Event(title, days, repeatType));
                }
                dismiss();
            } catch (ParseException e) {
                editDays.setError("Sai định dạng dd/MM/yyyy");
            }
        });

        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(view);
        return dialog;
    }
    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            // Thiết lập chiều rộng = 90% màn hình, chiều cao là WRAP_CONTENT
            int width = (int) (requireContext().getResources().getDisplayMetrics().widthPixels * 0.9);
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            getDialog().getWindow().setLayout(width, height);
        }
    }
}
