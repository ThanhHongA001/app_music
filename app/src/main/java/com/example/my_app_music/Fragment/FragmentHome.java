package com.example.my_app_music.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.my_app_music.R;
import com.example.my_app_music.databinding.FragmentHomeBinding;

public class FragmentHome extends Fragment {

    private FragmentHomeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupBanner();
        setupMusicList();
        setupAlbumList();
    }

    // 1️⃣ Banner slider
    private void setupBanner() {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(binding.homeBannerContainer.getId(), new FragmentHomeListMusic_V1());
        ft.commit();
    }

    // 2️⃣ Danh sách thể loại nhạc
    private void setupMusicList() {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(binding.homeMusicListContainer.getId(), new FragmentHomeListMusic_V2());
        ft.commit();
    }

    // 3️⃣ Danh sách album
    private void setupAlbumList() {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(binding.homeAlbumListContainer.getId(), new FragmentHomeListMusic_V3());
        ft.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
