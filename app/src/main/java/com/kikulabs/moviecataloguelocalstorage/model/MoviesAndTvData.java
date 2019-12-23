package com.kikulabs.moviecataloguelocalstorage.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MoviesAndTvData implements Parcelable {
    private String poster;
    private String title;
    private String releaseDate;
    private String voteAverage;
    private String language;
    private String overview;
    private int id;

    protected MoviesAndTvData(Parcel in) {
        poster = in.readString();
        title = in.readString();
        releaseDate = in.readString();
        voteAverage = in.readString();
        language = in.readString();
        overview = in.readString();
        id = in.readInt();
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

    public MoviesAndTvData() {

    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
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
        dest.writeString(title);
        dest.writeString(releaseDate);
        dest.writeString(voteAverage);
        dest.writeString(language);
        dest.writeString(overview);
        dest.writeInt(id);
    }
}
