package com.example.my_app_music.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.my_app_music.Fragment.FragmentPlayMusicAvatar;
import com.example.my_app_music.Fragment.FragmentPlayMusicLyrics;

public class FragmentPlayMusicPagerAdapter extends FragmentStateAdapter {

    public FragmentPlayMusicPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return position == 0 ? new FragmentPlayMusicAvatar() : new FragmentPlayMusicLyrics();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
