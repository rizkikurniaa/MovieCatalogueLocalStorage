package com.kikulabs.moviecataloguelocalstorage.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kikulabs.moviecataloguelocalstorage.R;
import com.kikulabs.moviecataloguelocalstorage.adapter.ListMoviesAdapter;
import com.kikulabs.moviecataloguelocalstorage.database.LoadCallback;
import com.kikulabs.moviecataloguelocalstorage.database.MoviesHelper;
import com.kikulabs.moviecataloguelocalstorage.model.MoviesAndTvData;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFavoriteFragment extends Fragment implements LoadCallback {
    private ListMoviesAdapter adapter;
    private ProgressBar progressBar;
    private RecyclerView recyclerMovieFav;
    private TextView tvNoData;
    private MoviesHelper moviesHelper;
    private static final String EXTRA_STATE = "EXTRA_STATE";

    public MoviesFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies_favorite, container, false);

        tvNoData = view.findViewById(R.id.tv_nodata);
        recyclerMovieFav = view.findViewById(R.id.recyclerMovieFav);
        recyclerMovieFav.setHasFixedSize(true);
        recyclerMovieFav.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBar = view.findViewById(R.id.progressBar);

        adapter = new ListMoviesAdapter(getContext());
        adapter.notifyDataSetChanged();
        recyclerMovieFav.setAdapter(adapter);

        moviesHelper = MoviesHelper.getInstance(getContext());
        moviesHelper.open();

        if (savedInstanceState == null) {
            new LoadMoviesAsync(moviesHelper, this).execute();
        } else {
            ArrayList<MoviesAndTvData> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setData(list);
            }
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListMovies());
    }

    public void preExecute() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void postExecute(ArrayList<MoviesAndTvData> moviesAndTvData) {
        progressBar.setVisibility(View.INVISIBLE);
        if (moviesAndTvData.size() > 0) {
            adapter.setData(moviesAndTvData);
            tvNoData.setVisibility(View.GONE);
        } else {
            adapter.setData(new ArrayList<MoviesAndTvData>());
            tvNoData.setVisibility(View.VISIBLE);
        }
    }

    private static class LoadMoviesAsync extends AsyncTask<Void, Void, ArrayList<MoviesAndTvData>> {
        private final WeakReference<MoviesHelper> weakMoviesHelper;
        private final WeakReference<LoadCallback> weakCallback;

        private LoadMoviesAsync(MoviesHelper moviesHelper, LoadCallback callback) {
            weakMoviesHelper = new WeakReference<>(moviesHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<MoviesAndTvData> doInBackground(Void... voids) {
            return weakMoviesHelper.get().getAllMovies();
        }

        @Override
        protected void onPostExecute(ArrayList<MoviesAndTvData> moviesAndTvData) {
            super.onPostExecute(moviesAndTvData);
            weakCallback.get().postExecute(moviesAndTvData);
        }
    }

}
