package com.example.my_app_music.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.my_app_music.Activity.ActivityListMusic;
import com.example.my_app_music.Adapter.FragmentHomeListMusic_V2_Adapter;
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
    private String genre = "Pop"; // Giá trị mặc định

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

        // Set tên thể loại trên thanh tiêu đề
        binding.homeListMusicV2Tvname.setText(genre);

        setupRecyclerView();
        loadSongsByGenre(genre);

        // Listener chuyển sang ActivityListMusic
        View.OnClickListener goToListScreen = v -> {
            Intent intent = new Intent(requireContext(), ActivityListMusic.class);
            intent.putExtra("genre", genre);
            startActivity(intent);
        };

        // Click nút mũi tên
        binding.homeListMusicV2TvnameBtn.setOnClickListener(goToListScreen);
        // (Nếu muốn) Click luôn vào chữ thể loại cũng nhảy:
        // binding.homeListMusicV2Tvname.setOnClickListener(goToListScreen);

        // Click từng bài trong list → sau này có thể mở ActivityPlayMusic
        adapter.setOnSongClickListener(song -> {
            // Ở Home chỉ demo toast, hoặc bạn có thể mở Player luôn
            Toast.makeText(getContext(), "Chọn: " + song.title, Toast.LENGTH_SHORT).show();
        });
    }

    private void setupRecyclerView() {
        adapter = new FragmentHomeListMusic_V2_Adapter();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                // Nếu fragment nằm trong ScrollView ở Home thì để false
                return false;
            }
        };

        binding.homeListMusicV2RcvList.setLayoutManager(layoutManager);
        binding.homeListMusicV2RcvList.setAdapter(adapter);
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
