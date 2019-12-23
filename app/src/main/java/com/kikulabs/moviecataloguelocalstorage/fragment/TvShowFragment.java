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
import com.kikulabs.moviecataloguelocalstorage.adapter.ListTvShowAdapter;
import com.kikulabs.moviecataloguelocalstorage.model.MoviesAndTvData;
import com.kikulabs.moviecataloguelocalstorage.viewModel.TvShowViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {
    private ListTvShowAdapter adapter;
    private TvShowViewModel tvShowViewModel;
    private ProgressBar progressBar;
    private RecyclerView recyclerTvShow;

    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv_show, container, false);

        recyclerTvShow = view.findViewById(R.id.recyclerTvShow);
        recyclerTvShow.setHasFixedSize(true);
        recyclerTvShow.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBar = view.findViewById(R.id.progressBar);

        adapter = new ListTvShowAdapter(getContext());
        adapter.notifyDataSetChanged();
        recyclerTvShow.setAdapter(adapter);

        tvShowViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(TvShowViewModel.class);
        tvShowViewModel.getTvShow().observe(this, getTvShow);
        tvShowViewModel.setTvShow("EXTRA_MOVIE");

        showLoading(true);


        return view;
    }

    private Observer<ArrayList<MoviesAndTvData>> getTvShow = new Observer<ArrayList<MoviesAndTvData>>() {
        @Override
        public void onChanged(ArrayList<MoviesAndTvData> tvshow) {
            if (tvshow != null) {
                adapter.setData(tvshow);
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
