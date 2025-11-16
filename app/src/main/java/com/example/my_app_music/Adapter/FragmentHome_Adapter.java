package com.example.my_app_music.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.my_app_music.Fragment.FragmentHome;
import com.example.my_app_music.Fragment.FragmentSearch;

public class FragmentHome_Adapter extends FragmentStateAdapter {

    public FragmentHome_Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                // Trang chủ
                return new FragmentHome();
            case 1:
                // Tìm kiếm
                return new FragmentSearch();
            default:
                return new FragmentHome();
        }
    }

    @Override
    public int getItemCount() {
        // Có 2 màn: Home, Search
        return 2;
    }
}
