package com.example.my_app_music.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.my_app_music.R;
import com.example.my_app_music.Utils_Api.Api.SessionManager;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityAccoutInfo extends AppCompatActivity {

    private ImageView btnBack;
    private CircleImageView imgAvatar;
    private TextView tvDisplayName, tvEmailSmall, tvFullName, tvEmail, tvPhone, tvCreatedAt;
    private Button btnEdit, btnChangePassword, btnLogout;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_accout_info);

        initViews();
        initSession();
        initData();
        initEvents();
    }

    private void initViews() {
        btnBack = findViewById(R.id.acc_info_btn_back);
        imgAvatar = findViewById(R.id.acc_info_img_avatar);
        tvDisplayName = findViewById(R.id.acc_info_txt_display_name);
        tvEmailSmall = findViewById(R.id.acc_info_txt_email_small);
        tvFullName = findViewById(R.id.acc_info_txt_fullname);
        tvEmail = findViewById(R.id.acc_info_txt_email);
        tvPhone = findViewById(R.id.acc_info_txt_phone);
        tvCreatedAt = findViewById(R.id.acc_info_txt_created_at);

        btnEdit = findViewById(R.id.acc_info_btn_edit);
        btnChangePassword = findViewById(R.id.acc_info_btn_change_password);
        btnLogout = findViewById(R.id.acc_info_btn_logout);
    }

    private void initSession() {
        sessionManager = new SessionManager(this);
    }

    private void initData() {
        if (sessionManager != null && sessionManager.isLoggedIn()) {
            String fullName = sessionManager.getFullName();
            String email = sessionManager.getEmail();

            if (fullName == null || fullName.trim().isEmpty()) {
                fullName = "Người dùng";
            }
            if (email == null) {
                email = "";
            }

            // Header
            tvDisplayName.setText(fullName);
            tvEmailSmall.setText(email);

            // Khối thông tin chi tiết
            tvFullName.setText(fullName);
            tvEmail.setText(email);

            // Tạm thời chưa có API phone & created_at nên để default
            tvPhone.setText("Chưa cập nhật");
            tvCreatedAt.setText("--/--/----");
        } else {
            // Nếu chưa đăng nhập thì quay về Home
            tvDisplayName.setText("Khách");
            tvEmailSmall.setText("");
            tvFullName.setText("Khách");
            tvEmail.setText("");
            tvPhone.setText("Chưa đăng nhập");
            tvCreatedAt.setText("--/--/----");
        }
    }

    private void initEvents() {
        btnBack.setOnClickListener(v -> onBackPressed());

        btnEdit.setOnClickListener(v -> {
            // TODO: Mở Activity/Fragment chỉnh sửa thông tin tài khoản
            // Ví dụ: startActivity(new Intent(this, ActivityEditAccountInfo.class));
        });

        btnChangePassword.setOnClickListener(v -> {
            // TODO: Màn đổi mật khẩu (nếu bạn có API tương ứng)
        });

        btnLogout.setOnClickListener(v -> {
            if (sessionManager != null) {
                sessionManager.logout();
            }
            // Sau khi logout quay về Home, Home sẽ tự update UI từ SessionManager
            Intent intent = new Intent(ActivityAccoutInfo.this, Activity_Home.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finishAffinity();
        });

        imgAvatar.setOnClickListener(v -> {
            // TODO: Mở dialog chọn ảnh/Camera để đổi avatar (sau này nếu bạn có API)
        });
    }
}
