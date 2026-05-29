package com.example.muridgameapp;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Post {
    @SerializedName("title")
    public Title title;

    public String link;

    @SerializedName("excerpt")
    public Excerpt excerpt;

    @SerializedName("_embedded")
    public Embedded embedded;

    public static class Title { public String rendered; }
    public static class Excerpt { public String rendered; }

    public static class Embedded {
        @SerializedName("wp:featuredmedia")
        public List<FeaturedMedia> featuredMedia;
    }

    public static class FeaturedMedia {
        @SerializedName("source_url")
        public String sourceUrl;
    }
}