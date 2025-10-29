package com.example.app_music.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app_music.Adapter.FragmentHomeListMusicV1_Adapter;
import com.example.app_music.Model.FragmentHomeListMusicV1_Model;
import com.example.app_music.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentHomeListMusicV1 extends Fragment {

    private RecyclerView recyclerView;
    private FragmentHomeListMusicV1_Adapter adapter;
    private List<FragmentHomeListMusicV1_Model> albumList;

    public FragmentHomeListMusicV1() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_list_music_v1, container, false);

        recyclerView = view.findViewById(R.id.rcv_home_list_music_v1);

        // Gán layout manager dạng ngang
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // Dữ liệu mẫu
        albumList = new ArrayList<>();
        albumList.add(new FragmentHomeListMusicV1_Model("Best of Sơn Tùng MTP", R.drawable.bg_album_rounded));
        albumList.add(new FragmentHomeListMusicV1_Model("Top Hit 2025", R.drawable.bg_album_rounded));
        albumList.add(new FragmentHomeListMusicV1_Model("Acoustic Chill", R.drawable.bg_album_rounded));
        albumList.add(new FragmentHomeListMusicV1_Model("EDM Bùng Nổ", R.drawable.bg_album_rounded));
        albumList.add(new FragmentHomeListMusicV1_Model("Nhạc Trẻ Hay Nhất", R.drawable.bg_album_rounded));

        adapter = new FragmentHomeListMusicV1_Adapter(getContext(), albumList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
