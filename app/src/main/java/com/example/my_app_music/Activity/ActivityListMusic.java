package com.example.my_app_music.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_app_music.Adapter.ActivityListMusic_Adapter;
import com.example.my_app_music.R;
import com.example.my_app_music.Utils_Api.Api.Songs.ApiClient_Music;
import com.example.my_app_music.Utils_Api.Api.Songs.ApiService_Music;
import com.example.my_app_music.Utils_Api.Api.Songs.Constants_Music;
import com.example.my_app_music.Utils_Api.model.Song;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityListMusic extends AppCompatActivity {

    private RecyclerView rv;
    private ActivityListMusic_Adapter adapter;
    private TextView tvTitle;
    private ImageView btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupLayout();
        initViews();
        setupRecycler();
        setupEvents();

        String genre = getGenreFromIntent();
        bindGenreTitle(genre);
        loadByGenre(genre);
    }

    // ========== Nhóm hàm khởi tạo UI / layout ==========
    private void setupLayout() {
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_music);
    }

    private void initViews() {
        btnBack = findViewById(R.id.list_music_btn_back);
        tvTitle = findViewById(R.id.list_music_title);
        rv = findViewById(R.id.list_music_recycler);
    }

    // ========== Nhóm hàm xử lý Intent & bind dữ liệu view ==========
    private String getGenreFromIntent() {
        String genre = getIntent().getStringExtra("genre");
        if (genre == null || genre.trim().isEmpty()) {
            genre = "Pop";
        }
        return genre;
    }

    private void bindGenreTitle(String genre) {
        tvTitle.setText("Thể loại: " + genre);
    }

    // ========== Nhóm hàm setup RecyclerView & Adapter ==========
    private void setupRecycler() {
        adapter = new ActivityListMusic_Adapter();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        setupAdapterItemClick();
    }

    private void setupAdapterItemClick() {
        adapter.setOnSongClickListener(song -> openPlayMusicActivity(song));
    }

    // ========== Nhóm hàm điều hướng / Intent ==========
    private void openPlayMusicActivity(Song song) {
        Intent intent = new Intent(ActivityListMusic.this, ActivityPlayMusic.class);
        intent.putExtra("song_id", song.id);
        intent.putExtra("song_title", song.title);
        intent.putExtra("song_artist", song.artist_name);
        intent.putExtra("song_avatar", song.song_avatar_url);
        intent.putExtra("song_url", song.mp3_url);
        startActivity(intent);
    }

    // ========== Nhóm hàm setup sự kiện ==========
    private void setupEvents() {
        btnBack.setOnClickListener(v -> onBackPressed());
    }

    // ========== Nhóm hàm gọi API & xử lý dữ liệu ==========
    private ApiService_Music getApiService() {
        return ApiClient_Music.getClient().create(ApiService_Music.class);
    }

    private void loadByGenre(String genre) {
        ApiService_Music api = getApiService();
        String url = Constants_Music.getSongsByGenre(genre); // dùng nguyên hàm có sẵn

        Call<List<Song>> call = api.getSongsByDynamicUrl(url);
        call.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.setData(response.body());
                    if (response.body().isEmpty()) {
                        Toast.makeText(ActivityListMusic.this, "Không có bài hát cho thể loại này", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ActivityListMusic.this, "Không tải được dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {
                Toast.makeText(ActivityListMusic.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
