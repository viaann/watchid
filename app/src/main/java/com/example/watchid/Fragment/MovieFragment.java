package com.example.watchid.Fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.watchid.Activity.DetailActivity;
import com.example.watchid.Adapter.DiscoverAdapter;
import com.example.watchid.Adapter.PopularAdapter;
import com.example.watchid.Adapter.SectionsPagerAdapter;
import com.example.watchid.Model.Movie;
import com.example.watchid.Model.MoviePopular;
import com.example.watchid.R;
import com.example.watchid.ViewModel.MovieViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    @BindView(R.id.discoverRv)
    RecyclerView discoverRv;
    @BindView(R.id.popularRv)
    RecyclerView popularRv;
    @BindView(R.id.tvDiscover)
    TextView tvDiscover;
    @BindView(R.id.tvPopular)
    TextView tvPopular;
    @BindView(R.id.movieProgressbar)
    ProgressBar progressBar;

    private DiscoverAdapter discoverAdapter;
    private PopularAdapter popularAdapter;
    private MovieViewModel movieViewModel;


    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        showLoading(true);
        setTvhide();
        setupViewModel();
        setupData();
        setupDiscoverView();
        setupPopularView();
    }

    private void setupViewModel() {
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getMovies().observe(this, getMovies);
        movieViewModel.getPopular().observe(this, getPopular);
    }

    private void setupData() {
        String LANGUAGE = Locale.getDefault().toString();
        if (LANGUAGE.equals("in_ID")) {
            LANGUAGE = "id_ID";
        }
        movieViewModel.setMovies(LANGUAGE);
        movieViewModel.setPopular(LANGUAGE);
    }

    private void setupDiscoverView() {
        discoverAdapter = new DiscoverAdapter(getContext(), new ArrayList<>(),  id -> {
            Intent intent = new Intent(getContext(), DetailActivity.class);
            intent.putExtra(DetailActivity.MID, id);
            startActivity(intent);
        });

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        discoverRv.setLayoutManager(llm);
        discoverRv.setAdapter(discoverAdapter);
    }


    private void setupPopularView() {
        popularAdapter = new PopularAdapter(getContext(), new ArrayList<>(),  id -> {
            Intent intent = new Intent(getContext(), DetailActivity.class);
            intent.putExtra(DetailActivity.MID, id);
            startActivity(intent);
        });

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        popularRv.setLayoutManager(llm);
        popularRv.setAdapter(popularAdapter);
    }


    private final Observer<ArrayList<Movie>> getMovies = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movies) {
            if (movies != null) {
                discoverAdapter.addMovies(movies);
                showLoading(false);
                setTvshow();
            }
        }
    };


    private final Observer<ArrayList<MoviePopular>> getPopular = new Observer<ArrayList<MoviePopular>>() {
        @Override
        public void onChanged(ArrayList<MoviePopular> moviePopulars) {
            if (moviePopulars != null) {
                popularAdapter.addMovies(moviePopulars);
            }
        }
    };



    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void setTvhide() {
        tvDiscover.setVisibility(View.INVISIBLE);
        tvPopular.setVisibility(View.INVISIBLE);
    }


    private void setTvshow() {
        tvDiscover.setVisibility(View.VISIBLE);
        tvPopular.setVisibility(View.VISIBLE);
    }
}
