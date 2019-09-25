package com.example.watchid.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import com.example.watchid.Adapter.DiscoverAdapter;
import com.example.watchid.Adapter.FavoriteAdapter;
import com.example.watchid.Adapter.PopularAdapter;
import com.example.watchid.Adapter.ResultAdapter;
import com.example.watchid.Model.Favorite;
import com.example.watchid.Model.Movie;
import com.example.watchid.R;
import com.example.watchid.ViewModel.MovieViewModel;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultSearchActivity extends AppCompatActivity {

    static final String KEYWORD = "keyword";
    static final String INDEX = "index";
    @BindView(R.id.nulllayout)
    LinearLayout nullLayout;
    @BindView(R.id.rvSearch)
    RecyclerView rvSearch;
    @BindView(R.id.resultProgressbar)
    ProgressBar progressBar;
    @BindView(R.id.resultToolbar)
    Toolbar toolbar;
    @BindView(R.id.resultActivity)
    RelativeLayout resultActivity;
    @BindView(R.id.tvToolbarTitle)
    TextView tvToolbar;

    private String QUERY, LANGUAGE;
    private ResultAdapter adapter;
    private MovieViewModel movieViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_search);
        ButterKnife.bind(this);

        QUERY = getIntent().getStringExtra(KEYWORD);
        String CATEGORY = getIntent().getStringExtra(INDEX);
        getLanguage();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tvToolbar.setText((getResources().getString(R.string.result) + " \""+QUERY+"\""));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




        // for validate the movie or tv show
        if (CATEGORY.equals("0")) {
            setupMovieViewModeL();
            setupMovieData();
            setupMovieView();
            Log.i("Categotry", "MOVIE");
        }
    }


    private final Observer<ArrayList<Movie>> getMovie = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> items) {
            if (items != null) {
                adapter.addMovies(items);
                showLoading();
                if (adapter.getItemCount() == 0) {
                    showNull();
                    rvSearch.setVisibility(View.GONE);
                }
            } else {
                showLoading();
                showNull();
            }
        }
    };

    private void getLanguage() {
        LANGUAGE = Locale.getDefault().toString();
        if (LANGUAGE.equals("in_ID")) {
            LANGUAGE = "id_ID";
        }
    }

    private void setupMovieView() {
        adapter = new ResultAdapter(getApplicationContext(), new ArrayList<>(), id -> {
            Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
            intent.putExtra(DetailActivity.MID, id);
            startActivity(intent);
        });

        rvSearch.setLayoutManager(new LinearLayoutManager(this));
        rvSearch.setAdapter(adapter);
    }


    private void setupMovieData() {
        movieViewModel.searchMovies(LANGUAGE, QUERY);
    }

    private void setupMovieViewModeL() {
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getMovies().observe(this, getMovie);

    }

    private void showNull() {
        nullLayout.setVisibility(View.VISIBLE);
    }


    // for showing the loading indicator
    private void showLoading() {
        if (false) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

}
