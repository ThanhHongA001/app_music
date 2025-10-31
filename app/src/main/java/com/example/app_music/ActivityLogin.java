package com.example.app_music;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_music.Utils.SupabaseManagerUser;

/**
 * 🧾 Màn hình Đăng nhập người dùng
 */
public class ActivityLogin extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private Button btnLogin;
    private TextView tvForgotPassword, tvGoToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // 👉 Ánh xạ view
        bindingView();
        // 👉 Xử lý sự kiện
        setupEvents();
    }

    private void bindingView() {
        edtEmail = findViewById(R.id.login_edt_email);
        edtPassword = findViewById(R.id.login_edt_password);
        btnLogin = findViewById(R.id.login_btn_login);
        tvForgotPassword = findViewById(R.id.login_tv_forgot_password);
        tvGoToRegister = findViewById(R.id.login_tv_go_to_register);
    }

    private void setupEvents() {
        btnLogin.setOnClickListener(v -> loginUser());

        tvGoToRegister.setOnClickListener(v -> {
            Intent intent = new Intent(ActivityLogin.this, ActivityRegister.class);
            startActivity(intent);
        });

        tvForgotPassword.setOnClickListener(v ->
                Toast.makeText(this, "Tính năng đang phát triển", Toast.LENGTH_SHORT).show());
    }

    // Hàm xử lý đăng nhập
    private void loginUser() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (!validateInput(email, password)) return;

        // 👉 Gọi Supabase API để kiểm tra đăng nhập
        new Thread(() -> {
            boolean success = SupabaseManagerUser.loginUser(email, password);
            runOnUiThread(() -> {
                if (success) {
                    Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
//                     👉 Chuyển sang màn hình chính
                     startActivity(new Intent(this, ActivityHome.class));
                     finish();

                } else {
                    Toast.makeText(this, "Sai email hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }

    // Kiểm tra dữ liệu hợp lệ
    private boolean validateInput(String email, String password) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
