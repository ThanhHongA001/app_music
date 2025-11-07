package com.example.my_app_music.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.my_app_music.Adapter.FragmentPlayMusicPagerAdapter;
import com.example.my_app_music.Fragment.FragmentPlayMusicAvatar;
import com.example.my_app_music.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ActivityPlayMusic extends AppCompatActivity {

    private ImageButton btnBack, btnPlayPause, btnNext, btnPrevious;
    private TextView tvSongName, tvArtist, tvCurrentTime, tvTotalTime;
    private SeekBar seekBar;
    private BottomNavigationView bottomNav;
    private ViewPager2 viewPager;

    private FragmentPlayMusicAvatar fragmentAvatar;
    private FragmentPlayMusicPagerAdapter pagerAdapter;

    private boolean isPlaying = false;
    private Handler handler = new Handler();
    private int currentPosition = 0;
    private int totalDuration = 212; // 3:32 = 212s

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        initViews();
        setupViewPager();
        setupListeners();
        setupDemoSong();
    }

    private void initViews() {
        btnBack = findViewById(R.id.playmusic_btn_back);
        btnPlayPause = findViewById(R.id.playmusic_btn_playpause);
        btnNext = findViewById(R.id.playmusic_btn_next);
        btnPrevious = findViewById(R.id.playmusic_btn_previous);
        tvSongName = findViewById(R.id.playmusic_tv_songname);
        tvArtist = findViewById(R.id.playmusic_tv_artist);
        tvCurrentTime = findViewById(R.id.playmusic_tv_currenttime);
        tvTotalTime = findViewById(R.id.playmusic_tv_totaltime);
        seekBar = findViewById(R.id.playmusic_seekbar_progress);
        bottomNav = findViewById(R.id.playmusic_bottomnav_effects);
        viewPager = findViewById(R.id.playmusic_viewpager);
    }

    private void setupViewPager() {
        pagerAdapter = new FragmentPlayMusicPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        // Lấy fragment Avatar để điều khiển xoay
        fragmentAvatar = (FragmentPlayMusicAvatar) pagerAdapter.createFragment(0);

        // Khi vuốt sang trang khác, cập nhật bottom nav
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 0)
                    bottomNav.setSelectedItemId(R.id.nav_avatar);
                else
                    bottomNav.setSelectedItemId(R.id.nav_lyrics);
            }
        });

        // Khi chọn item trong bottom nav → đổi trang
        bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_avatar) {
                viewPager.setCurrentItem(0, true);
                return true;
            } else if (item.getItemId() == R.id.nav_lyrics) {
                viewPager.setCurrentItem(1, true);
                return true;
            }
            return false;
        });
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> onBackPressed()); // quay lại màn trước đó

        btnPlayPause.setOnClickListener(v -> {
            isPlaying = !isPlaying;
            if (isPlaying) {
                btnPlayPause.setImageResource(R.drawable.ic_pause);
                Toast.makeText(this, "Đang phát nhạc 🎵", Toast.LENGTH_SHORT).show();
                startMusic();
            } else {
                btnPlayPause.setImageResource(R.drawable.ic_play_arrow);
                Toast.makeText(this, "Tạm dừng ⏸️", Toast.LENGTH_SHORT).show();
                pauseMusic();
            }
        });

        btnNext.setOnClickListener(v ->
                Toast.makeText(this, "Next bài 🎶", Toast.LENGTH_SHORT).show());

        btnPrevious.setOnClickListener(v ->
                Toast.makeText(this, "Previous bài 🔁", Toast.LENGTH_SHORT).show());

        seekBar.setMax(totalDuration);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    currentPosition = progress;
                    tvCurrentTime.setText(formatTime(progress));
                }
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void setupDemoSong() {
        tvSongName.setText("Faded");
        tvArtist.setText("Alan Walker");
        tvCurrentTime.setText("00:00");
        tvTotalTime.setText(formatTime(totalDuration));
    }

    private void startMusic() {
        handler.postDelayed(updateSeekBar, 1000);
        if (fragmentAvatar != null) fragmentAvatar.startRotate();
    }

    private void pauseMusic() {
        handler.removeCallbacks(updateSeekBar);
        if (fragmentAvatar != null) fragmentAvatar.pauseRotate();
    }

    private final Runnable updateSeekBar = new Runnable() {
        @Override
        public void run() {
            if (isPlaying) {
                currentPosition++;
                if (currentPosition <= totalDuration) {
                    seekBar.setProgress(currentPosition);
                    tvCurrentTime.setText(formatTime(currentPosition));
                    handler.postDelayed(this, 1000);
                } else {
                    // Hết bài
                    isPlaying = false;
                    currentPosition = 0;
                    btnPlayPause.setImageResource(R.drawable.ic_play_arrow);
                    seekBar.setProgress(0);
                    tvCurrentTime.setText("00:00");
                    pauseMusic();
                }
            }
        }
    };

    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d", minutes, secs);
    }
}
