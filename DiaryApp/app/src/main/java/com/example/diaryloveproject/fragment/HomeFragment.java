package com.example.diaryloveproject.fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.diaryloveproject.ConnectActivity;
import com.example.diaryloveproject.ProfileActivity;
import com.example.diaryloveproject.R;
import com.example.diaryloveproject.api.ApiService;
import com.example.diaryloveproject.api.RetrofitClient;
import com.example.diaryloveproject.model.Friendship;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;


public class HomeFragment extends Fragment {
    ImageView imgMyAvt, imgPartner;
    private Uri imageUri;
    private ActivityResultLauncher <Intent> launcher ;

    public HomeFragment() {
        // Constructor rỗng bắt buộc
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Tìm ImageView avatar
      imgMyAvt = view.findViewById(R.id.imgMyAvt);
       imgPartner = view.findViewById(R.id.imgPartnerAvt);

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            String uriStr = data.getStringExtra("updatedImageUri");
                            if (uriStr != null) {
                                imageUri = Uri.parse(uriStr);
                                // imgMyAvt.setImageURI(imageUri)
                                Glide.with(requireContext()).load(imageUri).into(imgMyAvt);
                            }
                        }
                    }
                }
        );

       // imageUri = Uri.parse(imgMyAvt.get)
        imgPartner.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), ConnectActivity.class);
            startActivity(intent);
        });
        imgMyAvt.setOnClickListener(v -> {
                Intent intent = new Intent(requireContext(), ProfileActivity.class);

                launcher.launch(intent);
        });


        return view;
    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
//            imageUri = data.getData();
//            imgMyAvt.setImageURI(imageUri);
//        }
//    }
}

//
////        ImageView imgPartnerAvt = view.findViewById(R.id.imgPartnerAvt);
////
////        imgPartnerAvt.setOnClickListener(v -> {
////            Long myId = ...; // Lấy ID người dùng hiện tại từ local/sharedPreferences
////            Long partnerId = ...; // ID người kia
////
////            ApiService apiService = RetrofitClient.getInstance().getAuthApi();
////            apiService.checkFriendship(myId, partnerId).enqueue(new Callback<Friendship>() {
////                @Override
////                public void onResponse(Call<Friendship> call, Response<Friendship> response) {
////                    if (response.isSuccessful()) {
////                        Friendship friendship = response.body();
////                        if (friendship != null && friendship.isAccepted()) {
////                            // Đã là bạn => không làm gì
////                            Toast.makeText(requireContext(), "Đã kết bạn", Toast.LENGTH_SHORT).show();
////                        } else {
////                            // Chưa là bạn => mở ConnectActivity
////                            Intent intent = new Intent(requireContext(), ConnectActivity.class);
////                            intent.putExtra("partnerId", partnerId);
////                            startActivity(intent);
////                        }
////                    } else {
////                        Toast.makeText(requireContext(), "Không kiểm tra được trạng thái kết bạn", Toast.LENGTH_SHORT).show();
////                    }
////                }
//
//                @Override
//                public void onFailure(Call<Friendship> call, Throwable t) {
//                    Toast.makeText(requireContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
//                }
//            });
//        });
//
//    }

