package com.example.app_music.Utils;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * ⚙️ SupabaseManager
 * Lớp trung tâm quản lý cấu hình và hàm HTTP chung
 * Dùng chung cho tất cả module con (User, Music)
 */
public class SupabaseManager {

    // =============== 🔑 Cấu hình Supabase ==================
    public static final String SUPABASE_URL = "https://uenttfoksdvecerlnjoc.supabase.co";
    public static final String SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InVlbnR0Zm9rc2R2ZWNlcmxuam9jIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjA0OTk1MDYsImV4cCI6MjA3NjA3NTUwNn0.f6eagTpMf9RL47TVdII-lKNMgPI8bgNupuTYirzklSc";

    // Endpoint RESTful
    public static final String USERS_URL = SUPABASE_URL + "/rest/v1/users";
    public static final String SONGS_URL = SUPABASE_URL + "/rest/v1/songs";

    public static final String TAG = "Supabase";

    // =======================================================
    // 🌐 Hàm dùng chung để gửi HTTP request
    // =======================================================
    public static String makeRequest(String urlString, String method, JSONObject body) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod(method);
        conn.setRequestProperty("apikey", SUPABASE_KEY);
        conn.setRequestProperty("Authorization", "Bearer " + SUPABASE_KEY);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Prefer", "return=representation");

        if (body != null) {
            conn.setDoOutput(true);
            try (OutputStream os = conn.getOutputStream()) {
                os.write(body.toString().getBytes());
            }
        }

        int responseCode = conn.getResponseCode();
        Log.d(TAG, "HTTP " + method + " → " + responseCode);

        BufferedReader reader = (responseCode >= 200 && responseCode < 300)
                ? new BufferedReader(new InputStreamReader(conn.getInputStream()))
                : new BufferedReader(new InputStreamReader(conn.getErrorStream()));

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) sb.append(line);
        reader.close();

        return sb.toString();
    }
}
