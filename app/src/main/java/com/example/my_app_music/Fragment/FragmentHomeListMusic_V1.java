package com.example.my_app_music.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.my_app_music.Adapter.FragmentHomeListMusic_V1_Adapter;
import com.example.my_app_music.R;
import com.example.my_app_music.Utils_Api.Api.ApiClient;
import com.example.my_app_music.Utils_Api.Api.ApiService;
import com.example.my_app_music.Utils_Api.Api.Constants;
import com.example.my_app_music.Utils_Api.model.Song;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentHomeListMusic_V1 extends Fragment {

    private ViewPager2 viewPager;
    private CircleIndicator3 indicator;
    private FragmentHomeListMusic_V1_Adapter adapter;
    private final List<Song> songs = new ArrayList<>();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private int currentPage = 0;
    private final long delay = 4000; // tự động chuyển slide 4s

    private final Runnable autoSlideRunnable = new Runnable() {
        @Override
        public void run() {
            if (songs.isEmpty()) return;
            currentPage = (currentPage + 1) % songs.size();
            viewPager.setCurrentItem(currentPage, true);
            handler.postDelayed(this, delay);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_list_music_v1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = view.findViewById(R.id.home_listmusic_v1_viewpager);
        indicator = view.findViewById(R.id.home_listmusic_v1_circleindicator3);

        adapter = new FragmentHomeListMusic_V1_Adapter(requireContext(), songs);
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);

        loadTop10NewSongs();
    }

    private void loadTop10NewSongs() {
        ApiClient.getClient().create(ApiService.class)
                .getSongsByDynamicUrl(Constants.GET_TOP_10_NEW_RELEASE)
                .enqueue(new Callback<List<Song>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Song>> call, @NonNull Response<List<Song>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            songs.clear();
                            songs.addAll(response.body());
                            adapter.notifyDataSetChanged();

                            if (!songs.isEmpty()) {
                                currentPage = 0;
                                handler.postDelayed(autoSlideRunnable, delay);
                            }
                        } else {
                            Toast.makeText(requireContext(), "Không thể tải dữ liệu bài hát mới", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Song>> call, @NonNull Throwable t) {
                        Toast.makeText(requireContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(autoSlideRunnable);
    }
}
