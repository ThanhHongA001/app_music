package com.example.app_music.Utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 🧍 SupabaseManagerUser
 * Quản lý người dùng (đăng ký, đăng nhập) sử dụng bảng users trên Supabase
 */
public class SupabaseManagerUser {

    /**
     * 🧾 Đăng ký người dùng mới
     */
    public static boolean registerUser(String fullname, String email, String password) {
        try {
            // 🧱 Tạo JSON body gửi lên Supabase
            JSONObject json = new JSONObject();
            json.put("fullname", fullname);
            json.put("email", email);
            json.put("password", password);

            // Gửi POST request đến bảng users
            String response = SupabaseManager.makeRequest(SupabaseManager.USERS_URL, "POST", json);
            Log.d(SupabaseManager.TAG, "Register response: " + response);

            // Nếu response có chứa id → đăng ký thành công
            JSONObject resJson = new JSONObject(response);
            return resJson.has("id");

        } catch (Exception e) {
            Log.e(SupabaseManager.TAG, "Register error", e);
            return false;
        }
    }

    /**
     * 🔐 Đăng nhập người dùng (so khớp email + password)
     */
    public static boolean loginUser(String email, String password) {
        try {
            // 🧩 Supabase RESTful query:
            //    ?email=eq.<email> → lọc theo email
            //    &select=* → lấy tất cả cột
            String url = SupabaseManager.USERS_URL + "?email=eq." + email + "&select=*";

            // Gửi GET request
            String response = SupabaseManager.makeRequest(url, "GET", null);
            Log.d(SupabaseManager.TAG, "Login response: " + response);

            // Parse kết quả trả về
            JSONArray arr = new JSONArray(response);
            if (arr.length() > 0) {
                JSONObject user = arr.getJSONObject(0);
                String storedPwd = user.getString("password");
                // So sánh mật khẩu nhập vào và mật khẩu trong DB
                return password.equals(storedPwd);
            }
            return false;

        } catch (Exception e) {
            Log.e(SupabaseManager.TAG, "Login error", e);
            return false;
        }
    }
}
