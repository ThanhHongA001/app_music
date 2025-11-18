package com.example.my_app_music.Utils_Api.Api;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.my_app_music.Utils_Api.model.Users;

public class SessionManager {

    private static final String PREF_NAME = "user_session";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_EMAIL = "email";

    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    // NEW: lưu thông tin user sau khi đăng nhập thành công
    public void saveUser(Users user) {
        if (user == null) return;

        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putString(KEY_FULL_NAME, user.getFullName());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.apply();
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public String getFullName() {
        return prefs.getString(KEY_FULL_NAME, "");
    }

    public String getEmail() {
        return prefs.getString(KEY_EMAIL, "");
    }

    // NEW: dùng cho chức năng Đăng xuất sau này
    public void logout() {
        editor.clear();
        editor.apply();
    }


}

