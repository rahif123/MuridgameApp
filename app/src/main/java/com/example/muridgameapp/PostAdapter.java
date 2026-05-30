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
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private List<Post> postList;
    private List<Post> postListFull;

    public PostAdapter(List<Post> postList) {
        this.postList = postList;
        this.postListFull = new ArrayList<>(postList);
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

        // Menampilkan Kategori
        if (post.embedded != null && post.embedded.terms != null && !post.embedded.terms.isEmpty()) {
            List<Post.Term> categories = post.embedded.terms.get(0);
            if (!categories.isEmpty()) {
                holder.textCategory.setText(categories.get(0).name);
                holder.textCategory.setVisibility(View.VISIBLE);
            } else {
                holder.textCategory.setVisibility(View.GONE);
            }
        } else {
            holder.textCategory.setVisibility(View.GONE);
        }

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
            intent.putExtra("post_url", post.link);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public void updateData(List<Post> posts) {
        this.postList = posts;
        this.postListFull = new ArrayList<>(posts);
        notifyDataSetChanged();
    }

    public void filter(String text) {
        postList = new ArrayList<>();
        if (text == null || text.isEmpty()) {
            postList.addAll(postListFull);
        } else {
            String query = text.toLowerCase().trim();
            for (Post post : postListFull) {
                if (post.title.rendered.toLowerCase().contains(query)) {
                    postList.add(post);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textExcerpt, textCategory;
        ImageView imgThumbnail;

        public PostViewHolder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textExcerpt = itemView.findViewById(R.id.textExcerpt);
            textCategory = itemView.findViewById(R.id.textCategory);
            imgThumbnail = itemView.findViewById(R.id.imgThumbnail);
        }
    }
}