package com.example.app_music.Utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 🎵 SupabaseManagerMusic
 * Quản lý API bài hát và thể loại nhạc
 */
public class SupabaseManagerMusic {

    // 🧾 Lấy toàn bộ bài hát
    public static JSONArray getAllSongs() {
        try {
            String response = SupabaseManager.makeRequest(SupabaseManager.SONGS_URL, "GET", null);
            return new JSONArray(response);
        } catch (Exception e) {
            Log.e(SupabaseManager.TAG, "GetAllSongs error", e);
            return null;
        }
    }

    // 🎧 Lấy bài hát theo thể loại
    public static JSONArray getSongsByCategory(String category) {
        try {
            String url = SupabaseManager.SONGS_URL + "?category=eq." + category;
            String response = SupabaseManager.makeRequest(url, "GET", null);
            return new JSONArray(response);
        } catch (Exception e) {
            Log.e(SupabaseManager.TAG, "GetSongsByCategory error", e);
            return null;
        }
    }

    // ➕ Thêm bài hát mới
    public static boolean addSong(String title, String artist, String category, String urlMusic) {
        try {
            JSONObject json = new JSONObject();
            json.put("title", title);
            json.put("artist", artist);
            json.put("category", category);
            json.put("url", urlMusic);

            String response = SupabaseManager.makeRequest(SupabaseManager.SONGS_URL, "POST", json);
            Log.d(SupabaseManager.TAG, "Add song response: " + response);
            return true;

        } catch (Exception e) {
            Log.e(SupabaseManager.TAG, "AddSong error", e);
            return false;
        }
    }
}
