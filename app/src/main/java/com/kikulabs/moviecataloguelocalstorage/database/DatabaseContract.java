package com.kikulabs.moviecataloguelocalstorage.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String AUTHORITY = "com.kikulabs.moviecataloguelocalstorage";
    private static final String SCHEME = "content";
    public static String TABLE_MOVIES = "movies";

    public static final class MoviesColumns implements BaseColumns {

        public static final String ID = "id";
        public static String TITLE = "title";
        public static String POSTER = "poster";
        public static String BG = "bg";
        public static String RELEASEDATE = "releaseDate";
        public static String VOTEAVERAGE = "voteAverage";
        public static String LANGUAGE = "language";
        public static String OVERVIEW = "overview";

        // untuk membuat URI content://com.kikulabs.moviecataloguelocalstorage
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_MOVIES)
                .build();

    }


    static String TABLE_TVSHOW = "tvshow";

    static final class TvShowColumns implements BaseColumns {
        public static final String ID = "id";
        static String TITLE = "title";
        static String POSTER = "poster";
        static String BG = "bg";
        static String RELEASEDATE = "releaseDate";
        static String VOTEAVERAGE = "voteAverage";
        static String LANGUAGE = "language";
        static String OVERVIEW = "overview";
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }
}
