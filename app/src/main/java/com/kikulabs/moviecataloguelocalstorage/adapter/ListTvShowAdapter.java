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
import com.kikulabs.moviecataloguelocalstorage.activity.DetailTvShowActivity;
import com.kikulabs.moviecataloguelocalstorage.model.MoviesAndTvData;

import java.util.ArrayList;

public class ListTvShowAdapter extends RecyclerView.Adapter<ListTvShowAdapter.ListViewHolder> {
    private ArrayList<MoviesAndTvData> tvShowData = new ArrayList<>();
    private Context context;

    public ListTvShowAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<MoviesAndTvData> items) {
        tvShowData.clear();
        tvShowData.addAll(items);
        notifyDataSetChanged();
    }

    public ArrayList<MoviesAndTvData> getListTvShow() {
        return tvShowData;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_tvshow, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, final int position) {
        holder.bind(tvShowData.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailTvShowActivity.class);
                intent.putExtra(DetailTvShowActivity.EXTRA_FILM, tvShowData.get(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tvShowData.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvTitle, tvDate, tvRate;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.tvshow_poster);
            tvTitle = itemView.findViewById(R.id.tvshow_title);
            tvDate = itemView.findViewById(R.id.tvshow_release_date);
            tvRate = itemView.findViewById(R.id.tvshow_vote);
        }

        void bind(MoviesAndTvData tvShowData) {
            String url_image = "https://image.tmdb.org/t/p/w185" + tvShowData.getPoster();
            tvTitle.setText(tvShowData.getTitle());
            tvDate.setText(tvShowData.getReleaseDate());
            tvRate.setText(tvShowData.getVoteAverage());

            Glide.with(itemView.getContext())
                    .load(url_image)
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .into(imgPoster);

        }
    }
}
