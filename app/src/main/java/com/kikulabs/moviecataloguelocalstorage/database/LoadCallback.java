package com.kikulabs.moviecataloguelocalstorage.database;

import com.kikulabs.moviecataloguelocalstorage.model.MoviesAndTvData;

import java.util.ArrayList;

public interface LoadCallback {
    void preExecute();

    void postExecute(ArrayList<MoviesAndTvData> moviesAndTvData);
}
