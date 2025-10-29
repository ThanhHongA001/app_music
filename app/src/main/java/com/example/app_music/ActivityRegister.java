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

public class ActivityRegister extends AppCompatActivity {

    private EditText edtFullname, edtEmail, edtPassword, edtConfirmPassword;
    private Button btnRegister;
    private TextView tvGoToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        // Ánh xạ view
        edtFullname = findViewById(R.id.register_edt_fullname);
        edtEmail = findViewById(R.id.register_edt_email);
        edtPassword = findViewById(R.id.register_edt_password);
        edtConfirmPassword = findViewById(R.id.register_edt_confirm_password);
        btnRegister = findViewById(R.id.register_btn_register);
        tvGoToLogin = findViewById(R.id.register_tv_go_to_login);

        // 👉 Sự kiện nút đăng ký
        btnRegister.setOnClickListener(v -> registerUser());

        // 👉 Chuyển sang màn hình Đăng nhập
        tvGoToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(ActivityRegister.this, ActivityLogin.class);
            startActivity(intent);
            finish();
        });
    }

    private void registerUser() {
        String fullname = edtFullname.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();

        // Kiểm tra rỗng
        if (TextUtils.isEmpty(fullname) || TextUtils.isEmpty(email)
                || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra email
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra mật khẩu
        if (password.length() < 6) {
            Toast.makeText(this, "Mật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra xác nhận mật khẩu
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Mật khẩu xác nhận không trùng khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        // 👉 Giả lập đăng ký thành công
        Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();

        // Sau khi đăng ký xong, chuyển sang màn Login
        Intent intent = new Intent(ActivityRegister.this, ActivityLogin.class);
        startActivity(intent);
        finish();
    }
}
