package com.example.my_app_music.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.my_app_music.Adapter.FragmentHomeListMusic_V2_Adapter;
import com.example.my_app_music.R;
import com.example.my_app_music.Utils_Api.Api.ApiClient;
import com.example.my_app_music.Utils_Api.Api.ApiService;
import com.example.my_app_music.Utils_Api.Api.Constants;
import com.example.my_app_music.Utils_Api.model.Song;
import com.example.my_app_music.databinding.FragmentHomeListMusicV2Binding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentHomeListMusic_V2 extends Fragment {

    private FragmentHomeListMusicV2Binding binding;
    private FragmentHomeListMusic_V2_Adapter adapter;
    private String genre = "Pop"; // giá trị mặc định

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeListMusicV2Binding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Nhận genre từ FragmentHome
        if (getArguments() != null) {
            genre = getArguments().getString("genre", "Pop");
        }

        binding.homeListmusicV2TvTitle.setText(genre);
        setupRecyclerView();
        loadSongsByGenre(genre);

        // Xử lý nút ">>>"
        binding.homeListmusicV2BtnMore.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Xem thêm thể loại: " + genre, Toast.LENGTH_SHORT).show();
            // TODO: có thể replace sang FragmentGenreDetail
        });
    }

    private void setupRecyclerView() {
        adapter = new FragmentHomeListMusic_V2_Adapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        binding.homeListmusicV2Recyclerview.setLayoutManager(layoutManager);
        binding.homeListmusicV2Recyclerview.setAdapter(adapter);
    }

    private void loadSongsByGenre(String genre) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        String url = Constants.getSongsByGenre(genre);

        Call<List<Song>> call = apiService.getSongsByDynamicUrl(url);
        call.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(@NonNull Call<List<Song>> call, @NonNull Response<List<Song>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.setSongs(response.body());
                } else {
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Song>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
