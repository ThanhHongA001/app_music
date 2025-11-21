// ActivityMyAccout_Adapter.java
package com.example.my_app_music.Activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.my_app_music.Fragment.User.FragmentMyAccount_ListMusicFavourite;
import com.example.my_app_music.Fragment.User.FragmentMyAccount_ListMusicRecently;

public class ActivityMyAccout_Adapter extends FragmentStateAdapter {

    public ActivityMyAccout_Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // position 0: Trang nhạc yêu thích
        // position 1: trang nhạc đã nghe gần đây
        if (position == 0) {
            return new FragmentMyAccount_ListMusicFavourite();
        } else {
            return new FragmentMyAccount_ListMusicRecently();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
