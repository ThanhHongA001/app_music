package com.example.my_app_music.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.my_app_music.R;
import com.example.my_app_music.Utils_Api.Api.ApiClient_Users;
import com.example.my_app_music.Utils_Api.Api.ApiService_Users;
import com.example.my_app_music.Utils_Api.model.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityRegister extends AppCompatActivity {

    private EditText edtFullName, edtEmail, edtPassword, edtConfirmPassword;
    private Button btnRegister;
    private TextView txtLoginNow;
    private LinearLayout layoutLoginRedirect;

    private ApiService_Users apiServiceUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        initView();
        initApi();
        initEvents();
    }

    private void initView() {
        edtFullName = findViewById(R.id.register_edit_fullname);
        edtEmail = findViewById(R.id.register_edit_email);
        edtPassword = findViewById(R.id.register_edit_password);
        edtConfirmPassword = findViewById(R.id.register_edit_confirm_password);
        btnRegister = findViewById(R.id.register_btn_register);
        txtLoginNow = findViewById(R.id.register_txt_login_now);
        layoutLoginRedirect = findViewById(R.id.register_layout_login_redirect);
    }

    private void initApi() {
        apiServiceUsers = ApiClient_Users.getClient().create(ApiService_Users.class);
    }

    private void initEvents() {
        // Click Đăng ký
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegisterClick();
            }
        });

        // Click "Đăng nhập ngay" hoặc cả layout redirect
        View.OnClickListener goLoginListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActivityRegister.this, ActivityLogin.class);
                startActivity(i);
                finish();
            }
        };
        txtLoginNow.setOnClickListener(goLoginListener);
        layoutLoginRedirect.setOnClickListener(goLoginListener);
    }

    private void onRegisterClick() {
        String fullName = edtFullName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();

        // Validate
        if (TextUtils.isEmpty(fullName)) {
            edtFullName.setError("Vui lòng nhập họ tên");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            edtEmail.setError("Vui lòng nhập email");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("Email không hợp lệ");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            edtPassword.setError("Vui lòng nhập mật khẩu");
            return;
        }

        if (password.length() < 6) {
            edtPassword.setError("Mật khẩu phải từ 6 ký tự");
            return;
        }

        if (!password.equals(confirmPassword)) {
            edtConfirmPassword.setError("Mật khẩu xác nhận không khớp");
            return;
        }

        // Tạo đối tượng Users để gửi lên API
        Users user = new Users(fullName, email, password);

        btnRegister.setEnabled(false);

        Call<List<Users>> call = apiServiceUsers.register(user);
        call.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                btnRegister.setEnabled(true);

                if (response.isSuccessful()) {
                    List<Users> createdUsers = response.body();
                    if (createdUsers != null && !createdUsers.isEmpty()) {
                        Toast.makeText(ActivityRegister.this,
                                "Đăng ký thành công!", Toast.LENGTH_SHORT).show();

                        // Sau khi đăng ký xong -> chuyển sang màn đăng nhập
                        Intent i = new Intent(ActivityRegister.this, ActivityLogin.class);
                        // Có thể truyền email sang sẵn nếu muốn
                        i.putExtra("email", email);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(ActivityRegister.this,
                                "Đăng ký thất bại (không nhận được dữ liệu)", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ActivityRegister.this,
                            "Đăng ký thất bại: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {
                btnRegister.setEnabled(true);
                Toast.makeText(ActivityRegister.this,
                        "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
