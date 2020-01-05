package com.kikulabs.moviecataloguelocalstorage.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kikulabs.moviecataloguelocalstorage.database.MoviesHelper;

import static com.kikulabs.moviecataloguelocalstorage.database.DatabaseContract.AUTHORITY;
import static com.kikulabs.moviecataloguelocalstorage.database.DatabaseContract.MoviesColumns.CONTENT_URI;
import static com.kikulabs.moviecataloguelocalstorage.database.DatabaseContract.TABLE_MOVIES;

public class MoviesFavoriteProvider extends ContentProvider {
    private static final int MOVIES = 1;
    private static final int MOVIES_ID = 2;
    private MoviesHelper moviesHelper;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, TABLE_MOVIES, MOVIES);
        sUriMatcher.addURI(AUTHORITY, TABLE_MOVIES + "/#", MOVIES_ID);
    }


    public MoviesFavoriteProvider() {
    }

    @Override
    public boolean onCreate() {
        moviesHelper = MoviesHelper.getInstance(getContext());
        moviesHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIES:
                cursor = moviesHelper.getAllMoviesProvider();
                break;
            case MOVIES_ID:
                cursor = moviesHelper.getByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long added;
        switch (sUriMatcher.match(uri)) {
            case MOVIES:
                added = moviesHelper.insertProvider(contentValues);
                break;
            default:
                added = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, null);
        return Uri.parse(CONTENT_URI + "/" + added);
    }
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        int updated;
        switch (sUriMatcher.match(uri)) {
            case MOVIES_ID:
                updated = moviesHelper.updateProvider(uri.getLastPathSegment(), contentValues);
                break;
            default:
                updated = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, null);
        return updated;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case MOVIES_ID:
                deleted = moviesHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, null);
        return deleted;
    }
}
