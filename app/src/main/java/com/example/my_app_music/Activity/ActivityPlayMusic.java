package com.example.my_app_music.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.my_app_music.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityPlayMusic extends AppCompatActivity {

    private ImageButton btnBack, btnPrevious, btnPlayPause, btnNext;
    private TextView tvHeaderTitle, tvSongName, tvArtist, tvCurrentTime, tvTotalTime;
    private SeekBar seekBar;

    // Nếu bạn có ImageView hiển thị avatar bài hát trong ViewPager riêng thì thay thế lại
    private CircleImageView imgSongAvatar;

    private boolean isPlaying = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_play_music);

        initViews();
        receiveDataFromIntent();
        setupEvents();
    }

    private void initViews() {
        btnBack = findViewById(R.id.playmusic_btn_back);
        tvHeaderTitle = findViewById(R.id.playmusic_tv_title);

        tvSongName = findViewById(R.id.playmusic_tv_songname);
        tvArtist = findViewById(R.id.playmusic_tv_artist);

        tvCurrentTime = findViewById(R.id.playmusic_tv_currenttime);
        tvTotalTime = findViewById(R.id.playmusic_tv_totaltime);
        seekBar = findViewById(R.id.playmusic_seekbar_progress);

        btnPrevious = findViewById(R.id.playmusic_btn_previous);
        btnPlayPause = findViewById(R.id.playmusic_btn_playpause);
        btnNext = findViewById(R.id.playmusic_btn_next);

        // Nếu trong layout activity_play_music có 1 ImageView dành cho avatar bài hát,
        // giả sử id là playmusic_img_song, bạn sửa lại cho đúng:
        // imgSongAvatar = findViewById(R.id.playmusic_img_song);
        // Ở XML bạn đang dùng ViewPager2 để hiển thị ảnh/lời nên đoạn này có thể chỉnh lại sau.
    }

    private void receiveDataFromIntent() {
        Intent intent = getIntent();
        if (intent == null) return;

        int id = intent.getIntExtra("song_id", -1);
        String title = intent.getStringExtra("song_title");
        String artist = intent.getStringExtra("song_artist");
        String avatarUrl = intent.getStringExtra("song_avatar");
        String mp3Url = intent.getStringExtra("song_url");

        // Header
        tvHeaderTitle.setText("Đang phát nhạc");
        // Thông tin chính
        if (title != null) tvSongName.setText(title);
        if (artist != null) tvArtist.setText(artist);

        // Demo, nếu có avatar và ImageView tương ứng
        /*
        if (imgSongAvatar != null && avatarUrl != null) {
            Glide.with(this)
                    .load(avatarUrl)
                    .placeholder(R.drawable.bg_album_rounded)
                    .into(imgSongAvatar);
        }
        */

        // TODO: bạn có thể lưu mp3Url vào biến và dùng MediaPlayer/ExoPlayer để play
        // Hiện tại chỉ set tổng thời gian mẫu
        tvCurrentTime.setText("00:00");
        tvTotalTime.setText("00:00");
    }

    private void setupEvents() {
        btnBack.setOnClickListener(v -> onBackPressed());

        btnPlayPause.setOnClickListener(v -> {
            // Tạm thời chỉ toggle icon, chưa play thật
            isPlaying = !isPlaying;
            if (isPlaying) {
                btnPlayPause.setImageResource(R.drawable.ic_pause); // bạn cần icon pause
            } else {
                btnPlayPause.setImageResource(R.drawable.ic_play_arrow);
            }
        });

        btnPrevious.setOnClickListener(v -> {
            // TODO: xử lý chuyển bài trước đó
        });

        btnNext.setOnClickListener(v -> {
            // TODO: xử lý chuyển bài tiếp theo
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO: nếu fromUser == true thì seek trong MediaPlayer
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }
}
