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

public class ActivityLogin extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private Button btnLogin;
    private TextView tvForgotPassword, tvGoToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Ánh xạ view
        edtEmail = findViewById(R.id.login_edt_email);
        edtPassword = findViewById(R.id.login_edt_password);
        btnLogin = findViewById(R.id.login_btn_login);
        tvForgotPassword = findViewById(R.id.login_tv_forgot_password);
        tvGoToRegister = findViewById(R.id.login_tv_go_to_register);

        // 👉 Xử lý sự kiện đăng nhập
        btnLogin.setOnClickListener(v -> loginUser());

        // 👉 Chuyển sang màn hình Đăng ký
        tvGoToRegister.setOnClickListener(v -> {
            Intent intent = new Intent(ActivityLogin.this, ActivityRegister.class);
            startActivity(intent);
        });

        // 👉 Quên mật khẩu (tạm thời hiển thị thông báo)
        tvForgotPassword.setOnClickListener(v ->
                Toast.makeText(this, "Tính năng đang phát triển", Toast.LENGTH_SHORT).show());
    }

    private void loginUser() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        // Kiểm tra rỗng
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra định dạng email
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra độ dài mật khẩu
        if (password.length() < 6) {
            Toast.makeText(this, "Mật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
            return;
        }

        // 👉 Xử lý đăng nhập (giả lập)
        Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

        // Chuyển đến ActivityHome (sau này bạn có thể thêm)
        // Intent intent = new Intent(ActivityLogin.this, ActivityHome.class);
        // startActivity(intent);
        // finish();
    }
}
