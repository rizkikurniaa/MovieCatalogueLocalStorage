package com.kikulabs.moviecataloguelocalstorage.activity;

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
import com.kikulabs.moviecataloguelocalstorage.database.TvShowHelper;
import com.kikulabs.moviecataloguelocalstorage.model.MoviesAndTvData;

public class DetailTvShowActivity extends AppCompatActivity {
    public static final String EXTRA_FILM = "extra_film";
    private ConstraintLayout cl;
    private Toolbar toolbar;
    private TextView tvTitle, tvRelease, tvRating, tvLang, tvOverview;
    private ImageView ivPoster, ivBg, fav;
    private TvShowHelper tvShowHelper;
    private Boolean add = true;
    private Boolean del = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_show);

        toolbar = findViewById(R.id.toolbar);
        tvTitle = findViewById(R.id.tv_title);
        tvRelease = findViewById(R.id.tv_release_date);
        tvRating = findViewById(R.id.tv_ratingscore);
        tvLang = findViewById(R.id.tv_language);
        tvOverview = findViewById(R.id.tv_overview);
        ivPoster = findViewById(R.id.iv_film);
        ivBg = findViewById(R.id.iv_bgtv);
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

        tvShowHelper = TvShowHelper.getInstance(this);
        tvShowHelper.open();


        MoviesAndTvData selectedFilm = getIntent().getParcelableExtra(EXTRA_FILM);

        if (selectedFilm != null) {

            String tvShowTitle = selectedFilm.getTitle();
            String url_image = "https://image.tmdb.org/t/p/w185" + selectedFilm.getPoster();
            String url_bg = "https://image.tmdb.org/t/p/w500" + selectedFilm.getBackdrop();
            tvTitle.setText(selectedFilm.getTitle());
            tvRelease.setText(selectedFilm.getReleaseDate());
            tvRating.setText(selectedFilm.getVoteAverage());
            tvLang.setText(selectedFilm.getLanguage());
            tvOverview.setText(selectedFilm.getOverview());

            Glide.with(DetailTvShowActivity.this)
                    .load(url_image)
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .into(ivPoster);

            Glide.with(DetailTvShowActivity.this)
                    .load(url_bg)
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .into(ivBg);


            Log.d("check", "statusTvShow: " + tvShowTitle + " : " + tvShowHelper.queryByTitle(tvShowTitle));
            if (tvShowHelper.queryByTitle(tvShowTitle)) {
                del = true;
                add = false;
                fav.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite));
            } else if (!tvShowHelper.queryByTitle(tvShowTitle)) {
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
            long result = tvShowHelper.addTvShow(moviesAndTvData);

            if (result > 0) {
                add = false;
                del = true;
                Toast.makeText(this, R.string.success, Toast.LENGTH_SHORT).show();
                fav.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite));
            } else {
                Toast.makeText(this, R.string.failed, Toast.LENGTH_SHORT).show();
            }
        } else if (del) {
            long result = tvShowHelper.deleteTvShow(moviesAndTvData.getTitle());
            if (result > 0) {
                del = false;
                add = true;
                Toast.makeText(this, R.string.removed, Toast.LENGTH_SHORT).show();
                fav.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border));
                Intent intent = new Intent(DetailTvShowActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, R.string.failed, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
