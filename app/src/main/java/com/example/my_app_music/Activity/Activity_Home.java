package com.example.my_app_music.Activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.my_app_music.Adapter.FragmentHome_Adapter;
import com.example.my_app_music.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Activity_Home extends AppCompatActivity {

    private ViewPager2 homeViewPager;
    private BottomNavigationView bottomNavigationView;
    private FragmentHome_Adapter homeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        initViews();
        setupViewPager();
        setupBottomNav();
        syncViewPagerWithBottomNav();
    }

    private void initViews() {
        homeViewPager = findViewById(R.id.home_viewpager);
        bottomNavigationView = findViewById(R.id.home_bottom_navigation);
    }

    private void setupViewPager() {
        homeAdapter = new FragmentHome_Adapter(this);
        homeViewPager.setAdapter(homeAdapter);

        // Nếu không muốn cho swipe trái/phải, có thể tắt:
        // homeViewPager.setUserInputEnabled(false);
    }

    private void setupBottomNav() {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                homeViewPager.setCurrentItem(0, false);
                return true;
            } else if (itemId == R.id.nav_search) {
                homeViewPager.setCurrentItem(1, false);
                return true;
            }
            return false;
        });

        // Mặc định chọn Home khi mở app
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }

    private void syncViewPagerWithBottomNav() {
        homeViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.nav_home);
                        break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.nav_search);
                        break;
                }
            }
        });
    }
}
