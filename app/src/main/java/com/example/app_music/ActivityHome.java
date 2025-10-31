package com.example.app_music;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.app_music.Adapter.ActivityHomePager_Adapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ActivityHome extends AppCompatActivity {

    private ViewPager2 viewPager;
    private BottomNavigationView bottomNavigationView;

    // Top bar
    private LinearLayout layoutUserInfo;
    private View btnLogin;
    private TextView tvUsername;

    // Player bar
    private LinearLayout layoutPlayerBar;
    private ImageView imgSongThumb;
    private TextView tvSongTitle, tvSongArtist;
    private ImageButton btnPlayPause;

    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        initUI();
        setupViewPager();
        setupBottomNavigation();
        setupPlayerBar();
        setupLoginUI();
    }

    private void initUI() {
        // ViewPager + BottomNav
        viewPager = findViewById(R.id.home_view_pager);
        bottomNavigationView = findViewById(R.id.home_bottom_nav);

        // Top bar
        layoutUserInfo = findViewById(R.id.home_layout_user_info);
        btnLogin = findViewById(R.id.home_btn_login);
        tvUsername = findViewById(R.id.home_tv_username);

        // Player bar
        layoutPlayerBar = findViewById(R.id.home_layout_player_bar);
        imgSongThumb = findViewById(R.id.home_img_song_thumb);
        tvSongTitle = findViewById(R.id.home_tv_song_title);
        tvSongArtist = findViewById(R.id.home_tv_song_artist);
        btnPlayPause = findViewById(R.id.home_btn_play_pause);
    }

    private void setupViewPager() {
        ActivityHomePager_Adapter adapter = new ActivityHomePager_Adapter(this);
        viewPager.setAdapter(adapter);

        // Khi trượt ViewPager → đổi trạng thái bottom navigation
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.nav_home);
                        break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.nav_search);
                        break;
                }
            }
        });
    }

    private void setupBottomNavigation() {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                viewPager.setCurrentItem(0, true);
                return true;
            } else if (item.getItemId() == R.id.nav_search) {
                viewPager.setCurrentItem(1, true);
                return true;
            }
            return false;
        });
    }

    private void setupPlayerBar() {
        // Giả lập dữ liệu bài hát
        layoutPlayerBar.setVisibility(View.VISIBLE);
        tvSongTitle.setText("Hoa Hải Đường");
        tvSongArtist.setText("Jack - J97");

        btnPlayPause.setOnClickListener(v -> {
            isPlaying = !isPlaying;
            btnPlayPause.setImageResource(isPlaying ? R.drawable.ic_pause : R.drawable.ic_play_arrow);
        });
    }

    private void setupLoginUI() {
        btnLogin.setOnClickListener(v -> {
            // Giả lập: sau khi đăng nhập thành công
            btnLogin.setVisibility(View.GONE);
            layoutUserInfo.setVisibility(View.VISIBLE);
            tvUsername.setText("Người dùng A");
        });
    }
}
