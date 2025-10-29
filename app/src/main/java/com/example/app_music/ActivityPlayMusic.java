package com.example.app_music;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class ActivityPlayMusic extends AppCompatActivity {

    private MediaPlayer mediaPlayer;             // Đối tượng phát nhạc
    private SeekBar seekBar;                     // Thanh kéo thời gian
    private TextView tvCurrentTime, tvTotalTime, tvSongName, tvArtist;
    private ImageButton btnPlayPause, btnNext, btnPrev;
    private BottomNavigationView bottomNav;
    private Handler handler = new Handler();

    private boolean isPlaying = false;           // Trạng thái phát nhạc

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        // Khởi tạo giao diện
        initUI();

        // Thiết lập MediaPlayer (phát nhạc)
        setupMedia();

        // Thiết lập SeekBar
        setupSeekBar();

        // Thiết lập nút điều khiển
        setupControls();

        // Thiết lập thanh điều hướng dưới (avatar / lời bài hát)
        setupBottomNav();
    }

    // ======================================
    // 🔹 Ánh xạ view
    // ======================================
    private void initUI() {
        seekBar = findViewById(R.id.pm_seekbar);
        tvCurrentTime = findViewById(R.id.pm_tv_current_time);
        tvTotalTime = findViewById(R.id.pm_tv_total_time);
        tvSongName = findViewById(R.id.pm_tv_song_name);
        tvArtist = findViewById(R.id.pm_tv_artist);
        btnPlayPause = findViewById(R.id.pm_btn_play_pause);
        btnNext = findViewById(R.id.pm_btn_next);
        btnPrev = findViewById(R.id.pm_btn_prev);
        bottomNav = findViewById(R.id.pm_bottom_navigation);
    }

    // ======================================
    // 🔹 Thiết lập MediaPlayer phát nhạc
    // ======================================
    private void setupMedia() {
        try {
            // Tạo media player từ file nhạc trong thư mục res/raw
            mediaPlayer = MediaPlayer.create(this, R.raw.song1);

            // Kiểm tra nếu không tạo được -> báo lỗi
            if (mediaPlayer == null) {
                Toast.makeText(this, "Không thể phát file nhạc (R.raw.song1)", Toast.LENGTH_LONG).show();
                return;
            }

            // Gán thông tin bài hát (tạm thời cố định)
            tvSongName.setText("Tên bài hát mẫu");
            tvArtist.setText("Ca sĩ thể hiện");

            // Lấy tổng thời lượng bài hát và hiển thị
            int duration = mediaPlayer.getDuration();
            tvTotalTime.setText(formatTime(duration));
            seekBar.setMax(duration);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Lỗi khi khởi tạo MediaPlayer", Toast.LENGTH_SHORT).show();
        }
    }

    // ======================================
    // 🔹 Thiết lập nút điều khiển (play / pause / next / prev)
    // ======================================
    private void setupControls() {
        btnPlayPause.setOnClickListener(v -> {
            if (mediaPlayer == null) {
                Toast.makeText(this, "MediaPlayer chưa sẵn sàng", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isPlaying) {
                // Nếu đang phát -> tạm dừng
                mediaPlayer.pause();
                btnPlayPause.setImageResource(R.drawable.ic_play_arrow);
            } else {
                // Nếu đang dừng -> phát
                mediaPlayer.start();
                btnPlayPause.setImageResource(R.drawable.ic_pause);
                updateSeekBar();
            }
            isPlaying = !isPlaying;
        });

        btnNext.setOnClickListener(v -> {
            // Chức năng chuyển bài (có thể mở rộng sau)
            Toast.makeText(this, "Chức năng chuyển bài (chưa hỗ trợ)", Toast.LENGTH_SHORT).show();
        });

        btnPrev.setOnClickListener(v -> {
            // Chức năng quay lại bài (có thể mở rộng sau)
            Toast.makeText(this, "Chức năng quay lại bài (chưa hỗ trợ)", Toast.LENGTH_SHORT).show();
        });
    }

    // ======================================
    // 🔹 Thiết lập SeekBar
    // ======================================
    private void setupSeekBar() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer == null) return;

                if (fromUser) mediaPlayer.seekTo(progress);
                tvCurrentTime.setText(formatTime(mediaPlayer.getCurrentPosition()));
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    // ======================================
    // 🔹 Cập nhật SeekBar theo thời gian thực
    // ======================================
    private void updateSeekBar() {
        if (mediaPlayer == null) return;

        seekBar.setProgress(mediaPlayer.getCurrentPosition());
        tvCurrentTime.setText(formatTime(mediaPlayer.getCurrentPosition()));

        if (mediaPlayer.isPlaying()) {
            handler.postDelayed(this::updateSeekBar, 500);
        }
    }

    // ======================================
    // 🔹 Thiết lập thanh điều hướng dưới (BottomNavigation)
    // ======================================
    private void setupBottomNav() {
        bottomNav.inflateMenu(R.menu.pm_bottom_navigation_menu);

        bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_lyrics) {
                showLyrics();
                return true;
            } else if (item.getItemId() == R.id.nav_avatar) {
                Toast.makeText(this, "Hiển thị avatar / animation đĩa nhạc", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });
    }

    // ======================================
    // 🔹 Hiển thị lời bài hát (đọc file trong raw)
    // ======================================
    private void showLyrics() {
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.lyrics_song1);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder lyrics = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                lyrics.append(line).append("\n");
            }
            reader.close();

            Toast.makeText(this, lyrics.toString(), Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Không tìm thấy file lời bài hát (R.raw.lyrics_song1)", Toast.LENGTH_SHORT).show();
        }
    }

    // ======================================
    // 🔹 Hàm định dạng thời gian (mm:ss)
    // ======================================
    private String formatTime(int millis) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    // ======================================
    // 🔹 Giải phóng tài nguyên khi thoát Activity
    // ======================================
    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }
}
