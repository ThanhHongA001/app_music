package com.example.my_app_music.Utils_Api.Api;

import com.example.my_app_music.Utils_Api.model.Song;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiService {

    //  Lấy tất cả bài hát
    @GET("songs")
    Call<List<Song>> getAllSongs();

    //  Lọc theo album
    @GET("songs")
    Call<List<Song>> getSongsByAlbum(@Query("album_name") String albumName);

    //  Lọc theo nghệ sĩ
    @GET("songs")
    Call<List<Song>> getSongsByArtist(@Query("artist_name") String artistName);

    //  Lọc theo mood
    @GET("songs")
    Call<List<Song>> getSongsByMood(@Query("mood") String mood);

    //  Thêm bài hát mới
    @POST("songs")
    Call<Song> addSong(@Body Song song);

    //  Cập nhật bài hát theo ID
    @PATCH("songs")
    Call<Song> updateSong(@Query("id") String idFilter, @Body Song song);

    // Xóa bài hát
    @DELETE("songs")
    Call<Void> deleteSong(@Query("id") String idFilter);

    // Lấy danh sách giới hạn
    @GET("rest/v1/songs")
    Call<List<Song>> getSongsWithLimit(@Query("limit") int limit);

    // 3️⃣ Lấy bài hát sắp xếp theo ngày phát hành
    @GET("rest/v1/songs")
    Call<List<Song>> getSongsOrderByDate(@Query("order") String order);

    // Dùng dynamic URL để giữ nguyên filter eq.
    @GET
    Call<List<Song>> getSongsByDynamicUrl(@Url String url);



}
