package com.example.app_music.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_music.Adapter.FragmentHomeListMusicV2_Adapter;
import com.example.app_music.Model.FragmentHomeListMusicV2_Model;
import com.example.app_music.R;
import com.example.app_music.Utils.SupabaseManagerMusic;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 🎶 Fragment hiển thị danh sách bài hát theo thể loại
 */
public class FragmentHomeListMusicV2 extends Fragment {

    private RecyclerView rcvHomeListMusic;
    private FragmentHomeListMusicV2_Adapter adapter;
    private List<FragmentHomeListMusicV2_Model> musicList;
    private String category; // 🏷️ thể loại nhạc được truyền vào

    // 🔧 Hàm tạo instance fragment kèm thể loại
    public static FragmentHomeListMusicV2 newInstance(String category) {
        FragmentHomeListMusicV2 fragment = new FragmentHomeListMusicV2();
        Bundle args = new Bundle();
        args.putString("category", category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_list_music_v2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 🧩 Lấy thể loại được truyền từ Bundle
        if (getArguments() != null) {
            category = getArguments().getString("category");
        }

        rcvHomeListMusic = view.findViewById(R.id.rcv_home_list_music_v2);

        // Cấu hình RecyclerView
        rcvHomeListMusic.setLayoutManager(new GridLayoutManager(getContext(), 1));
        rcvHomeListMusic.setNestedScrollingEnabled(false);

        // Tạo list rỗng ban đầu
        musicList = new ArrayList<>();
        adapter = new FragmentHomeListMusicV2_Adapter(getContext(), musicList);
        rcvHomeListMusic.setAdapter(adapter);

        // ⏳ Gọi API tải nhạc theo thể loại
        loadSongsFromApi(category);
    }

    /**
     * 🌐 Hàm tải nhạc theo thể loại từ Supabase API
     */
    private void loadSongsFromApi(String category) {
        new AsyncTask<Void, Void, JSONArray>() {
            @Override
            protected JSONArray doInBackground(Void... voids) {
                // Gọi API qua lớp quản lý SupabaseManagerMusic
                return SupabaseManagerMusic.getSongsByCategory(category);
            }

            @Override
            protected void onPostExecute(JSONArray result) {
                if (result != null) {
                    musicList.clear();
                    try {
                        // Duyệt mảng JSON để đưa dữ liệu vào danh sách
                        for (int i = 0; i < result.length(); i++) {
                            JSONObject obj = result.getJSONObject(i);
                            String title = obj.optString("title", "Không tên");
                            int luotNghe = obj.optInt("view_count", 0);
                            int luotThich = obj.optInt("like_count", 0);
                            int anh = R.drawable.bg_album_rounded; // Ảnh mặc định

                            musicList.add(new FragmentHomeListMusicV2_Model(title, luotNghe, luotThich, anh));
                        }
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getContext(), "Không thể tải nhạc: " + category, Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }
}
