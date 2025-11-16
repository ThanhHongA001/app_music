package com.example.my_app_music.Utils_Api.Api;

// 📚 Chứa toàn bộ URL & header key cho Supabase API
public class Constants_Music {

    // 🌍 URL gốc của Supabase Project
    public static final String BASE_URL = "https://uenttfoksdvecerlnjoc.supabase.co/rest/v1/";
    // 🔑 Supabase API Key (Public Anon Key)
    public static final String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InVlbnR0Zm9rc2R2ZWNlcmxuam9jIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjA0OTk1MDYsImV4cCI6MjA3NjA3NTUwNn0.f6eagTpMf9RL47TVdII-lKNMgPI8bgNupuTYirzklSc";

    // 🔒 Header mặc định khi gọi API Supabase
    public static final String HEADER_API_KEY = "apikey";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";

    // 🧱 Endpoint gốc cho bảng "songs"
    public static final String SONGS_ENDPOINT = BASE_URL + "songs";

    // 🔹 Lấy 10 bài hát mới nhất
    public static final String GET_TOP_10_NEW_RELEASE = SONGS_ENDPOINT + "?order=release_date.desc&limit=10";
    // Lọc theo thể loại nhạc

    // Hàm tạo URL filter theo genre
    public static String getSongsByGenre(String genre) {
        return SONGS_ENDPOINT + "?genre=eq." + genre; // giữ nguyên dấu .
    }

    // 🔍 Hàm tạo URL search theo title / artist / album (Supabase PostgREST)
    public static String buildSearchSongsUrl(String keyword) {
        try {
            String encoded = java.net.URLEncoder.encode(keyword, "UTF-8");
            // or=(title.ilike.*kw*,artist_name.ilike.*kw*,album_name.ilike.*kw*)&limit=20
            return SONGS_ENDPOINT
                    + "?or=(title.ilike.*" + encoded
                    + "*,artist_name.ilike.*" + encoded
                    + "*,album_name.ilike.*" + encoded
                    + "*)&limit=20";
        } catch (Exception e) {
            return SONGS_ENDPOINT + "?limit=0";
        }
    }

    // 💿 ALBUMS
    public static final String ALBUMS_ENDPOINT = BASE_URL + "albums";

    public static final String GET_ALL_ALBUMS = ALBUMS_ENDPOINT;
    public static final String GET_TOP_10_ALBUMS = ALBUMS_ENDPOINT + "?limit=10";


}
