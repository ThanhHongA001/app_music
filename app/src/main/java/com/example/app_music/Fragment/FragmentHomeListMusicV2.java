package com.example.app_music.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_music.Adapter.FragmentHomeListMusicV2_Adapter;
import com.example.app_music.Model.FragmentHomeListMusicV2_Model;
import com.example.app_music.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentHomeListMusicV2 extends Fragment {

    private RecyclerView rcvHomeListMusic;
    private FragmentHomeListMusicV2_Adapter adapter;
    private List<FragmentHomeListMusicV2_Model> musicList;

    public FragmentHomeListMusicV2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_list_music_v2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rcvHomeListMusic = view.findViewById(R.id.rcv_home_list_music_v2);

        // Cấu hình LayoutManager dạng Grid 1 cột
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        rcvHomeListMusic.setLayoutManager(gridLayoutManager);

        // Ngăn scroll trong RecyclerView
        rcvHomeListMusic.setNestedScrollingEnabled(false);

        // Dữ liệu mẫu
        musicList = new ArrayList<>();
        musicList.add(new FragmentHomeListMusicV2_Model("Hẹn Em Ở Lần Yêu Thứ 2", 1200, 350, R.drawable.bg_album_rounded));
        musicList.add(new FragmentHomeListMusicV2_Model("Đêm Trăng Tình Yêu", 980, 240, R.drawable.bg_album_rounded));
        musicList.add(new FragmentHomeListMusicV2_Model("Trái Tim Không Ngủ Yên", 1530, 400, R.drawable.bg_album_rounded));
        musicList.add(new FragmentHomeListMusicV2_Model("Cơn Mưa Ngang Qua", 2100, 530, R.drawable.bg_album_rounded));
        musicList.add(new FragmentHomeListMusicV2_Model("Tháng Năm Không Quên", 870, 190, R.drawable.bg_album_rounded));
        musicList.add(new FragmentHomeListMusicV2_Model("Lặng Thầm", 430, 120, R.drawable.bg_album_rounded));

        // Gắn adapter
        adapter = new FragmentHomeListMusicV2_Adapter(getContext(), musicList);
        rcvHomeListMusic.setAdapter(adapter);
    }
}
