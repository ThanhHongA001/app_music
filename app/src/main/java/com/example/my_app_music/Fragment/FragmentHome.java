package com.example.my_app_music.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.my_app_music.databinding.FragmentHomeBinding;

import java.util.Arrays;
import java.util.List;

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
        setupHomeSections();
    }

    private void setupHomeSections() {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        // 🟢 1️⃣ Thêm fragment banner (top 10 bài hát mới)
        FragmentHomeListMusic_V1 fragmentV1 = new FragmentHomeListMusic_V1();
        transaction.add(binding.homeFragmentContainer.getId(), fragmentV1);

        // 🟢 2️⃣ Thêm các fragment danh sách nhạc theo thể loại (V2)
        List<String> genres = Arrays.asList("Pop", "Rap", "Acoustic");
        for (String genre : genres) {
            FragmentHomeListMusic_V2 fragmentV2 = new FragmentHomeListMusic_V2();
            Bundle args = new Bundle();
            args.putString("genre", genre);
            fragmentV2.setArguments(args);
            transaction.add(binding.homeFragmentContainer.getId(), fragmentV2);
        }

        // 🟢 3️⃣ Thêm fragment hiển thị album (V3)
        FragmentHomeListMusic_V3 fragmentV3 = new FragmentHomeListMusic_V3();
        transaction.add(binding.homeFragmentContainer.getId(), fragmentV3);

        // ✅ Commit toàn bộ transaction
        transaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
