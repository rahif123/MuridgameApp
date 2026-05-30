package com.example.muridgameapp;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("wp-json/wp/v2/posts?_embed")
    Call<List<Post>> getPosts();

    @GET("wp-json/wp/v2/posts?_embed")
    Call<List<Post>> getPostsByCategory(@Query("categories") int categoryId);
}