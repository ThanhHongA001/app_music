package com.example.app_music.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.app_music.Fragment.FragmentHome;
import com.example.app_music.Fragment.FragmentSearch;

public class ActivityHomePager_Adapter extends FragmentStateAdapter {

    public ActivityHomePager_Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FragmentHome();
            case 1:
                return new FragmentSearch();
            default:
                return new FragmentHome();
        }
    }

    @Override
    public int getItemCount() {
        return 2; // có 2 fragment: Home + Search
    }
}
