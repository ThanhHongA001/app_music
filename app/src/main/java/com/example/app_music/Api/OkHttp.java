package com.example.app_music.Api;

import okhttp3.*;
import org.json.JSONArray;

public class SupabaseManager {
    private static final String SUPABASE_URL = "https://uenttfoksdvecerlnjoc.supabase.co";
    private static final String SUPABASE_API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...";
    private static final OkHttpClient client = new OkHttpClient();

    public static void getSongs(Callback callback) {
        Request request = new Request.Builder()
                .url(SUPABASE_URL + "/rest/v1/songs?select=*")
                .addHeader("apikey", SUPABASE_API_KEY)
                .addHeader("Authorization", "Bearer " + SUPABASE_API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(callback);
    }
}
