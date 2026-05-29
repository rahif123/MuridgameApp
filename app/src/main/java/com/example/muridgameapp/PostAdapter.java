package com.example.muridgameapp;

import android.text.Html;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide; // INI IMPORT YANG KURANG
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private final List<Post> postList; // Menggunakan final

    public PostAdapter(List<Post> postList) {
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.textTitle.setText(post.title.rendered);

        // Memuat gambar dengan Glide
        if (post.embedded != null && post.embedded.featuredMedia != null && !post.embedded.featuredMedia.isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(post.embedded.featuredMedia.get(0).sourceUrl)
                    .into(holder.imgThumbnail);
        }
        if (post.excerpt != null) {
            holder.textExcerpt.setText(Html.fromHtml(post.excerpt.rendered, Html.FROM_HTML_MODE_LEGACY));
        }
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetailActivity.class);
            // Kita kirim URL artikelnya ke halaman detail
            intent.putExtra("post_url", postList.get(position).link);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textExcerpt;
        ImageView imgThumbnail;

        public PostViewHolder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textExcerpt = itemView.findViewById(R.id.textExcerpt); // Pastikan ID sama
            imgThumbnail = itemView.findViewById(R.id.imgThumbnail);
        }
    }
}