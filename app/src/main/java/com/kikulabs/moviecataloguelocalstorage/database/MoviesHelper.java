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

import static com.kikulabs.moviecataloguelocalstorage.database.DatabaseContract.MoviesColumns.ID;
import static com.kikulabs.moviecataloguelocalstorage.database.DatabaseContract.TABLE_MOVIES;

public class MoviesHelper {
    private static final String DATABASE_TABLE = TABLE_MOVIES;
    private static DatabaseHelper dataBaseHelper;
    private static MoviesHelper INSTANCE;
    private static SQLiteDatabase database;

    public MoviesHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static MoviesHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MoviesHelper(context);
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

    public ArrayList<MoviesAndTvData> getAllMovies() {
        ArrayList<MoviesAndTvData> items = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null, null, null, null, null, DatabaseContract.MoviesColumns._ID, null);
        cursor.moveToFirst();
        MoviesAndTvData mItem;
        if (cursor.getCount() > 0) {
            do {
                mItem = new MoviesAndTvData();
                mItem.setId(cursor.getInt(0));
                mItem.setTitle(String.valueOf(cursor.getString(1)));
                mItem.setPoster(String.valueOf(cursor.getString(2)));
                mItem.setBackdrop(String.valueOf(cursor.getString(3)));
                mItem.setReleaseDate(String.valueOf(cursor.getString(4)));
                mItem.setVoteAverage(String.valueOf(cursor.getString(5)));
                mItem.setLanguage(String.valueOf(cursor.getString(6)));
                mItem.setOverview(String.valueOf(cursor.getString(7)));
                items.add(mItem);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return items;
    }

    public Boolean queryByTitle(String title) {
        String queryCheckRecord = "SELECT * FROM " + DATABASE_TABLE + " WHERE " + DatabaseContract.MoviesColumns.TITLE + " " + " LIKE " + "'" + title + "'";
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

    public Cursor getByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null,
                ID + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null);
    }

    public Cursor getAllMoviesProvider() {
        return database.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                ID + " ASC");
    }

    public long addMovies(MoviesAndTvData moviesAndTvData) {
        ContentValues args = new ContentValues();
        args.put(DatabaseContract.MoviesColumns.TITLE, moviesAndTvData.getTitle());
        args.put(DatabaseContract.MoviesColumns.POSTER, moviesAndTvData.getPoster());
        args.put(DatabaseContract.MoviesColumns.BG, moviesAndTvData.getBackdrop());
        args.put(DatabaseContract.MoviesColumns.RELEASEDATE, moviesAndTvData.getReleaseDate());
        args.put(DatabaseContract.MoviesColumns.VOTEAVERAGE, moviesAndTvData.getVoteAverage());
        args.put(DatabaseContract.MoviesColumns.LANGUAGE, moviesAndTvData.getLanguage());
        args.put(DatabaseContract.MoviesColumns.OVERVIEW, moviesAndTvData.getOverview());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public int deleteMovies(String title) {
        return database.delete(TABLE_MOVIES, DatabaseContract.MoviesColumns.TITLE + " = " + "'" + title + "'", null);
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, ID + " = ?", new String[]{id});
    }
}
