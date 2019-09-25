package com.example.watchid.Fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.watchid.Activity.DetailActivity;
import com.example.watchid.Adapter.DiscoverAdapter;
import com.example.watchid.Adapter.NowPlayingAdapter;
import com.example.watchid.Adapter.UpcomingAdapter;
import com.example.watchid.Model.Movie;
import com.example.watchid.Model.MovieNowPlaying;
import com.example.watchid.Model.MovieUpComing;
import com.example.watchid.R;
import com.example.watchid.ViewModel.MovieViewModel;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    @BindView(R.id.bdRvMovie)
    RecyclerView bdRvMovie;
    @BindView(R.id.nsRv)
    RecyclerView nsRv;
    @BindView(R.id.upRv)
    RecyclerView upRv;
    @BindView(R.id.homeProgressbar)
    ProgressBar progressBar;
    @BindView(R.id.tvPlayingtoday)
    TextView tvPlaying;
    @BindView(R.id.tvRecommended)
    TextView tvRecommended;
    @BindView(R.id.tvUpcoming)
    TextView tvUpComing;

    private MovieViewModel movieViewModel;
    private DiscoverAdapter discoverAdapter;
    private NowPlayingAdapter nowPlayingAdapter;
    private UpcomingAdapter upcomingAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        showLoading(true);
        setTvHide();
        setupViewModeL();
        setupData();
        setupDiscoverView();
        setupNowPlayingview();
        setupUpcoming();
    }


    private void setupViewModeL() {
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getMovies().observe(this, getMovies);
        movieViewModel.getNowPlaying().observe(this, getNowPlaying);
        movieViewModel.getUpComing().observe(this, getUpcoming);
    }



    private void setupData() {
        String LANGUAGE = Locale.getDefault().toString();
        if (LANGUAGE.equals("in_ID")) {
            LANGUAGE = "id_ID";
        }
        movieViewModel.setMovies(LANGUAGE);
        movieViewModel.setNowPlaying(LANGUAGE);
        movieViewModel.setUpComing(LANGUAGE);
    }

    private void setupDiscoverView() {
        discoverAdapter = new DiscoverAdapter(getContext(), new ArrayList<>(),   id -> {
            Intent intent = new Intent(getContext(), DetailActivity.class);
            intent.putExtra(DetailActivity.MID, id);
            startActivity(intent);
        });

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        bdRvMovie.setLayoutManager(llm);
        bdRvMovie.setAdapter(discoverAdapter);
    }


    private void setupNowPlayingview() {
        nowPlayingAdapter = new NowPlayingAdapter(getContext(), new ArrayList<>(), id -> {
            Intent intent = new Intent(getContext(), DetailActivity.class);
            intent.putExtra(DetailActivity.MID, id);
            startActivity(intent);
        });

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        nsRv.setLayoutManager(llm);
        nsRv.setAdapter(nowPlayingAdapter);
    }

    private void setupUpcoming() {
        upcomingAdapter = new UpcomingAdapter(getContext(), new ArrayList<>(),  id -> {
            Intent intent = new Intent(getContext(), DetailActivity.class);
            intent.putExtra(DetailActivity.MID, id);
            startActivity(intent);
        });

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        upRv.setLayoutManager(llm);
        upRv.setAdapter(upcomingAdapter);
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

    private final Observer<ArrayList<MovieNowPlaying>> getNowPlaying = new Observer<ArrayList<MovieNowPlaying>>() {
        @Override
        public void onChanged(ArrayList<MovieNowPlaying> movieNowPlayings) {
            if (movieNowPlayings != null) {
                nowPlayingAdapter.addMovies(movieNowPlayings);
            }
        }
    };

    private final Observer<ArrayList<MovieUpComing>> getUpcoming = new Observer<ArrayList<MovieUpComing>>() {
        @Override
        public void onChanged(ArrayList<MovieUpComing> movieUpComings) {
            if (movieUpComings != null) {
                upcomingAdapter.addMovies(movieUpComings);
            }
        }
    };

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void setTvHide() {
        tvPlaying.setVisibility(View.INVISIBLE);
        tvRecommended.setVisibility(View.INVISIBLE);
        tvUpComing.setVisibility(View.INVISIBLE);
    }


    private void setTvshow() {
        tvPlaying.setVisibility(View.VISIBLE);
        tvRecommended.setVisibility(View.VISIBLE);
        tvUpComing.setVisibility(View.VISIBLE);
    }
}