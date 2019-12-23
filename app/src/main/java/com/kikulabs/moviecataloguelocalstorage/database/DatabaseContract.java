package com.kikulabs.moviecataloguelocalstorage.database;

import android.provider.BaseColumns;

public class DatabaseContract {
    static String TABLE_MOVIES = "movies";

    static final class MoviesColumns implements BaseColumns {
        static String TITLE = "title";
        static String POSTER = "poster";
        static String RELEASEDATE = "releaseDate";
        static String VOTEAVERAGE = "voteAverage";
        static String LANGUAGE = "language";
        static String OVERVIEW = "overview";
    }

    static String TABLE_TVSHOW = "tvshow";

    static final class TvShowColumns implements BaseColumns {
        static String TITLE = "title";
        static String POSTER = "poster";
        static String RELEASEDATE = "releaseDate";
        static String VOTEAVERAGE = "voteAverage";
        static String LANGUAGE = "language";
        static String OVERVIEW = "overview";
    }
}
