package com.kikulabs.moviecataloguelocalstorage.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kikulabs.moviecataloguelocalstorage.R;
import com.kikulabs.moviecataloguelocalstorage.activity.DetailMoviesActivity;
import com.kikulabs.moviecataloguelocalstorage.model.MoviesAndTvData;

import java.util.ArrayList;

public class ListMoviesAdapter extends RecyclerView.Adapter<ListMoviesAdapter.ListViewHolder> {
    private ArrayList<MoviesAndTvData> moviesData = new ArrayList<>();
    private Context context;

    public ListMoviesAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<MoviesAndTvData> items) {
        moviesData.clear();
        moviesData.addAll(items);
        notifyDataSetChanged();
    }


    public ArrayList<MoviesAndTvData> getListMovies() {
        return moviesData;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_movie, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, final int position) {
        holder.bind(moviesData.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailMoviesActivity.class);
                intent.putExtra(DetailMoviesActivity.EXTRA_FILM, moviesData.get(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesData.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvTitle, tvDate, tvRate;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.movies_poster);
            tvTitle = itemView.findViewById(R.id.movies_title);
            tvDate = itemView.findViewById(R.id.release_date);
            tvRate = itemView.findViewById(R.id.movies_vote);
        }

        void bind(MoviesAndTvData moviesAndTvData) {
            String url_image = "https://image.tmdb.org/t/p/w185" + moviesAndTvData.getPoster();
            tvTitle.setText(moviesAndTvData.getTitle());
            tvDate.setText(moviesAndTvData.getReleaseDate());
            tvRate.setText(moviesAndTvData.getVoteAverage());

            Glide.with(itemView.getContext())
                    .load(url_image)
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .into(imgPoster);

        }
    }
}