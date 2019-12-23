package com.kikulabs.moviecataloguelocalstorage.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.kikulabs.moviecataloguelocalstorage.BuildConfig;
import com.kikulabs.moviecataloguelocalstorage.model.MoviesAndTvData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TvShowViewModel extends ViewModel {
    private static final String apiKey = BuildConfig.TMDB_API_KEY;
    private MutableLiveData<ArrayList<MoviesAndTvData>> listTvShow = new MutableLiveData<>();

    public void setTvShow(final String tvshow) {

        final ArrayList<MoviesAndTvData> listItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/discover/tv?api_key=" + apiKey + "&language=en-US";

        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Sukses", "onResponse: " + response);

                        {

                            try {
                                JSONArray datalist = response.getJSONArray("results");
                                for (int i = 0; i < datalist.length(); i++) {
                                    JSONObject data = datalist.getJSONObject(i);
                                    MoviesAndTvData tvshowItems = new MoviesAndTvData();
                                    tvshowItems.setTitle(data.getString("original_name"));
                                    tvshowItems.setPoster(data.getString("poster_path"));
                                    tvshowItems.setReleaseDate(data.getString("first_air_date"));
                                    tvshowItems.setVoteAverage(data.getString("vote_average"));
                                    tvshowItems.setLanguage(data.getString("original_language"));
                                    tvshowItems.setOverview(data.getString("overview"));
                                    listItems.add(tvshowItems);

                                }
                                listTvShow.postValue(listItems);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Log.d("Error", "onError: " + error);
                    }
                });


    }

    public LiveData<ArrayList<MoviesAndTvData>> getTvShow() {
        return listTvShow;
    }
}
