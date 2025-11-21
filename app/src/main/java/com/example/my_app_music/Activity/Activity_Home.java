package com.example.my_app_music.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.my_app_music.Activity.ActivityLogin;
import com.example.my_app_music.Activity.ActivityMyAccout;
import com.example.my_app_music.Adapter.FragmentHome_Adapter;
import com.example.my_app_music.R;
import com.example.my_app_music.Utils_Api.Api.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.widget.AppCompatButton;
import de.hdodenhof.circleimageview.CircleImageView;

public class Activity_Home extends AppCompatActivity {

    private ViewPager2 homeViewPager;
    private BottomNavigationView bottomNavigationView;
    private FragmentHome_Adapter homeAdapter;
    private AppCompatButton btnLogin;
    private LinearLayout layoutUserInfo;
    private TextView txtUserName;
    private CircleImageView imgUserAvatar;
    private SessionManager sessionManager;
    private ImageView imgListFunctions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        initViews();
        initSessionAndUserUi();
        setupViewPager();
        setupBottomNav();
        syncViewPagerWithBottomNav();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUserUiFromSession();
    }
    private void initViews() {
        homeViewPager = findViewById(R.id.home_viewpager);
        bottomNavigationView = findViewById(R.id.home_bottom_navigation);
        btnLogin = findViewById(R.id.home_btn_login);
        layoutUserInfo = findViewById(R.id.home_user_info_layout);
        txtUserName = findViewById(R.id.home_txt_user_name);
        imgUserAvatar = findViewById(R.id.home_img_user_avatar);
        imgListFunctions = findViewById(R.id.home_list_functions);
    }
    private void initSessionAndUserUi() {
        sessionManager = new SessionManager(this);
        if (btnLogin != null) {
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Activity_Home.this, ActivityLogin.class);
                    startActivity(i);
                }
            });
        }
        View.OnClickListener userInfoClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chỉ mở menu khi đã đăng nhập
                if (sessionManager != null && sessionManager.isLoggedIn()) {
                    showUserDropdownMenu(v);
                }
            }
        };

        if (layoutUserInfo != null) {
            layoutUserInfo.setOnClickListener(userInfoClickListener);
        }
        if (imgUserAvatar != null) {
            imgUserAvatar.setOnClickListener(userInfoClickListener);
        }
        if (txtUserName != null) {
            txtUserName.setOnClickListener(userInfoClickListener);
        }

        imgListFunctions.setOnClickListener(v -> showLeftFunctionsMenu(v));

        updateUserUiFromSession();
    }
    private void showUserDropdownMenu(View anchor) {
        PopupMenu popupMenu = new PopupMenu(this, anchor);
        popupMenu.getMenuInflater().inflate(R.menu.menu_user_dropdown_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_home) {
                    // MÀN "Tài khoản của tôi" (danh sách nhạc yêu thích / gần đây)
                    Intent intent = new Intent(Activity_Home.this, ActivityMyAccout.class);
                    startActivity(intent);
                    return true;

                } else if (id == R.id.nav_account_info) {
                    // MÀN "Thông tin tài khoản"
                    Intent intent = new Intent(Activity_Home.this, com.example.my_app_music.Activity.User.ActivityAccoutInfo.class);
                    startActivity(intent);
                    return true;

                } else if (id == R.id.nav_logout) {
                    if (sessionManager != null) {
                        sessionManager.logout();
                    }
                    updateUserUiFromSession();
                    Toast.makeText(Activity_Home.this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });

        popupMenu.show();
    }
    private void updateUserUiFromSession() {
        if (btnLogin == null || layoutUserInfo == null || txtUserName == null) return;
        if (sessionManager == null) return;

        if (sessionManager.isLoggedIn()) {
            // Đã đăng nhập -> ẩn nút login, hiện info
            btnLogin.setVisibility(View.GONE);
            layoutUserInfo.setVisibility(View.VISIBLE);

            String fullName = sessionManager.getFullName();
            if (fullName == null || fullName.trim().isEmpty()) {
                fullName = "Người dùng";
            }
            txtUserName.setText(fullName);
        } else {
            btnLogin.setVisibility(View.VISIBLE);
            layoutUserInfo.setVisibility(View.GONE);
        }
    }
    private void setupViewPager() {
        homeAdapter = new FragmentHome_Adapter(this);
        homeViewPager.setAdapter(homeAdapter);
    }
    private void setupBottomNav() {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                homeViewPager.setCurrentItem(0, false);
                return true;
            } else if (itemId == R.id.nav_search) {
                homeViewPager.setCurrentItem(1, false);
                return true;
            }
            return false;
        });
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }
    private void syncViewPagerWithBottomNav() {
        homeViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.nav_home);
                        break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.nav_search);
                        break;
                }
            }
        });
    }
    private void showLeftFunctionsMenu(View anchor) {
        // Inflate layout menu
        View menuView = getLayoutInflater().inflate(R.layout.layout_home_functions_menu, null);

        // Tạo PopupWindow
        PopupWindow popupWindow = new PopupWindow(
                menuView,
                getResources().getDimensionPixelSize(R.dimen.left_menu_width),
                LinearLayout.LayoutParams.MATCH_PARENT,
                true // focusable
        );

        // Nếu không dùng dim background thì cho trong suốt
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);

        // Hiển thị menu dính cạnh trái, từ trên xuống
        popupWindow.showAtLocation(anchor, Gravity.START | Gravity.TOP, 0, 0);

        // Lấy các view trong menu để xử lý click
        TextView tvTopBxh = menuView.findViewById(R.id.menu_top_bxh);
        TextView tvNgheSi = menuView.findViewById(R.id.menu_nghe_si_thinh_hanh);
        TextView tvTop100 = menuView.findViewById(R.id.menu_top_100);
        TextView tvDangYeuThich = menuView.findViewById(R.id.menu_dang_duoc_yeu_thich);

        tvTopBxh.setOnClickListener(v -> {
            // TODO: chuyển tới màn Top BXH
            Toast.makeText(this, "Top BXH", Toast.LENGTH_SHORT).show();
            popupWindow.dismiss();
        });

        tvNgheSi.setOnClickListener(v -> {
            // TODO: xử lý
            Toast.makeText(this, "Nghệ sĩ thịnh hành", Toast.LENGTH_SHORT).show();
            popupWindow.dismiss();
        });

        tvTop100.setOnClickListener(v -> {
            // TODO: xử lý
            Toast.makeText(this, "Top 100", Toast.LENGTH_SHORT).show();
            popupWindow.dismiss();
        });

        tvDangYeuThich.setOnClickListener(v -> {
            // TODO: xử lý
            Toast.makeText(this, "Đang được yêu thích", Toast.LENGTH_SHORT).show();
            popupWindow.dismiss();
        });
    }

}
