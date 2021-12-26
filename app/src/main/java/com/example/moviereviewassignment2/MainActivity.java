/**
 * USED LIBRARIES
 * Gson
 * pojo - https://www.jsonschema2pojo.org/
 * okHttp
 * Glide - image loading
 * **/

package com.example.moviereviewassignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    MovieModel decode;
    OkHttpClient client = new OkHttpClient();

    String run() throws IOException {
        Request request = new Request.Builder().url("https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&page=1&api_key=3fa9058382669f72dcb18fb405b7a831&gt").build();

        try (Response response = client.newCall(request).execute()) {
            return Objects.requireNonNull(response.body()).string();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewRV);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(new Adapter());

        try {
            String data = run();
            decode = new Gson().fromJson(data, MovieModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class Adapter extends RecyclerView.Adapter<ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.recycleview_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.textView.setText(decode.getResults().get(position).getTitle());
            holder.rating_tv.setText(String.valueOf(decode.getResults().get(position).getVoteAverage()));

            String url = "https://image.tmdb.org/t/p/w500" + decode.getResults().get(position).getPosterPath();
            Glide.with(getApplicationContext())
                    .load(url)
                    .centerCrop()
                    .into(holder.imageView);

            holder.imageView.setOnClickListener(view -> {
                Intent intent = new Intent(MainActivity.this, MovieDetails.class);
                int k = holder.getAdapterPosition();
                intent.putExtra("id", String.valueOf(decode.getResults().get(k).getId()));
                intent.putExtra("title", decode.getResults().get(k).getTitle());
                intent.putExtra("ov", decode.getResults().get(k).getOverview());
                intent.putExtra("posterPath", decode.getResults().get(k).getPosterPath());
                intent.putExtra("backdropPath", decode.getResults().get(k).getBackdropPath());
                intent.putExtra("rating", String.valueOf(decode.getResults().get(k).getVoteAverage()));
                intent.putExtra("releaseDate", decode.getResults().get(k).getReleaseDate());
                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return decode.getResults().size();
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView, rating_tv;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.title_tv);
            imageView = itemView.findViewById(R.id.poster_iv);
            rating_tv = itemView.findViewById(R.id.rating_tv);
        }
    }
}