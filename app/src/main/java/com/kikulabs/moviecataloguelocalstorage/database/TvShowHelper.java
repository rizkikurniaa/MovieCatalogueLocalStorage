package com.kikulabs.moviecataloguelocalstorage.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.kikulabs.moviecataloguelocalstorage.model.MoviesAndTvData;

import java.util.ArrayList;

import static com.kikulabs.moviecataloguelocalstorage.database.DatabaseContract.TABLE_TVSHOW;

public class TvShowHelper {
    private static final String DATABASE_TABLE = TABLE_TVSHOW;
    private static DatabaseHelper dataBaseHelper;
    private static TvShowHelper INSTANCE;
    private static SQLiteDatabase database;

    private TvShowHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static TvShowHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TvShowHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();
        if (database.isOpen())
            database.close();
    }

    public ArrayList<MoviesAndTvData> getAllTvShow() {
        ArrayList<MoviesAndTvData> items = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null, null, null, null, null, DatabaseContract.TvShowColumns._ID, null);
        cursor.moveToFirst();
        MoviesAndTvData moviesAndTvData;
        if (cursor.getCount() > 0) {
            do {
                moviesAndTvData = new MoviesAndTvData();
                moviesAndTvData.setId(cursor.getInt(0));
                moviesAndTvData.setTitle(String.valueOf(cursor.getString(1)));
                moviesAndTvData.setPoster(String.valueOf(cursor.getString(2)));
                moviesAndTvData.setBackdrop(String.valueOf(cursor.getString(3)));
                moviesAndTvData.setReleaseDate(String.valueOf(cursor.getString(4)));
                moviesAndTvData.setVoteAverage(String.valueOf(cursor.getString(5)));
                moviesAndTvData.setLanguage(String.valueOf(cursor.getString(6)));
                moviesAndTvData.setOverview(String.valueOf(cursor.getString(7)));
                items.add(moviesAndTvData);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return items;
    }

    public Boolean queryByTitle(String title) {
        String queryCheckRecord = "SELECT * FROM " + DATABASE_TABLE + " WHERE " + DatabaseContract.TvShowColumns.TITLE + " " + " LIKE " + "'" + title + "'";
        Cursor cursor = database.rawQuery(queryCheckRecord, null);
        cursor.moveToFirst();
        Log.d("cursor", String.valueOf(cursor.getCount()));
        if (cursor.getCount() > 0) {

            return true;
        } else if (cursor.getCount() == 0) {

            return false;
        }
        return false;
    }

    public long addTvShow(MoviesAndTvData moviesAndTvData) {
        ContentValues args = new ContentValues();
        args.put(DatabaseContract.TvShowColumns.TITLE, moviesAndTvData.getTitle());
        args.put(DatabaseContract.TvShowColumns.POSTER, moviesAndTvData.getPoster());
        args.put(DatabaseContract.TvShowColumns.BG, moviesAndTvData.getBackdrop());
        args.put(DatabaseContract.TvShowColumns.RELEASEDATE, moviesAndTvData.getReleaseDate());
        args.put(DatabaseContract.TvShowColumns.VOTEAVERAGE, moviesAndTvData.getVoteAverage());
        args.put(DatabaseContract.TvShowColumns.LANGUAGE, moviesAndTvData.getLanguage());
        args.put(DatabaseContract.TvShowColumns.OVERVIEW, moviesAndTvData.getOverview());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public int deleteTvShow(String title) {
        return database.delete(TABLE_TVSHOW, DatabaseContract.TvShowColumns.TITLE + " = " + "'" + title + "'", null);
    }
}
