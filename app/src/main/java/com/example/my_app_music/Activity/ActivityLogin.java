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

import com.example.my_app_music.Utils_Api.Api.SessionManager;
import com.example.my_app_music.Utils_Api.model.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityLogin extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private Button btnLogin;
    private TextView txtRegisterNow, txtForgotPassword;
    private LinearLayout layoutRegisterRedirect;

    private ApiService_Users apiServiceUsers;
    private SessionManager sessionManager; // NEW

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        initView();         // cũ
        initApi();          // cũ
        initSession();      // NEW: tách riêng
        initEvents();       // cũ + dùng thêm session

        // Nếu từ màn đăng ký quay sang, có thể nhận email
        String emailFromRegister = getIntent().getStringExtra("email");
        if (emailFromRegister != null) {
            edtEmail.setText(emailFromRegister);
        }
    }

    private void initView() {
        edtEmail = findViewById(R.id.login_edit_email);
        edtPassword = findViewById(R.id.login_edit_password);
        btnLogin = findViewById(R.id.login_btn_login);
        txtRegisterNow = findViewById(R.id.login_txt_register_now);
        txtForgotPassword = findViewById(R.id.login_txt_forgot_password);
        layoutRegisterRedirect = findViewById(R.id.login_layout_register_redirect);
    }

    private void initApi() {
        apiServiceUsers = ApiClient_Users.getClient().create(ApiService_Users.class);
    }

    // NEW: khởi tạo SessionManager tách riêng
    private void initSession() {
        sessionManager = new SessionManager(this);
    }

    private void initEvents() {
        // Click Đăng nhập
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginClick();
            }
        });

        // Click "Đăng ký ngay" hoặc cả layout redirect -> sang ActivityRegister
        View.OnClickListener goRegisterListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActivityLogin.this, ActivityRegister.class);
                startActivity(i);
                finish();
            }
        };
        txtRegisterNow.setOnClickListener(goRegisterListener);
        layoutRegisterRedirect.setOnClickListener(goRegisterListener);

        // Tạm thời: click "Quên mật khẩu?" chỉ thông báo (chưa triển khai)
        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityLogin.this,
                        "Tính năng quên mật khẩu chưa được triển khai.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onLoginClick() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        // Validate
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

        btnLogin.setEnabled(false);

        // Supabase REST yêu cầu filter dạng email=eq.xxxx
        String emailFilter = "eq." + email;
        String passwordFilter = "eq." + password;

        // Gọi API với select="*"
        Call<List<Users>> call = apiServiceUsers.login("*", emailFilter, passwordFilter);
        call.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                btnLogin.setEnabled(true);

                if (response.isSuccessful()) {
                    List<Users> usersList = response.body();
                    if (usersList != null && !usersList.isEmpty()) {
                        Users user = usersList.get(0);

                        // NEW: gom vào hàm riêng
                        handleLoginSuccess(user);

                    } else {
                        Toast.makeText(ActivityLogin.this,
                                "Sai email hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ActivityLogin.this,
                            "Đăng nhập thất bại: " + response.code(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {
                btnLogin.setEnabled(true);
                Toast.makeText(ActivityLogin.this,
                        "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // NEW: xử lý khi đăng nhập thành công (lưu session + chuyển về Home)
    private void handleLoginSuccess(Users user) {
        if (sessionManager != null) {
            sessionManager.saveUser(user);
        }

        Toast.makeText(ActivityLogin.this,
                "Đăng nhập thành công! Xin chào " + user.getFullName(),
                Toast.LENGTH_SHORT).show();

        // Chuyển về màn home chính
        Intent i = new Intent(ActivityLogin.this, Activity_Home.class);
        // Clear stack để tránh back quay lại màn login
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }
}
