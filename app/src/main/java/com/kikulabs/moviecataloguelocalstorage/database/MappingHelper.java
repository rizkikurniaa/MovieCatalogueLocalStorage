package com.kikulabs.moviecataloguelocalstorage.database;

import android.database.Cursor;

import com.kikulabs.moviecataloguelocalstorage.model.MoviesAndTvData;

import java.util.ArrayList;

import static com.kikulabs.moviecataloguelocalstorage.database.DatabaseContract.MoviesColumns.BG;
import static com.kikulabs.moviecataloguelocalstorage.database.DatabaseContract.MoviesColumns.ID;
import static com.kikulabs.moviecataloguelocalstorage.database.DatabaseContract.MoviesColumns.LANGUAGE;
import static com.kikulabs.moviecataloguelocalstorage.database.DatabaseContract.MoviesColumns.OVERVIEW;
import static com.kikulabs.moviecataloguelocalstorage.database.DatabaseContract.MoviesColumns.POSTER;
import static com.kikulabs.moviecataloguelocalstorage.database.DatabaseContract.MoviesColumns.RELEASEDATE;
import static com.kikulabs.moviecataloguelocalstorage.database.DatabaseContract.MoviesColumns.TITLE;
import static com.kikulabs.moviecataloguelocalstorage.database.DatabaseContract.MoviesColumns.VOTEAVERAGE;

public class MappingHelper {

    public static ArrayList<MoviesAndTvData> mapCursorToArrayList(Cursor moviesCursor) {
        ArrayList<MoviesAndTvData> modelArrayList = new ArrayList<>();
        while (moviesCursor.moveToNext()) {
            int id = moviesCursor.getInt(moviesCursor.getColumnIndexOrThrow(ID));
            String title = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(TITLE));
            String poster = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(POSTER));
            String bg = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(BG));
            String release_date = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(RELEASEDATE));
            String vote_average = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(VOTEAVERAGE));
            String language = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(LANGUAGE));
            String overview = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(OVERVIEW));
            modelArrayList.add(new MoviesAndTvData(id, title, poster, bg, release_date, vote_average, language, overview));
        }
        return modelArrayList;
    }
}
