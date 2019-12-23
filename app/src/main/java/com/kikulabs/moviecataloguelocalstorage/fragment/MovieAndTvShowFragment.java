package com.kikulabs.moviecataloguelocalstorage.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.kikulabs.moviecataloguelocalstorage.R;
import com.kikulabs.moviecataloguelocalstorage.adapter.SectionPagerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieAndTvShowFragment extends Fragment {

    private TabLayout tabs;
    private ViewPager viewPager;

    public MovieAndTvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_and_tv_show, container, false);

        SectionPagerAdapter sectionsPagerAdapter = new SectionPagerAdapter(getContext(), getChildFragmentManager());

        viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs = view.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        return view;
    }

}
