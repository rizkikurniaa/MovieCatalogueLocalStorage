package com.kikulabs.moviecataloguelocalstorage.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.kikulabs.moviecataloguelocalstorage.database.DatabaseContract;

import static com.kikulabs.moviecataloguelocalstorage.database.DatabaseContract.getColumnInt;
import static com.kikulabs.moviecataloguelocalstorage.database.DatabaseContract.getColumnString;

public class MoviesAndTvData implements Parcelable {
    private String poster;
    private String backdrop;
    private String title;
    private String releaseDate;
    private String voteAverage;
    private String language;
    private String overview;
    private int id;

    protected MoviesAndTvData(Parcel in) {
        poster = in.readString();
        backdrop = in.readString();
        title = in.readString();
        releaseDate = in.readString();
        voteAverage = in.readString();
        language = in.readString();
        overview = in.readString();
        id = in.readInt();
    }

    public MoviesAndTvData() {
    }

    public MoviesAndTvData(Cursor cursor) {
        this.id = getColumnInt(cursor, DatabaseContract.MoviesColumns._ID);
        this.poster = getColumnString(cursor, DatabaseContract.MoviesColumns.POSTER);
        this.backdrop = getColumnString(cursor, DatabaseContract.MoviesColumns.BG);
        this.title = getColumnString(cursor, DatabaseContract.MoviesColumns.TITLE);
        this.releaseDate = getColumnString(cursor, DatabaseContract.MoviesColumns.RELEASEDATE);
        this.voteAverage = getColumnString(cursor, DatabaseContract.MoviesColumns.VOTEAVERAGE);
        this.language = getColumnString(cursor, DatabaseContract.MoviesColumns.LANGUAGE);
        this.overview = getColumnString(cursor, DatabaseContract.MoviesColumns.OVERVIEW);
    }

    public static final Creator<MoviesAndTvData> CREATOR = new Creator<MoviesAndTvData>() {
        @Override
        public MoviesAndTvData createFromParcel(Parcel in) {
            return new MoviesAndTvData(in);
        }

        @Override
        public MoviesAndTvData[] newArray(int size) {
            return new MoviesAndTvData[size];
        }
    };

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(poster);
        dest.writeString(backdrop);
        dest.writeString(title);
        dest.writeString(releaseDate);
        dest.writeString(voteAverage);
        dest.writeString(language);
        dest.writeString(overview);
        dest.writeInt(id);
    }
}
