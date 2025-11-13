package com.example.my_app_music.Utils_Api.Api;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class ApiClient {

    private static Retrofit retrofit;

    // ===================== PUBLIC =====================
    public static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient client = createHttpClient();
            retrofit = createRetrofit(client);
        }
        return retrofit;
    }

    // ===================== CREATE HTTP CLIENT =====================
    private static OkHttpClient createHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(ApiClient::addDefaultHeaders)
                .build();
    }

    // Thêm các header mặc định cho mỗi request
    private static Response addDefaultHeaders(Interceptor.Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
                .addHeader(Constants.HEADER_API_KEY, Constants.API_KEY)
                .addHeader(Constants.HEADER_AUTHORIZATION, "Bearer " + Constants.API_KEY)
                .addHeader(Constants.HEADER_CONTENT_TYPE, "application/json")
                .build();
        return chain.proceed(request);
    }

    // ===================== CREATE RETROFIT =====================
    private static Retrofit createRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
}
