package com.example.muridgameapp;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private PostAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView recyclerView;
    private DrawerLayout drawerLayout;
    private String currentSearchQuery = "";
    private int currentCategoryId = -1; // -1 means all categories (Home)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Force Light Mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Setup Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 2. Setup Drawer and Toggle
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // 3. Setup RecyclerView and SwipeRefresh
        recyclerView = findViewById(R.id.recyclerView);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        swipeRefresh.setOnRefreshListener(() -> {
            if (currentCategoryId == -1) {
                loadData();
            } else {
                loadDataByCategory(currentCategoryId);
            }
        });

        // 4. Handle Back Press using OnBackPressedDispatcher
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed();
                    setEnabled(true);
                }
            }
        });

        loadData();
    }

    private void loadData() {
        currentCategoryId = -1;
        swipeRefresh.setRefreshing(true);
        RetrofitClient.getService().getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                swipeRefresh.setRefreshing(false);
                if (response.isSuccessful() && response.body() != null) {
                    displayPosts(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                swipeRefresh.setRefreshing(false);
                Log.e("TEST_API", "Error: " + t.getMessage());
            }
        });
    }

    private void loadDataByCategory(int categoryId) {
        currentCategoryId = categoryId;
        swipeRefresh.setRefreshing(true);
        RetrofitClient.getService().getPostsByCategory(categoryId).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                swipeRefresh.setRefreshing(false);
                if (response.isSuccessful() && response.body() != null) {
                    displayPosts(response.body());
                } else {
                    Toast.makeText(MainActivity.this, "Kategori kosong atau error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                swipeRefresh.setRefreshing(false);
                Toast.makeText(MainActivity.this, "Gagal memuat kategori", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayPosts(List<Post> posts) {
        if (adapter == null) {
            adapter = new PostAdapter(posts);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.updateData(posts);
        }
        if (!currentSearchQuery.isEmpty()) {
            adapter.filter(currentSearchQuery);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Cari artikel...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                currentSearchQuery = query;
                if (adapter != null) adapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                currentSearchQuery = newText;
                if (adapter != null) adapter.filter(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            loadData();
        } else if (id == R.id.nav_mouse) {
            loadDataByCategory(3);
        } else if (id == R.id.nav_keyboard) {
            loadDataByCategory(4);
        } else if (id == R.id.nav_console) {
            loadDataByCategory(6);
        } else if (id == R.id.nav_audio) {
            loadDataByCategory(5);
        } else if (id == R.id.nav_about) {
            openUrl("https://muridgame.site/about");
        } else if (id == R.id.nav_disclaimer) {
            openUrl("https://muridgame.site/disclaimer");
        } else if (id == R.id.nav_privacy) {
            openUrl("https://muridgame.site/privacy-policy");
        } else if (id == R.id.nav_policy) {
            openUrl("https://muridgame.site/contact");
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openUrl(String url) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("post_url", url);
        startActivity(intent);
    }
}
