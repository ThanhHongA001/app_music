package com.example.my_app_music.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.my_app_music.Activity.ActivityLogin;
import com.example.my_app_music.Activity.ActivityPlayMusic;
import com.example.my_app_music.Activity.ActivityRegister;
import com.example.my_app_music.Fragment.FragmentHome;
import com.example.my_app_music.Fragment.FragmentHomeListMusic_V1;
import com.example.my_app_music.Fragment.FragmentHomeListMusic_V2;
import com.example.my_app_music.Fragment.FragmentHomeListMusic_V3;
import com.example.my_app_music.Fragment.FragmentSearch;
import com.example.my_app_music.R;

public class ActivityTestScreen extends AppCompatActivity {

    // Các nút của activity
    private Button btnDangNhap, btnDangKy, btnHome, btnPlay;

    // Các nút của fragment
    private Button btnFragmentHome, btnFragmentV1, btnFragmentV2, btnFragmentV3, btnFragmentSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test_screen);

        // Gọi hàm ánh xạ và setup logic
        TestButton_Activity();
        TestButton_Fragment();
        Setup_Logic();
    }

    // Hàm ánh xạ và setup cho các nút mở Activity khác
    private void TestButton_Activity() {
        btnDangNhap = findViewById(R.id.testscreen_btn_dangnhap);
        btnDangKy = findViewById(R.id.testscreen_btn_dangky);
        btnHome = findViewById(R.id.testscreen_btn_home);
        btnPlay = findViewById(R.id.testscreen_btn_play);

        // Chuyển sang Activity Login
        btnDangNhap.setOnClickListener(v -> {
            Intent intent = new Intent(this, ActivityLogin.class);
            startActivity(intent);
        });

        // Chuyển sang Activity Register
        btnDangKy.setOnClickListener(v -> {
            Intent intent = new Intent(this, ActivityRegister.class);
            startActivity(intent);
        });

        // Chuyển sang Activity Home
        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(this, Activity_Home.class);
            startActivity(intent);
        });

        // Chuyển sang Activity Play
        btnPlay.setOnClickListener(v -> {
            Intent intent = new Intent(this, ActivityPlayMusic.class);
            startActivity(intent);
        });
    }

    // Hàm ánh xạ các nút fragment
    private void TestButton_Fragment() {
        btnFragmentHome = findViewById(R.id.testscreen_btn_fragment_home);
        btnFragmentV1 = findViewById(R.id.testscreen_btn_fragment_v1);
        btnFragmentV2 = findViewById(R.id.testscreen_btn_fragment_v2);
        btnFragmentV3 = findViewById(R.id.testscreen_btn_fragment_v3);
        btnFragmentSetting = findViewById(R.id.testscreen_btn_fragment_setting);
    }

    // Hàm xử lý sự kiện khi nhấn các nút fragment để hiển thị fragment tương ứng
    private void Setup_Logic() {
        btnFragmentHome.setOnClickListener(v -> replaceFragment(new FragmentHome()));
        btnFragmentV1.setOnClickListener(v -> replaceFragment(new FragmentHomeListMusic_V1()));
        btnFragmentV2.setOnClickListener(v -> replaceFragment(new FragmentHomeListMusic_V2()));
        btnFragmentV3.setOnClickListener(v -> replaceFragment(new FragmentHomeListMusic_V3()));
        btnFragmentSetting.setOnClickListener(v -> replaceFragment(new FragmentSearch()));
    }

    // Hàm dùng để thay thế fragment trong khung FrameLayout
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.testscreen_frame_container, fragment);
        transaction.commit();
    }
}
