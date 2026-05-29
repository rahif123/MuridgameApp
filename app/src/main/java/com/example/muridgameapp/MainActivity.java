package com.example.muridgameapp;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Memanggil API
        RetrofitClient.getService().getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 1. Cari RecyclerView di layout
                    RecyclerView recyclerView = findViewById(R.id.recyclerView);

                    // 2. Set LayoutManager agar list tampil berderet ke bawah
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                    // 3. Masukkan data ke adapter
                    PostAdapter adapter = new PostAdapter(response.body());

                    // 4. Hubungkan adapter ke recyclerView
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.e("TEST_API", "Error: " + t.getMessage());
            }
        });
    }
}