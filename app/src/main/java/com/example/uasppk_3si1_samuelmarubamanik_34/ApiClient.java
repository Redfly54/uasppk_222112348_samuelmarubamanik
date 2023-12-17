package com.example.uasppk_3si1_samuelmarubamanik_34;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "http://10.0.2.2:8080/"; // Ganti dengan URL server lokal
    private static Retrofit retrofit = null;

    public static Retrofit getClient(Context context) {
        if (retrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request original = chain.request();

                            SharedPreferences sharedPreferences = context.getSharedPreferences("YourAppPreferences", Context.MODE_PRIVATE);
                            String token = sharedPreferences.getString("jwt_token", "");

                            Request.Builder builder = original.newBuilder();
                            if (token != null) {
                                builder.header("Authorization", "Bearer " + token);
                                Log.d("JWT", "Bearer " + token); // Tambahkan log ini
                            }
                            Request request = builder.build();
                            return chain.proceed(request);
                        }
                    })
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getClientForLogin(Context context) {
        if (retrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request original = chain.request();

                            Request.Builder builder = original.newBuilder();
                            if (!original.url().encodedPath().equals("/login")) {
                                // Kode untuk menambahkan header, misalnya token
                                SharedPreferences sharedPreferences = context.getSharedPreferences("YourAppPreferences", Context.MODE_PRIVATE);
                                String token = sharedPreferences.getString("jwt_token", "");
                                if (token != null && !token.isEmpty()) {
                                    builder.header("Authorization", "Bearer " + token);
                                }
                            }
                            Request request = builder.build();
                            return chain.proceed(request);
                        }
                    })
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}
