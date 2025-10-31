package com.example.app_music.Utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 🎶 SupabaseMusicCategory
 * Lớp quản lý danh sách các thể loại nhạc và link API tương ứng
 * Giúp dễ dàng gọi API từng thể loại khi cần.
 */
public class SupabaseMusicCategory {

    // ✅ Domain chính của Supabase (sử dụng từ SupabaseManager)
    private static final String BASE_URL = SupabaseManager.SUPABASE_URL + "/rest/v1/songs?category=eq.";

    /**
     * 🧱 Hàm lấy danh sách toàn bộ thể loại nhạc
     * @return Map<String, String> (tên thể loại → link API)
     */
    public static Map<String, String> getAllCategoryApiLinks() {
        Map<String, String> map = new LinkedHashMap<>();

        map.put("🎧 Nhạc Buồn", BASE_URL + "nhac_buon");
        map.put("🎉 Nhạc Vui", BASE_URL + "nhac_vui");
        map.put("❤️ Nhạc Trữ Tình", BASE_URL + "nhac_tru_tinh");
        map.put("💛 Nhạc Vàng", BASE_URL + "nhac_vang");
        map.put("❤️‍🔥 Nhạc Đỏ", BASE_URL + "nhac_do");
        map.put("💕 Nhạc Balat", BASE_URL + "nhac_balat");
        map.put("🎤 Nhạc Pop", BASE_URL + "nhac_pop");
        map.put("🧠 Nhạc Rap", BASE_URL + "nhac_rap");
        map.put("🌙 Nhạc Chill", BASE_URL + "nhac_chill");
        map.put("⚡ Nhạc EDM", BASE_URL + "nhac_edm");
        map.put("🎚️ Nhạc Remix", BASE_URL + "nhac_remix");
        map.put("🧒 Nhạc Trẻ", BASE_URL + "nhac_tre");
        map.put("👶 Nhạc Thiếu Nhi", BASE_URL + "nhac_thieu_nhi");
        map.put("🕹️ Nhạc Game", BASE_URL + "nhac_game");
        map.put("📚 Nhạc Học Tập", BASE_URL + "nhac_hoc_tap");
        map.put("☕ Nhạc Lofi", BASE_URL + "nhac_lofi");
        map.put("🎬 Nhạc Phim", BASE_URL + "nhac_phim");

        return map;
    }

    /**
     * 🔍 Lấy link API theo tên mã thể loại (vd: "nhac_buon", "nhac_vui")
     */
    public static String getApiLinkByKey(String key) {
        return BASE_URL + key;
    }

    /**
     * 🔍 Lấy link API theo tên hiển thị (vd: "🎧 Nhạc Buồn")
     */
    public static String getApiLinkByDisplayName(String displayName) {
        return getAllCategoryApiLinks().get(displayName);
    }
}
