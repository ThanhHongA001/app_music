package com.example.app_music.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.app_music.Adapter.FragmentHomeListMusicV1_Adapter;
import com.example.app_music.Model.FragmentHomeListMusicV1_Model;
import com.example.app_music.R;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class FragmentHomeListMusicV1 extends Fragment {

    private ViewPager2 viewPager;
    private CircleIndicator3 indicator;
    private FragmentHomeListMusicV1_Adapter adapter;
    private Handler handler = new Handler(Looper.getMainLooper());
    private int currentPage = 0;

    private final Runnable slideRunnable = new Runnable() {
        @Override
        public void run() {
            if (adapter != null && adapter.getItemCount() > 0) {
                currentPage = (currentPage + 1) % adapter.getItemCount();
                viewPager.setCurrentItem(currentPage, true);
                handler.postDelayed(this, 5000); // mỗi 5 giây
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_list_music_v1, container, false);
        viewPager = view.findViewById(R.id.viewpager_top_music);
        indicator = view.findViewById(R.id.indicator_top_music);

        setupViewPager();
        return view;
    }

    private void setupViewPager() {
        List<FragmentHomeListMusicV1_Model> list = new ArrayList<>();
        list.add(new FragmentHomeListMusicV1_Model("Top Hits", R.drawable.bg_album_rounded));
        list.add(new FragmentHomeListMusicV1_Model("Pop Rising", R.drawable.bg_album_rounded));
        list.add(new FragmentHomeListMusicV1_Model("New Releases", R.drawable.bg_album_rounded));

        adapter = new FragmentHomeListMusicV1_Adapter(list);
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);

        // Khi người dùng vuốt tay thì reset lại thời gian auto slide
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentPage = position;
                handler.removeCallbacks(slideRunnable);
                handler.postDelayed(slideRunnable, 5000);
            }
        });

        // Bắt đầu auto slide
        handler.postDelayed(slideRunnable, 5000);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(slideRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(slideRunnable, 5000);
    }
}
