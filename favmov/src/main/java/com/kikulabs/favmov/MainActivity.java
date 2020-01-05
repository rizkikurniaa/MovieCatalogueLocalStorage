package com.kikulabs.favmov;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kikulabs.favmov.adapter.ListMoviesAdapter;
import com.kikulabs.favmov.database.LoadCallback;
import com.kikulabs.favmov.model.MoviesAndTvData;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.kikulabs.favmov.database.DatabaseContract.MoviesColumns.CONTENT_URI;
import static com.kikulabs.favmov.database.MappingHelper.mapCursorToArrayList;

public class MainActivity extends AppCompatActivity implements LoadCallback {
    private ListMoviesAdapter adapter;
    private RecyclerView recyclerMovieFav;
    private ArrayList<MoviesAndTvData> listMovies;
    private static final String EXTRA_STATE = "EXTRA_STATE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerMovieFav = findViewById(R.id.recyclerMovieFav);
        recyclerMovieFav.setHasFixedSize(true);
        recyclerMovieFav.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        adapter = new ListMoviesAdapter(getApplicationContext());
        adapter.notifyDataSetChanged();
        recyclerMovieFav.setAdapter(adapter);

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        DataObserver myObserver = new DataObserver(handler, getApplicationContext());
        getContentResolver().registerContentObserver(CONTENT_URI, true, myObserver);


        if (savedInstanceState == null) {
            new LoadMoviesAsync(getApplicationContext(), this).execute();
        } else {
            ArrayList<MoviesAndTvData> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setData(list);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListMovies());
    }


    @Override
    public void preExecute() {

    }

    @Override
    public void postExecute(Cursor cursor) {
        listMovies = mapCursorToArrayList(cursor);
        if (listMovies.size() > 0) {
            adapter.setData(listMovies);
        } else {
            adapter.setData(new ArrayList<MoviesAndTvData>());
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
