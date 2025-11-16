package com.example.my_app_music.Utils_Api.Api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient_Users {

    private static Retrofit retrofit;

    public static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient client = createHttpClient();
            retrofit = createRetrofit(client);
        }
        return retrofit;
    }

    private static OkHttpClient createHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        Request request = original.newBuilder()
                                // Header bắt buộc cho Supabase REST
                                .addHeader(Constants_Users.HEADER_API_KEY, Constants_Users.SUPABASE_API_KEY)
                                .addHeader(Constants_Users.HEADER_AUTHORIZATION, "Bearer " + Constants_Users.SUPABASE_API_KEY)
                                .addHeader(Constants_Users.HEADER_CONTENT_TYPE, "application/json")
                                // Prefer: return=representation để Supabase trả về row vừa insert
                                .addHeader(Constants_Users.HEADER_PREFER, "return=representation")
                                .build();

                        return chain.proceed(request);
                    }
                })
                .build();
    }

    private static Retrofit createRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(Constants_Users.SUPABASE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
}
