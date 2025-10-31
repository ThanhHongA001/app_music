package com.example.app_music.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.app_music.R;

import java.util.Arrays;
import java.util.List;

/**
 * 🏠 Fragment Home – hiển thị danh sách các thể loại nhạc
 */
public class FragmentHome extends Fragment {

    private ViewGroup containerLayout;

    public FragmentHome() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        containerLayout = view.findViewById(R.id.layout_home_container);

        // 🧾 Danh sách thể loại nhạc muốn hiển thị
        List<String> categories = Arrays.asList(
                "nhac_buon",
                "nhac_vui",
                "nhac_tru_tinh",
                "nhac_vang",
                "nhac_do",
                "nhac_balat",
                "nhac_pop",
                "nhac_rap",
                "nhac_chill",
                "nhac_edm",
                "nhac_remix",
                "nhac_tre",
                "nhac_thieu_nhi",
                "nhac_game",
                "nhac_hoc_tap",
                "nhac_lofi",
                "nhac_phim",
                "nhac_que_huong",
                "nhac_giang_sinh",
                "nhac_mua_xuan"
        );

        // 🔁 Thêm từng fragment con theo thể loại
        for (String category : categories) {
            addCategoryFragment(category);
        }
    }

    /**
     * ➕ Hàm thêm FragmentHomeListMusicV2 vào layout theo thể loại
     */
    private void addCategoryFragment(String category) {
        Fragment childFragment = FragmentHomeListMusicV2.newInstance(category);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.layout_home_container, childFragment);
        transaction.commit();
    }
}
