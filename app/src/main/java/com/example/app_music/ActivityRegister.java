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
 * 🧾 Màn hình Đăng ký người dùng
 */
public class ActivityRegister extends AppCompatActivity {

    private EditText edtFullname, edtEmail, edtPassword, edtConfirmPassword;
    private Button btnRegister;
    private TextView tvGoToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        // 👉 Ánh xạ view
        bindingView();
        // 👉 Xử lý sự kiện
        setupEvents();
    }

    // Ánh xạ view
    private void bindingView() {
        edtFullname = findViewById(R.id.register_edt_fullname);
        edtEmail = findViewById(R.id.register_edt_email);
        edtPassword = findViewById(R.id.register_edt_password);
        edtConfirmPassword = findViewById(R.id.register_edt_confirm_password);
        btnRegister = findViewById(R.id.register_btn_register);
        tvGoToLogin = findViewById(R.id.register_tv_go_to_login);
    }

    // Thiết lập sự kiện
    private void setupEvents() {
        btnRegister.setOnClickListener(v -> registerUser());
        tvGoToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(ActivityRegister.this, ActivityLogin.class);
            startActivity(intent);
            finish();
        });
    }

    // Hàm xử lý đăng ký
    private void registerUser() {
        String fullname = edtFullname.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();

        // ✅ Kiểm tra hợp lệ
        if (!validateInput(fullname, email, password, confirmPassword)) return;

        // 👉 Gọi Supabase API
        new Thread(() -> {
            boolean success = SupabaseManagerUser.registerUser(fullname, email, password);
            runOnUiThread(() -> {
                if (success) {
                    Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, ActivityLogin.class));
                    finish();
                } else {
                    Toast.makeText(this, "Email đã tồn tại hoặc lỗi server!", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }

    // Hàm kiểm tra dữ liệu hợp lệ
    private boolean validateInput(String fullname, String email, String password, String confirmPassword) {
        if (TextUtils.isEmpty(fullname) || TextUtils.isEmpty(email)
                || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "Mật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Mật khẩu xác nhận không trùng khớp", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
