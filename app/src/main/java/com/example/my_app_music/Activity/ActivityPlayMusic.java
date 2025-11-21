package com.example.my_app_music.Activity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.my_app_music.Fragment.PlayMusic.FragmentPlayMusicAvatar;
import com.example.my_app_music.Fragment.PlayMusic.FragmentPlayMusicLyrics;
import com.example.my_app_music.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ActivityPlayMusic extends AppCompatActivity {

    private ImageButton btnBack, btnPrevious, btnPlayPause, btnNext;
    private TextView tvHeaderTitle, tvSongName, tvArtist, tvCurrentTime, tvTotalTime;
    private SeekBar seekBar;

    private ViewPager2 viewPager;
    private BottomNavigationView bottomNav;

    // Fragment avatar & lyrics
    private FragmentPlayMusicAvatar avatarFragment;
    private FragmentPlayMusicLyrics lyricsFragment;

    // MediaPlayer
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    private String currentMp3Url;

    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable updateSeekbarRunnable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_play_music);

        initViews();
        setupViewPagerAndBottomNav();
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

        viewPager = findViewById(R.id.playmusic_viewpager);
        bottomNav = findViewById(R.id.playmusic_bottomnav_effects);
    }

    // ==========================
    // ViewPager + BottomNav
    // ==========================
    private void setupViewPagerAndBottomNav() {
        avatarFragment = new FragmentPlayMusicAvatar();
        lyricsFragment = new FragmentPlayMusicLyrics();

        viewPager.setAdapter(new PlayMusicPagerAdapter(this, avatarFragment, lyricsFragment));

        // chọn item bottom nav -> chuyển page
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

        // vuốt ViewPager -> sync bottom nav
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    bottomNav.setSelectedItemId(R.id.nav_avatar);
                } else {
                    bottomNav.setSelectedItemId(R.id.nav_lyrics);
                }
            }
        });
    }

    // Adapter cho ViewPager2
    private static class PlayMusicPagerAdapter extends FragmentStateAdapter {

        private final FragmentPlayMusicAvatar avatarFragment;
        private final FragmentPlayMusicLyrics lyricsFragment;

        public PlayMusicPagerAdapter(@NonNull FragmentActivity fragmentActivity,
                                     FragmentPlayMusicAvatar avatarFragment,
                                     FragmentPlayMusicLyrics lyricsFragment) {
            super(fragmentActivity);
            this.avatarFragment = avatarFragment;
            this.lyricsFragment = lyricsFragment;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 0) return avatarFragment;
            return lyricsFragment;
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }

    // ==========================
    // Nhận data từ Intent
    // ==========================
    private void receiveDataFromIntent() {
        Intent intent = getIntent();
        if (intent == null) return;

        int id = intent.getIntExtra("song_id", -1);
        String title = intent.getStringExtra("song_title");
        String artist = intent.getStringExtra("song_artist");

        // Đọc avatar: ưu tiên key đang dùng, fallback key cũ nếu có
        String avatarUrl = intent.getStringExtra("song_avatar");
        if (avatarUrl == null) {
            avatarUrl = intent.getStringExtra("song_avatar_url");
        }

        // Đọc mp3 url: ưu tiên "song_url", fallback "song_mp3_url"
        String mp3Url = intent.getStringExtra("song_url");
        if (mp3Url == null) {
            mp3Url = intent.getStringExtra("song_mp3_url");
        }

        tvHeaderTitle.setText("Đang phát nhạc");
        if (title != null) tvSongName.setText(title);
        if (artist != null) tvArtist.setText(artist);

        // TODO: có thể truyền avatarUrl sang avatarFragment bằng Bundle hoặc ViewModel

        currentMp3Url = mp3Url;
        tvCurrentTime.setText("00:00");
        tvTotalTime.setText("00:00");

        if (currentMp3Url != null && !currentMp3Url.isEmpty()) {
            initMediaPlayer(currentMp3Url);
        }
    }

    // ==========================
    // MediaPlayer
    // ==========================
    private void initMediaPlayer(String url) {
        releaseMediaPlayer(); // clear nếu đã có

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mediaPlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        mediaPlayer.setOnPreparedListener(mp -> {
            int duration = mp.getDuration();
            seekBar.setMax(duration);
            tvTotalTime.setText(formatTime(duration));
            startMusic();  // auto play khi đã sẵn sàng
        });

        mediaPlayer.setOnCompletionListener(mp -> {
            // Khi phát xong
            isPlaying = false;
            btnPlayPause.setImageResource(R.drawable.ic_play_arrow);
            seekBar.setProgress(0);
            tvCurrentTime.setText("00:00");
            if (avatarFragment != null) avatarFragment.pauseRotate();
            stopUpdateSeekbar();
        });

        mediaPlayer.prepareAsync();

        // Runnable cập nhật SeekBar
        updateSeekbarRunnable = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null && isPlaying) {
                    int current = mediaPlayer.getCurrentPosition();
                    seekBar.setProgress(current);
                    tvCurrentTime.setText(formatTime(current));
                    handler.postDelayed(this, 500);
                }
            }
        };
    }

    private void startMusic() {
        if (mediaPlayer == null) return;

        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
        isPlaying = true;
        btnPlayPause.setImageResource(R.drawable.ic_pause);

        // Avatar quay
        if (avatarFragment != null) {
            avatarFragment.startRotate();
        }

        startUpdateSeekbar();
    }

    private void pauseMusic() {
        if (mediaPlayer == null) return;

        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
        isPlaying = false;
        btnPlayPause.setImageResource(R.drawable.ic_play_arrow);

        if (avatarFragment != null) {
            avatarFragment.pauseRotate();
        }

        stopUpdateSeekbar();
    }

    private void startUpdateSeekbar() {
        if (updateSeekbarRunnable != null) {
            handler.post(updateSeekbarRunnable);
        }
    }

    private void stopUpdateSeekbar() {
        if (updateSeekbarRunnable != null) {
            handler.removeCallbacks(updateSeekbarRunnable);
        }
    }

    private void releaseMediaPlayer() {
        stopUpdateSeekbar();
        if (mediaPlayer != null) {
            try {
                mediaPlayer.stop();
            } catch (IllegalStateException ignored) {}
            mediaPlayer.release();
            mediaPlayer = null;
        }
        isPlaying = false;
    }

    private String formatTime(long millis) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    // ==========================
    // Event nút & SeekBar
    // ==========================
    private void setupEvents() {
        btnBack.setOnClickListener(v -> onBackPressed());

        btnPlayPause.setOnClickListener(v -> {
            if (mediaPlayer == null) return;

            if (isPlaying) {
                pauseMusic();
            } else {
                startMusic();
            }
        });

        btnPrevious.setOnClickListener(v -> {
            // TODO: xử lý chuyển bài trước đó (cần truyền list + index)
        });

        btnNext.setOnClickListener(v -> {
            // TODO: xử lý chuyển bài tiếp theo (cần truyền list + index)
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && mediaPlayer != null) {
                    mediaPlayer.seekTo(progress);
                    tvCurrentTime.setText(formatTime(progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        // tạm dừng khi rời màn
        if (isPlaying) {
            pauseMusic();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
    }
}
