package com.example.my_app_music.Utils_Api.Api;

public class Constants_Users {

    // ========= SUPABASE CONFIG =========
    public static final String SUPABASE_BASE_URL = "https://uenttfoksdvecerlnjoc.supabase.co/rest/v1/";

    // Dùng anon public API key của Supabase
    public static final String SUPABASE_API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InVlbnR0Zm9rc2R2ZWNlcmxuam9jIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjA0OTk1MDYsImV4cCI6MjA3NjA3NTUwNn0.f6eagTpMf9RL47TVdII-lKNMgPI8bgNupuTYirzklSc";

    // Tên bảng Users trên Supabase
    public static final String TABLE_USERS = "Users";

    // ========= HEADER KEYS (theo chuẩn Supabase) =========
    public static final String HEADER_API_KEY = "apikey";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_PREFER = "Prefer";

}
