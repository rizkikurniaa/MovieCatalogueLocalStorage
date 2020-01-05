package com.kikulabs.moviecataloguelocalstorage.fragment;


import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
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

import static com.kikulabs.moviecataloguelocalstorage.database.DatabaseContract.MoviesColumns.CONTENT_URI;
import static com.kikulabs.moviecataloguelocalstorage.database.MappingHelper.mapCursorToArrayList;

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
    private ArrayList<MoviesAndTvData> listMovies;

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

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        DataObserver myObserver = new DataObserver(handler, getContext());

        if (getContext() != null) {
            getContext().getContentResolver().registerContentObserver(CONTENT_URI, true, myObserver);
        }

        if (savedInstanceState == null) {
            new LoadMoviesAsync(getContext(), this).execute();
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

    @Override
    public void postExecute(Cursor cursor) {
        progressBar.setVisibility(View.INVISIBLE);
        listMovies = mapCursorToArrayList(cursor);
        if (listMovies.size() > 0) {
            adapter.setData(listMovies);
            tvNoData.setVisibility(View.GONE);
        } else {
            adapter.setData(new ArrayList<MoviesAndTvData>());
            tvNoData.setVisibility(View.VISIBLE);
        }
    }


    private static class LoadMoviesAsync extends AsyncTask<Void, Void, Cursor> {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadCallback> weakCallback;

        private LoadMoviesAsync(Context context, LoadCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            Context context = weakContext.get();
            return context.getContentResolver().query(CONTENT_URI,
                    null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            weakCallback.get().postExecute(cursor);
        }
    }

    public static class DataObserver extends ContentObserver {
        final Context context;
        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new LoadMoviesAsync(context, (LoadCallback) context).execute();
        }
    }

}
