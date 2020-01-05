package com.kikulabs.favmov.database;

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

        // untuk membuat URI content://com.kikulabs.moviecataloguelocalstorage/fav
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_MOVIES)
                .build();

    }
}
