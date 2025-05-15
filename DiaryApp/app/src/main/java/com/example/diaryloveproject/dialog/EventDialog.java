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
        void onEventDeleted(Event event); // Thêm dòng này

    }

    private Event eventToEdit = null;
    private EventDialogListener listener;

    public EventDialog() {} // Constructor rỗng

    public void setListener(EventDialogListener listener) {
        this.listener = listener;
    }

    public void setEditingEvent(Event eventToEdit) {
        this.eventToEdit = eventToEdit;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_create_event, null);

        EditText editTitle = view.findViewById(R.id.editTitle);
        EditText editDays = view.findViewById(R.id.editDate);
        Spinner spinnerRepeat = view.findViewById(R.id.spinnerRepeat);
        Button btnSave = view.findViewById(R.id.btnSave);
        Button btnDelete = view.findViewById(R.id.btnDelete);

        // Ẩn nút Xoá nếu không phải đang chỉnh sửa
        if (eventToEdit == null) {
            btnDelete.setVisibility(View.GONE);
        } else {
            btnDelete.setVisibility(View.VISIBLE);
        }
        btnDelete.setOnClickListener(v -> {
            if (listener != null && eventToEdit != null) {
                listener.onEventDeleted(eventToEdit);
            }
            dismiss();
        });
        // Nếu đang chỉnh sửa event, điền sẵn dữ liệu
        if (eventToEdit != null) {
            editTitle.setText(eventToEdit.getTitle());

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis()); // Lấy thời gian hiện tại
            cal.add(Calendar.DAY_OF_YEAR, eventToEdit.getDay()); // Cộng số ngày từ `eventToEdit`
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            editDays.setText(sdf.format(cal.getTime()));

            switch (eventToEdit.getRepeatType()) {
                case "monthly":
                    spinnerRepeat.setSelection(1);
                    break;
                case "yearly":
                    spinnerRepeat.setSelection(2);
                    break;
                default:
                    spinnerRepeat.setSelection(0);
                    break;
            }
        }

        btnSave.setOnClickListener(v -> {
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

// Xóa phần thời gian, chỉ giữ lại ngày
                inputCal.set(Calendar.HOUR_OF_DAY, 0);
                inputCal.set(Calendar.MINUTE, 0);
                inputCal.set(Calendar.SECOND, 0);
                inputCal.set(Calendar.MILLISECOND, 0);

                Calendar currentCal = Calendar.getInstance();
                currentCal.setTime(currentDate);

// Xóa phần thời gian, chỉ giữ lại ngày
                currentCal.set(Calendar.HOUR_OF_DAY, 0);
                currentCal.set(Calendar.MINUTE, 0);
                currentCal.set(Calendar.SECOND, 0);
                currentCal.set(Calendar.MILLISECOND, 0);

// Tính số ngày giữa hai ngày


                if (!repeatType.equals("none") && inputDate.before(currentDate)) {
                    if (repeatType.equals("yearly")) {
                        inputCal.set(Calendar.YEAR, currentCal.get(Calendar.YEAR));
                        if (inputCal.before(currentCal)) inputCal.add(Calendar.YEAR, 1);
                    } else if (repeatType.equals("monthly")) {
                        inputCal.set(Calendar.YEAR, currentCal.get(Calendar.YEAR));
                        inputCal.set(Calendar.MONTH, currentCal.get(Calendar.MONTH));
                        if (inputCal.before(currentCal)) inputCal.add(Calendar.MONTH, 1);
                    }
                    inputDate = inputCal.getTime();
                }

                long diffMillis = inputCal.getTimeInMillis() - currentCal.getTimeInMillis();
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
            int width = (int) (requireContext().getResources().getDisplayMetrics().widthPixels * 0.9);
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            getDialog().getWindow().setLayout(width, height);
        }
    }
}
