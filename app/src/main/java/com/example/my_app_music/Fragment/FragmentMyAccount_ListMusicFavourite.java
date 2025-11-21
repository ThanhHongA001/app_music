package com.example.my_app_music.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.my_app_music.R;

public class FragmentMyAccount_ListMusicFavourite extends Fragment {

    public FragmentMyAccount_ListMusicFavourite() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_account_list_music_favourite, container, false);
    }
}