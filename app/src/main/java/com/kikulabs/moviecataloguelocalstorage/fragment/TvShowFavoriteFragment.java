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
import com.kikulabs.moviecataloguelocalstorage.adapter.ListTvShowAdapter;
import com.kikulabs.moviecataloguelocalstorage.database.LoadCallback;
import com.kikulabs.moviecataloguelocalstorage.database.TvShowHelper;
import com.kikulabs.moviecataloguelocalstorage.model.MoviesAndTvData;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFavoriteFragment extends Fragment implements LoadCallback {
    private ListTvShowAdapter adapter;
    private ProgressBar progressBar;
    private RecyclerView recyclerTvShowFav;
    private TextView tvNoData;
    private TvShowHelper tvShowHelper;
    private static final String EXTRA_STATE = "EXTRA_STATE";

    public TvShowFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv_show_favorite, container, false);

        tvNoData = view.findViewById(R.id.tv_nodata);
        recyclerTvShowFav = view.findViewById(R.id.recyclerTvShow);
        recyclerTvShowFav.setHasFixedSize(true);
        recyclerTvShowFav.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBar = view.findViewById(R.id.progressBar);

        adapter = new ListTvShowAdapter(getContext());
        adapter.notifyDataSetChanged();
        recyclerTvShowFav.setAdapter(adapter);

        tvShowHelper = TvShowHelper.getInstance(getContext());
        tvShowHelper.open();

        if (savedInstanceState == null) {
            new LoadTvShowAsync(tvShowHelper, this).execute();
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
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListTvShow());
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

    private static class LoadTvShowAsync extends AsyncTask<Void, Void, ArrayList<MoviesAndTvData>> {
        private final WeakReference<TvShowHelper> weakTvShowHelper;
        private final WeakReference<LoadCallback> weakCallback;

        private LoadTvShowAsync(TvShowHelper tvShowHelper, LoadCallback callback) {
            weakTvShowHelper = new WeakReference<>(tvShowHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<MoviesAndTvData> doInBackground(Void... voids) {
            return weakTvShowHelper.get().getAllTvShow();
        }

        @Override
        protected void onPostExecute(ArrayList<MoviesAndTvData> moviesAndTvData) {
            super.onPostExecute(moviesAndTvData);
            weakCallback.get().postExecute(moviesAndTvData);
        }
    }

}
