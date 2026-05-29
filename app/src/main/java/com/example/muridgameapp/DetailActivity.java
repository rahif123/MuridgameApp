package com.example.muridgameapp;

import android.os.Bundle;
import android.webkit.WebView; // PENTING: Harus diimport
import android.webkit.WebViewClient; // PENTING: Harus diimport
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Ambil data URL yang dikirim dari Adapter
        String url = getIntent().getStringExtra("post_url");

        // Cari WebView di layout
        WebView webView = findViewById(R.id.webView);

        // Pengaturan WebView
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient()); // Agar link dibuka di dalam app

        // Load URL
        if (url != null) {
            webView.loadUrl(url);
        }
    }
}