package com.example.app_music.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_music.Adapter.FragmentHomeListMusicV3_Adapter;
import com.example.app_music.Model.FragmentHomeListMusicV3_Model;
import com.example.app_music.R;
import com.example.app_music.Utils.SupabaseManagerMusic;
import com.example.app_music.Utils.SupabaseMusicCategory;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 🎧 FragmentHomeListMusicV1
 * Hiển thị danh sách album (theo thể loại nhạc) dạng ngang.
 * Mỗi album đại diện cho một thể loại nhạc lấy từ Supabase.
 */
public class FragmentHomeListMusicV3 extends Fragment {

    private RecyclerView recyclerView;
    private FragmentHomeListMusicV3_Adapter adapter;
    private List<FragmentHomeListMusicV3_Model> albumList;

    public FragmentHomeListMusicV3() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_list_music_v3, container, false);

        initUI(view);
        setupRecyclerView();
        loadAlbumsFromSupabase();

        return view;
    }

    // ============================== ⚙️ NHÓM 1: KHỞI TẠO GIAO DIỆN ==============================

    private void initUI(View view) {
        recyclerView = view.findViewById(R.id.rcv_home_list_music_v1);
        albumList = new ArrayList<>();
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new FragmentHomeListMusicV3_Adapter(getContext(), albumList);
        recyclerView.setAdapter(adapter);
    }

    // ============================== ⚙️ NHÓM 2: XỬ LÝ DỮ LIỆU API ==============================

    private void loadAlbumsFromSupabase() {
        new Thread(() -> {
            try {
                Map<String, String> categoryLinks = SupabaseMusicCategory.getAllCategoryApiLinks();

                for (Map.Entry<String, String> entry : categoryLinks.entrySet()) {
                    String displayName = entry.getKey(); // 🎧 Nhạc Buồn
                    String categoryKey = entry.getValue().substring(entry.getValue().lastIndexOf("=") + 1);

                    JSONObject firstSong = fetchFirstSongByCategory(categoryKey);

                    if (firstSong != null) {
                        String songTitle = firstSong.optString("title", "Không có bài hát");
                        addAlbumToList(displayName, songTitle, false);
                    } else {
                        addAlbumToList(displayName, "(Trống)", true);
                    }
                }

                updateUI();

            } catch (Exception e) {
                Log.e("Supabase", "Lỗi khi tải dữ liệu thể loại", e);
            }
        }).start();
    }

    // ============================== ⚙️ NHÓM 3: HÀM PHỤ TRỢ ==============================

    /**
     * 📦 Lấy bài hát đầu tiên trong thể loại cụ thể
     */
    private JSONObject fetchFirstSongByCategory(String categoryKey) {
        try {
            JSONArray songs = SupabaseManagerMusic.getSongsByCategory(categoryKey);
            if (songs != null && songs.length() > 0) {
                return songs.getJSONObject(0);
            }
        } catch (Exception e) {
            Log.e("Supabase", "fetchFirstSongByCategory error", e);
        }
        return null;
    }

    /**
     * ➕ Thêm 1 album vào danh sách
     */
    private void addAlbumToList(String categoryDisplay, String title, boolean isEmpty) {
        albumList.add(new FragmentHomeListMusicV3_Model(
                categoryDisplay + "\n" + title,
                R.drawable.bg_album_rounded
        ));
    }

    /**
     * 🔄 Cập nhật RecyclerView sau khi tải xong
     */
    private void updateUI() {
        new Handler(Looper.getMainLooper()).post(() -> adapter.notifyDataSetChanged());
    }
}
