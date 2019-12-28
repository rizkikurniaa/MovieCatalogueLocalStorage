package com.kikulabs.moviecataloguelocalstorage.activity;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.kikulabs.moviecataloguelocalstorage.R;
import com.kikulabs.moviecataloguelocalstorage.database.MoviesHelper;
import com.kikulabs.moviecataloguelocalstorage.model.MoviesAndTvData;
import com.kikulabs.moviecataloguelocalstorage.stackwidget.ImageBannerWidget;

public class DetailMoviesActivity extends AppCompatActivity {
    public static final String EXTRA_FILM = "extra_film";
    private ConstraintLayout cl;
    private Toolbar toolbar;
    private TextView tvTitle, tvRelease, tvRating, tvLang, tvOverview;
    private ImageView ivPoster, ivBg, fav;
    private MoviesHelper moviesHelper;
    private Boolean add = true;
    private Boolean del = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movies);
        toolbar = findViewById(R.id.toolbar);
        tvTitle = findViewById(R.id.tv_title);
        tvRelease = findViewById(R.id.tv_release_date);
        tvRating = findViewById(R.id.tv_ratingscore);
        tvLang = findViewById(R.id.tv_language);
        tvOverview = findViewById(R.id.tv_overview);
        ivPoster = findViewById(R.id.iv_film);
        ivBg = findViewById(R.id.iv_bgmovies);
        cl = findViewById(R.id.cl);
        fav = findViewById(R.id.ivFav);
        CollapsingToolbarLayout collapse = findViewById(R.id.collapse_toolbar);

        collapse.setExpandedTitleColor(Color.argb(0, 0, 0, 0));
        collapse.setContentScrimColor(getResources().getColor(R.color.colorPrimary));

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        moviesHelper = MoviesHelper.getInstance(this);
        moviesHelper.open();

        MoviesAndTvData selectedFilm = getIntent().getParcelableExtra(EXTRA_FILM);

        if (selectedFilm != null) {

            String moviesTitle = selectedFilm.getTitle();
            String url_image = "https://image.tmdb.org/t/p/w185" + selectedFilm.getPoster();
            String url_bg = "https://image.tmdb.org/t/p/w500" + selectedFilm.getBackdrop();
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

            Glide.with(DetailMoviesActivity.this)
                    .load(url_bg)
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .into(ivBg);

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
                updateFavoriteMoviesStackWidget();
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
                updateFavoriteMoviesStackWidget();
                Intent intent = new Intent(DetailMoviesActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(DetailMoviesActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateFavoriteMoviesStackWidget() {
        Context context = getApplicationContext();
        AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);
        ComponentName componentName = new ComponentName(context, ImageBannerWidget.class);
        int[] idAppWidget = widgetManager.getAppWidgetIds(componentName);
        widgetManager.notifyAppWidgetViewDataChanged(idAppWidget, R.id.stack_view);
    }
}
