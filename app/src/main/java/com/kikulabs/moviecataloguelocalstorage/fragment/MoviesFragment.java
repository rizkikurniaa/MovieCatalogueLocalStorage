package com.kikulabs.moviecataloguelocalstorage.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kikulabs.moviecataloguelocalstorage.R;
import com.kikulabs.moviecataloguelocalstorage.adapter.ListMoviesAdapter;
import com.kikulabs.moviecataloguelocalstorage.model.MoviesAndTvData;
import com.kikulabs.moviecataloguelocalstorage.viewModel.MoviesViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {
    private ListMoviesAdapter adapter;
    private MoviesViewModel moviesViewModel;
    private ProgressBar progressBar;
    RecyclerView recyclerMovie;

    public MoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        recyclerMovie = view.findViewById(R.id.recyclerMovie);
        recyclerMovie.setHasFixedSize(true);
        recyclerMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBar = view.findViewById(R.id.progressBar);

        adapter = new ListMoviesAdapter(getContext());
        adapter.notifyDataSetChanged();
        recyclerMovie.setAdapter(adapter);

        moviesViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MoviesViewModel.class);
        moviesViewModel.getMovies().observe(this, getMovie);
        moviesViewModel.setMovies("EXTRA_MOVIE");

        showLoading(true);

        return view;
    }

    private Observer<ArrayList<MoviesAndTvData>>
            getMovie = new Observer<ArrayList<MoviesAndTvData>>() {
        @Override
        public void onChanged(ArrayList<MoviesAndTvData> movies) {
            if (movies != null) {
                adapter.setData(movies);
            }

            showLoading(false);

        }
    };

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

}
