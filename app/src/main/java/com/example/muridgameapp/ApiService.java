package com.example.muridgameapp;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("wp-json/wp/v2/posts?_embed") // Tambahkan ?_embed di sini
    Call<List<Post>> getPosts();
}