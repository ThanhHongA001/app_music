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
        imgAvatarMusic = view.findViewById(R.id.imgAvatarMusic);

        rotationAnimator = ObjectAnimator.ofFloat(imgAvatarMusic, "rotation", 0f, 360f);
        rotationAnimator.setDuration(8000);
        rotationAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        rotationAnimator.setInterpolator(new LinearInterpolator());

        return view;
    }

    public void startRotate() {
        if (rotationAnimator != null) rotationAnimator.start();
    }

    public void pauseRotate() {
        if (rotationAnimator != null) rotationAnimator.pause();
    }
}
