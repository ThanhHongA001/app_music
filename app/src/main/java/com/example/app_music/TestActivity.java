package com.example.app_music;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.app_music.Fragment.FragmentHome;
import com.example.app_music.Fragment.FragmentHomeListMusicV3;
import com.example.app_music.Fragment.FragmentHomeListMusicV2;
import com.example.app_music.Fragment.FragmentSearch;
import com.example.app_music.Fragment.FragmentHomeListMusicV1;

public class TestActivity extends AppCompatActivity {

    // Button nhóm Activity
    private Button btnDN, btnDK, btnHome, btnPlay;
    // Button nhóm Fragment
    private Button btnFR_H, btnFR_V1, btnFR_V2, btnFR_S, btnFR_TM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test);

        // Gọi hàm setup
        setupActivityButtons();
        setupFragmentButtons();
    }

    // HÀM 1: Setup các nút mở Activity
    private void setupActivityButtons() {
        btnDN = findViewById(R.id.test_btn_dn);
        btnDK = findViewById(R.id.test_btn_dk);
        btnHome = findViewById(R.id.test_btn_home);
        btnPlay = findViewById(R.id.test_btn_play);

        // Chuyển sang ActivityLogin
        btnDN.setOnClickListener(v -> {
            Intent intent = new Intent(this, ActivityLogin.class);
            startActivity(intent);
        });

        // Chuyển sang ActivityRegister
        btnDK.setOnClickListener(v -> {
            Intent intent = new Intent(this, ActivityRegister.class);
            startActivity(intent);
        });

        // Chuyển sang ActivityHome
        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(this, ActivityHome.class);
            startActivity(intent);
        });

        // Chuyển sang ActivityPlayMusic
        btnPlay.setOnClickListener(v -> {
            Intent intent = new Intent(this, ActivityPlayMusic.class);
            startActivity(intent);
        });
    }

    // HÀM 2: Setup các nút hiển thị Fragment
    private void setupFragmentButtons() {
        btnFR_H = findViewById(R.id.test_btn_fr_h);
        btnFR_V1 = findViewById(R.id.test_btn_fr_v1);
        btnFR_V2 = findViewById(R.id.test_btn_fr_v2);
        btnFR_S = findViewById(R.id.test_btn_fr_s);
        btnFR_TM = findViewById(R.id.test_btn_fr_tm);

        btnFR_H.setOnClickListener(v -> replaceFragment(new FragmentHome()));
        btnFR_V1.setOnClickListener(v -> replaceFragment(new FragmentHomeListMusicV3()));
        btnFR_V2.setOnClickListener(v -> replaceFragment(new FragmentHomeListMusicV2()));
        btnFR_S.setOnClickListener(v -> replaceFragment(new FragmentSearch()));
        btnFR_TM.setOnClickListener(v -> replaceFragment(new FragmentHomeListMusicV1()));
    }

    // Hàm tiện ích dùng chung để replace Fragment
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.test_frame_main, fragment);
        transaction.commit();
    }
}
