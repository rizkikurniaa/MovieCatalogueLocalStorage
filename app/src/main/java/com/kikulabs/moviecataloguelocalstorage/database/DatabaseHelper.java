package com.kikulabs.moviecataloguelocalstorage.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "dbmoviestvshow";
    private static final int DATABASE_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",

            DatabaseContract.TABLE_MOVIES,
            DatabaseContract.MoviesColumns._ID,
            DatabaseContract.MoviesColumns.TITLE,
            DatabaseContract.MoviesColumns.POSTER,
            DatabaseContract.MoviesColumns.RELEASEDATE,
            DatabaseContract.MoviesColumns.VOTEAVERAGE,
            DatabaseContract.MoviesColumns.LANGUAGE,
            DatabaseContract.MoviesColumns.OVERVIEW
    );

    private static final String SQL_CREATE_TABLE_TVSHOW = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",

            DatabaseContract.TABLE_TVSHOW,
            DatabaseContract.MoviesColumns._ID,
            DatabaseContract.MoviesColumns.TITLE,
            DatabaseContract.MoviesColumns.POSTER,
            DatabaseContract.MoviesColumns.RELEASEDATE,
            DatabaseContract.MoviesColumns.VOTEAVERAGE,
            DatabaseContract.MoviesColumns.LANGUAGE,
            DatabaseContract.MoviesColumns.OVERVIEW
    );

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
        db.execSQL(SQL_CREATE_TABLE_TVSHOW);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_MOVIES);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_TVSHOW);
        onCreate(db);
    }
}
