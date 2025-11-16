package com.example.my_app_music.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_app_music.Adapter.FragmentHomeListMusic_V3_Adapter;
import com.example.my_app_music.R;
import com.example.my_app_music.Utils_Api.Api.ApiClient_Music;
import com.example.my_app_music.Utils_Api.Api.ApiService_Music;
import com.example.my_app_music.Utils_Api.model.Song;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentHomeListMusic_V3 extends Fragment {

    // ========== Fields ==========
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private FragmentHomeListMusic_V3_Adapter adapter;
    private final List<Song> albumList = new ArrayList<>();

    public FragmentHomeListMusic_V3() {}

    // ========== Vòng đời Fragment ==========
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_list_music_v3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        setupRecyclerView();
        fetchAlbums();
    }

    // ========== Nhóm hàm khởi tạo UI ==========
    private void initViews(@NonNull View view) {
        recyclerView = view.findViewById(R.id.home_listmusic_v3_recyclerview);
        progressBar = view.findViewById(R.id.home_listmusic_v3_progressbar);
    }

    private void setupRecyclerView() {
        if (getContext() == null) return;

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new FragmentHomeListMusic_V3_Adapter(getContext(), albumList);
        recyclerView.setAdapter(adapter);
    }

    // ========== Nhóm hàm API ==========
    private ApiService_Music getApiService() {
        return ApiClient_Music.getClient().create(ApiService_Music.class);
    }

    private void fetchAlbums() {
        showLoading(true);

        ApiService_Music apiServiceMusic = getApiService();
        Call<List<Song>> call = apiServiceMusic.getAllSongs();

        call.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(@NonNull Call<List<Song>> call, @NonNull Response<List<Song>> response) {
                showLoading(false);
                handleAlbumResponse(response);
            }

            @Override
            public void onFailure(@NonNull Call<List<Song>> call, @NonNull Throwable t) {
                showLoading(false);
                showToast("Lỗi API: " + t.getMessage());
            }
        });
    }

    // ========== Nhóm hàm xử lý Response & dữ liệu ==========
    private void handleAlbumResponse(@NonNull Response<List<Song>> response) {
        if (response.isSuccessful() && response.body() != null) {
            processAlbumList(response.body());
        } else {
            showToast("Không lấy được dữ liệu album!");
        }
    }

    private void processAlbumList(@NonNull List<Song> allSongs) {
        // Nhóm theo album_name (mỗi album chỉ lấy 1 bản ghi đại diện)
        Map<String, Song> albumMap = new LinkedHashMap<>();
        for (Song song : allSongs) {
            if (song == null || song.album_name == null) continue;
            if (!albumMap.containsKey(song.album_name)) {
                albumMap.put(song.album_name, song);
            }
        }

        albumList.clear();
        albumList.addAll(albumMap.values());
        adapter.notifyDataSetChanged();
    }

    // ========== Nhóm hàm tiện ích UI ==========
    private void showLoading(boolean isLoading) {
        if (progressBar == null) return;
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    private void showToast(String message) {
        if (getContext() != null) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }
}
