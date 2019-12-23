package com.kikulabs.moviecataloguelocalstorage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.kikulabs.moviecataloguelocalstorage.R;
import com.kikulabs.moviecataloguelocalstorage.database.MoviesHelper;
import com.kikulabs.moviecataloguelocalstorage.model.MoviesAndTvData;

public class DetailMoviesActivity extends AppCompatActivity {
    public static final String EXTRA_FILM = "extra_film";
    private ProgressBar progressBar;
    private ConstraintLayout cl;
    private TextView tvTitle, tvRelease, tvRating, tvLang, tvOverview;
    private ImageView ivPoster;
    private Handler handler;
    private ImageView fav;
    private MoviesHelper moviesHelper;
    private Boolean add = true;
    private Boolean del = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movies);

        moviesHelper = MoviesHelper.getInstance(this);
        moviesHelper.open();

        tvTitle = findViewById(R.id.tv_title);
        tvRelease = findViewById(R.id.tv_release_date);
        tvRating = findViewById(R.id.tv_ratingscore);
        tvLang = findViewById(R.id.tv_language);
        tvOverview = findViewById(R.id.tv_overview);
        ivPoster = findViewById(R.id.iv_film);
        progressBar = findViewById(R.id.progressBar);
        cl = findViewById(R.id.cl);
        fav = findViewById(R.id.ivFav);

        cl.setVisibility(View.GONE);

        handler = new Handler();

        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }

                handler.post(new Runnable() {
                    public void run() {

                        MoviesAndTvData selectedFilm = getIntent().getParcelableExtra(EXTRA_FILM);

                        if (selectedFilm != null) {

                            String moviesTitle = selectedFilm.getTitle();
                            String url_image = "https://image.tmdb.org/t/p/w185" + selectedFilm.getPoster();
                            tvTitle.setText(selectedFilm.getTitle());
                            tvRelease.setText(selectedFilm.getReleaseDate());
                            tvRating.setText(selectedFilm.getVoteAverage());
                            tvLang.setText(selectedFilm.getLanguage());
                            tvOverview.setText(selectedFilm.getOverview());

                            Glide.with(DetailMoviesActivity.this)
                                    .load(url_image)
                                    .placeholder(R.color.colorAccent)
                                    .dontAnimate()
                                    .into(ivPoster);

                            cl.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);

                            Log.d("check", "statusMovies: " + moviesTitle + " : " + moviesHelper.queryByTitle(moviesTitle));
                            if (moviesHelper.queryByTitle(moviesTitle)) {
                                del = true;
                                add = false;
                                fav.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite));
                            } else if (!moviesHelper.queryByTitle(moviesTitle)) {
                                add = true;
                                del = false;
                                fav.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_border));
                            }

                        }
                    }
                });
            }
        }).start();

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favClick();
            }
        });

    }

    private void favClick() {
        MoviesAndTvData moviesAndTvData = getIntent().getParcelableExtra(EXTRA_FILM);
        if (add) {
            moviesAndTvData.setTitle(moviesAndTvData.getTitle());
            moviesAndTvData.setPoster(moviesAndTvData.getPoster());
            moviesAndTvData.setReleaseDate(moviesAndTvData.getReleaseDate());
            moviesAndTvData.setVoteAverage(moviesAndTvData.getVoteAverage());
            moviesAndTvData.setLanguage(moviesAndTvData.getLanguage());
            moviesAndTvData.setOverview(moviesAndTvData.getOverview());
            long result = moviesHelper.addMovies(moviesAndTvData);

            if (result > 0) {
                add = false;
                del = true;
                Toast.makeText(DetailMoviesActivity.this, R.string.success, Toast.LENGTH_SHORT).show();
                fav.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite));
            } else {
                Toast.makeText(DetailMoviesActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
            }
        } else if (del) {
            long result = moviesHelper.deleteMovies(moviesAndTvData.getTitle());
            if (result > 0) {
                del = false;
                add = true;
                Toast.makeText(DetailMoviesActivity.this, R.string.removed, Toast.LENGTH_SHORT).show();
                fav.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border));
                Intent intent = new Intent(DetailMoviesActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(DetailMoviesActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
