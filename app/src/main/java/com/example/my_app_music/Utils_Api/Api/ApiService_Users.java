package com.example.my_app_music.Utils_Api.Api;

import com.example.my_app_music.Utils_Api.model.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService_Users {

    // Đăng ký: insert row vào bảng Users
    @POST(Constants_Users.TABLE_USERS)
    Call<List<Users>> register(@Body Users user);

    // Đăng nhập: filter theo email & password
    // Supabase REST thường yêu cầu có select=*
    @GET(Constants_Users.TABLE_USERS)
    Call<List<Users>> login(
            @Query("select") String select,
            @Query("email") String emailEq,
            @Query("password") String passwordEq
    );
}
