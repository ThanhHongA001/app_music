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
import com.example.my_app_music.Utils_Api.Api.ApiClient;
import com.example.my_app_music.Utils_Api.Api.ApiService;
import com.example.my_app_music.Utils_Api.model.Song;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentHomeListMusic_V3 extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private FragmentHomeListMusic_V3_Adapter adapter;
    private List<Song> albumList = new ArrayList<>();

    public FragmentHomeListMusic_V3() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_list_music_v3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.home_listmusic_v3_recyclerview);
        progressBar = view.findViewById(R.id.home_listmusic_v3_progressbar);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new FragmentHomeListMusic_V3_Adapter(getContext(), albumList);
        recyclerView.setAdapter(adapter);

        fetchAlbums();
    }

    private void fetchAlbums() {
        progressBar.setVisibility(View.VISIBLE);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Song>> call = apiService.getAllSongs();

        call.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(@NonNull Call<List<Song>> call, @NonNull Response<List<Song>> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    List<Song> allSongs = response.body();

                    // 🔹 Nhóm theo album_name (mỗi album chỉ lấy 1 bản ghi đại diện)
                    Map<String, Song> albumMap = new LinkedHashMap<>();
                    for (Song song : allSongs) {
                        if (!albumMap.containsKey(song.album_name)) {
                            albumMap.put(song.album_name, song);
                        }
                    }

                    albumList.clear();
                    albumList.addAll(albumMap.values());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Không lấy được dữ liệu album!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Song>> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Lỗi API: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
