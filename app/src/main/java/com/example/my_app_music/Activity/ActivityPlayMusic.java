package com.example.my_app_music.Activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.my_app_music.R;

public class ActivityPlayMusic extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;

    private TextView tvSongName, tvArtist;
    private ImageButton btnPlayPause, btnBack;
    private SeekBar seekBar;
    private ImageView imgArtwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        tvSongName = findViewById(R.id.playmusic_tv_songname);
        tvArtist = findViewById(R.id.playmusic_tv_artist);
        btnPlayPause = findViewById(R.id.playmusic_btn_playpause);
        btnBack = findViewById(R.id.playmusic_btn_back);
        seekBar = findViewById(R.id.playmusic_seekbar_progress);
        imgArtwork = findViewById(R.id.imgAvatarMusic);

        // Nhận dữ liệu từ Intent
        String songName = getIntent().getStringExtra("song_name");
        String artistName = getIntent().getStringExtra("artist_name");
        String imageUrl = getIntent().getStringExtra("image_url");
        String mp3Url = getIntent().getStringExtra("mp3_url");

        tvSongName.setText(songName);
        tvArtist.setText(artistName);
        Glide.with(this).load(imageUrl).placeholder(R.drawable.bg_album_rounded ).into(imgArtwork);

        btnBack.setOnClickListener(v -> finish());

        // Khởi tạo MediaPlayer
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(mp3Url);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(mp -> {
                seekBar.setMax(mp.getDuration());
                mp.start();
                isPlaying = true;
                btnPlayPause.setImageResource(R.drawable.ic_pause);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnPlayPause.setOnClickListener(v -> {
            if (isPlaying) {
                mediaPlayer.pause();
                btnPlayPause.setImageResource(R.drawable.ic_play_arrow);
            } else {
                mediaPlayer.start();
                btnPlayPause.setImageResource(R.drawable.ic_pause);
            }
            isPlaying = !isPlaying;
        });

        // Cập nhật SeekBar
        new Thread(() -> {
            while (mediaPlayer != null) {
                try {
                    if (mediaPlayer.isPlaying()) {
                        runOnUiThread(() -> seekBar.setProgress(mediaPlayer.getCurrentPosition()));
                    }
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
