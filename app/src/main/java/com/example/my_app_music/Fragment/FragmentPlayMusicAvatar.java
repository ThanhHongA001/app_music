package com.example.my_app_music.Fragment;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import androidx.fragment.app.Fragment;

import com.example.my_app_music.R;
import com.google.android.material.imageview.ShapeableImageView;

public class FragmentPlayMusicAvatar extends Fragment {

    private ShapeableImageView imgAvatarMusic;
    private ObjectAnimator rotationAnimator;

    public FragmentPlayMusicAvatar() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_play_music_avatar, container, false);

        initViews(view);        // 1. Ánh xạ View
        setupRotation();        // 2. Chuẩn bị animation xoay

        return view;
    }

    // ==============================
    // 1. Ánh xạ View
    // ==============================
    private void initViews(View view) {
        imgAvatarMusic = view.findViewById(R.id.imgAvatarMusic);
    }

    // ==============================
    // 2. Setup animation xoay avatar
    // ==============================
    private void setupRotation() {
        rotationAnimator = ObjectAnimator.ofFloat(imgAvatarMusic, "rotation", 0f, 360f);
        rotationAnimator.setDuration(8000);
        rotationAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        rotationAnimator.setInterpolator(new LinearInterpolator());
    }

    // ==============================
    // 3. Hàm điều khiển xoay
    // ==============================
    public void startRotate() {
        if (rotationAnimator != null) rotationAnimator.start();
    }

    public void pauseRotate() {
        if (rotationAnimator != null) rotationAnimator.pause();
    }
}
