package com.example.app_music.Utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * ⚙️ Lớp quản lý kết nối đến Supabase thông qua REST API
 * Dùng để xử lý Đăng ký và Đăng nhập tài khoản người dùng.
 */
public class SupabaseManager {

    // 🔑 Thay bằng API URL & KEY thật của bạn trong Supabase
    private static final String SUPABASE_URL = "https://uenttfoksdvecerlnjoc.supabase.co";
    private static final String SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InVlbnR0Zm9rc2R2ZWNlcmxuam9jIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjA0OTk1MDYsImV4cCI6MjA3NjA3NTUwNn0.f6eagTpMf9RL47TVdII-lKNMgPI8bgNupuTYirzklSc";

    // Endpoint RESTful đến bảng "users"
    private static final String TABLE_URL = SUPABASE_URL + "/rest/v1/users";

    /**
     * 🧾 Đăng ký người dùng mới
     * @param fullname tên đầy đủ
     * @param email email người dùng
     * @param password mật khẩu người dùng
     * @return true nếu đăng ký thành công
     */
    public static boolean registerUser(String fullname, String email, String password) {
        try {
            URL url = new URL(TABLE_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("apikey", SUPABASE_KEY);
            conn.setRequestProperty("Authorization", "Bearer " + SUPABASE_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Prefer", "return=minimal");
            conn.setDoOutput(true);

            // JSON gửi lên Supabase
            JSONObject json = new JSONObject();
            json.put("fullname", fullname);
            json.put("email", email);
            json.put("password", password);

            // Gửi dữ liệu
            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.toString().getBytes());
            }

            int responseCode = conn.getResponseCode();
            Log.d("Supabase", "Register response: " + responseCode);

            // 201 = Created
            return responseCode == 201;

        } catch (Exception e) {
            Log.e("Supabase", "Register error", e);
            return false;
        }
    }

    /**
     * 🧾 Kiểm tra đăng nhập người dùng
     * @param email email
     * @param password mật khẩu
     * @return true nếu đăng nhập đúng
     */
    public static boolean loginUser(String email, String password) {
        try {
            // Gọi API để lấy user theo email
            String query = TABLE_URL + "?email=eq." + email + "&select=*";
            URL url = new URL(query);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("apikey", SUPABASE_KEY);
            conn.setRequestProperty("Authorization", "Bearer " + SUPABASE_KEY);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) sb.append(line);

                JSONArray arr = new JSONArray(sb.toString());
                if (arr.length() > 0) {
                    JSONObject user = arr.getJSONObject(0);
                    String pwd = user.getString("password");
                    // So sánh mật khẩu
                    return password.equals(pwd);
                }
            }
            return false;
        } catch (Exception e) {
            Log.e("Supabase", "Login error", e);
            return false;
        }
    }
}
