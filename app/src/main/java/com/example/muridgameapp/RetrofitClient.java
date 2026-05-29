package com.example.muridgameapp;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static ApiService getService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://muridgame.site/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ApiService.class);
    }
}