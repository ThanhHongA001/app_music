// ActivityMyAccout.java
package com.example.my_app_music.Activity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.my_app_music.Adapter.User.ActivityMyAccout_Adapter;
import com.example.my_app_music.R;
import com.example.my_app_music.Utils_Api.Api.Users.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ActivityMyAccout extends AppCompatActivity {

    // View
    private ImageButton btnBack;
    private ImageView imgAvatar;
    private TextView tvFullName, tvEmail;
    private BottomNavigationView bottomNavigationView;
    private ViewPager2 viewPager2;

    // Session
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_accout);

        initViews();
        initSessionAndHeader();
        setupViewPager();
        setupBottomNav();
    }

    // Ánh xạ view
    private void initViews() {
        btnBack = findViewById(R.id.my_accout_btn_back);
        imgAvatar = findViewById(R.id.my_accout_img_avatar);
        tvFullName = findViewById(R.id.my_accout_txt_fullname);
        tvEmail = findViewById(R.id.my_accout_txt_email);
        bottomNavigationView = findViewById(R.id.my_accout_bnv);
        viewPager2 = findViewById(R.id.my_accout_viewpager);

        // Back về màn trước
        btnBack.setOnClickListener(v -> onBackPressed());
    }

    // Lấy thông tin user từ SessionManager và hiển thị lên header
    private void initSessionAndHeader() {
        sessionManager = new SessionManager(this);

        String fullName = "Name Account";
        String email = "email@example.com";

        if (sessionManager != null && sessionManager.isLoggedIn()) {
            String sessionName = sessionManager.getFullName();
            String sessionEmail = sessionManager.getEmail();

            if (sessionName != null && !sessionName.trim().isEmpty()) {
                fullName = sessionName;
            }
            if (sessionEmail != null && !sessionEmail.trim().isEmpty()) {
                email = sessionEmail;
            }
        }

        tvFullName.setText(fullName);
        tvEmail.setText(email);

        // TODO: load avatar nếu bạn lưu URL/avatar trong SessionManager sau này
    }

    // Setup ViewPager2 + Adapter cho 2 tab: Yêu thích / Đã nghe gần đây
    private void setupViewPager() {
        ActivityMyAccout_Adapter adapter = new ActivityMyAccout_Adapter(this);
        viewPager2.setAdapter(adapter);

        // Lướt ViewPager thì đổi tab tương ứng
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 0) {
                    bottomNavigationView.setSelectedItemId(R.id.account_list_music_favourite);
                } else {
                    bottomNavigationView.setSelectedItemId(R.id.accout_list_music_recently);
                }
            }
        });
    }

    // Setup BottomNavigationView để chuyển giữa 2 fragment
    private void setupBottomNav() {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.account_list_music_favourite) {
                viewPager2.setCurrentItem(0, true);
                return true;
            } else if (itemId == R.id.accout_list_music_recently) {
                viewPager2.setCurrentItem(1, true);
                return true;
            }
            return false;
        });
    }
}
