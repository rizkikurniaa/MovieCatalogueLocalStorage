package com.kikulabs.favmov.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MoviesAndTvData implements Parcelable {
    private int id;
    private String title;
    private String poster;
    private String backdrop;
    private String releaseDate;
    private String voteAverage;
    private String language;
    private String overview;

    protected MoviesAndTvData(Parcel in) {
        id = in.readInt();
        title = in.readString();
        poster = in.readString();
        backdrop = in.readString();
        releaseDate = in.readString();
        voteAverage = in.readString();
        language = in.readString();
        overview = in.readString();
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

    public MoviesAndTvData(int id, String title, String poster, String backdrop, String releaseDate, String voteAverage, String language, String overview) {
        this.id = id;
        this.title = title;
        this.poster = poster;
        this.backdrop = backdrop;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.language = language;
        this.overview = overview;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(poster);
        dest.writeString(backdrop);
        dest.writeString(releaseDate);
        dest.writeString(voteAverage);
        dest.writeString(language);
        dest.writeString(overview);
    }
}
