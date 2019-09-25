package com.example.watchid.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.watchid.Adapter.CreditsAdapter;
import com.example.watchid.Adapter.UpcomingAdapter;
import com.example.watchid.Database.FavoriteHelper;
import com.example.watchid.Model.Favorite;
import com.example.watchid.Model.Movie;
import com.example.watchid.Model.MovieCredits;
import com.example.watchid.R;
import com.example.watchid.ViewModel.MovieViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.http.POST;

import static com.example.watchid.Database.DatabaseContract.FavoriteColumns.CONTENT_URI;
import static com.example.watchid.Database.DatabaseContract.FavoriteColumns.ID;
import static com.example.watchid.Database.DatabaseContract.FavoriteColumns.POSTER;
import static com.example.watchid.Database.DatabaseContract.FavoriteColumns.RATING;
import static com.example.watchid.Database.DatabaseContract.FavoriteColumns.TITLE;
import static com.example.watchid.Utils.ApiUtils.IMAGE_URL;

public class DetailActivity extends AppCompatActivity {
    public static final String MID = "movie_id";
    private int MOVIE_ID;
    @BindView(R.id.detailPoster)
    ImageView detailPoster;
    @BindView(R.id.detailTitle)
    TextView detailTitle;
    @BindView(R.id.detailOverview)
    TextView detailOverview;
    @BindView(R.id.detailRating)
    RatingBar detailRating;
    @BindView(R.id.detailRv)
    RecyclerView detailRv;
    @BindView(R.id.detailFavorite)
    ImageView detailFavorie;
    @BindView(R.id.detailDate)
    TextView detailDate;
    @BindView(R.id.detailProgress)
    ProgressBar progressBar;
    @BindView(R.id.detailBack)
    ImageView detailBack;
    MovieViewModel movieViewModel;
    CreditsAdapter creditsAdapter;

    private FavoriteHelper helper;
    private int id, mId;
    private String title, poster;
    private double rating;
    private Favorite favorite = new Favorite();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        showView(false);
        showLoading(true);
        setupViewModeL();
        setupData();
        setupCredits();
    }



    private void setupData() {
        helper = new FavoriteHelper(getApplicationContext());
        helper.open();
        MOVIE_ID = getIntent().getIntExtra(MID, MOVIE_ID);
        String LANGUANGE = Locale.getDefault().toString();
        if (LANGUANGE.equals("in_ID")) {
            LANGUANGE = "id_ID";
        }
        movieViewModel.setMovie(MOVIE_ID, LANGUANGE);
        movieViewModel.setCredits(MOVIE_ID);
    }

    private void setupViewModeL() {
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getMovie().observe(this, getMovie);
        movieViewModel.getCredits().observe(this, getCredits);
    }


    private void setupCredits() {
            creditsAdapter = new CreditsAdapter();
            LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
            llm.setOrientation(LinearLayoutManager.HORIZONTAL);
            detailRv.setLayoutManager(llm);
            detailRv.setAdapter(creditsAdapter);
    }

    private final Observer<ArrayList<MovieCredits>> getCredits = new Observer<ArrayList<MovieCredits>>() {
        @Override
        public void onChanged(ArrayList<MovieCredits> movieCredits) {
            if (movieCredits != null) {
                creditsAdapter.addCredits(movieCredits);
                showLoading(false);
                showView(true);
            }
        }
    };

    @OnClick({R.id.detailBack})
    public void doBack(View view){
        finish();
    }

    @OnClick({R.id.detailFavorite})
    public void addFavorite(View view) {
        if (isFavorite()) {
            Uri uri = Uri.parse(CONTENT_URI + "/" + id);
            int i = getContentResolver().delete(uri, null, null);
            detailFavorie.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            Toast.makeText(this, getString(R.string.favorite_deletd), Toast.LENGTH_SHORT).show();
        } else {
            favorite.setmId(id);
            favorite.setFavTitle(title);
            favorite.setFavPoster(poster);
            favorite.setFavRating(rating);

            ContentValues contentValues = new ContentValues();
            contentValues.put(ID, mId);
            contentValues.put(TITLE, title);
            contentValues.put(POSTER, poster);
            contentValues.put(RATING, rating);

            if (getContentResolver().insert(CONTENT_URI, contentValues) != null) {
                Toast.makeText(this, title + " " + "has been a favorite", Toast.LENGTH_SHORT).show();
                detailFavorie.setImageResource(R.drawable.ic_favorite_black_24dp);
            } else {
                Toast.makeText(this, title + " " + "failed to be favorite", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isFavorite() {
        Uri uri = Uri.parse(CONTENT_URI + "");
        boolean favorite = false;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        int getmId;
        if (cursor.moveToFirst()) {
            do {
                getmId = cursor.getInt(1);
                if (getmId == mId) {
                    id = cursor.getInt(0);
                    detailFavorie.setImageResource(R.drawable.ic_favorite_black_24dp);
                    favorite = true;
                }
            } while (cursor.moveToNext());

        }

        return favorite;
    }

    private final Observer<Movie> getMovie = new Observer<Movie>() {
        @Override
        public void onChanged(Movie movie) {
            if (movie != null) {

                // add data to favorite
                mId = movie.getId() ;
                title = movie.getTitle();
                poster = movie.getPosterPath();
                rating = movie.getRating();
                isFavorite();

                double score = movie.getRating() / 2;

                Glide.with(getApplicationContext())
                        .load(IMAGE_URL + movie.getBackdropPath())
                        .into(detailPoster);

                detailTitle.setText(movie.getTitle());
                detailOverview.setText(movie.getOverview());
                detailRating.setRating((float) score);
                detailDate.setText(movie.getReleaseDate());


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

    private void showView(Boolean state) {
        if (state) {
            detailFavorie.setVisibility(View.VISIBLE);
            detailTitle.setVisibility(View.VISIBLE);
            detailRv.setVisibility(View.VISIBLE);
            detailRating.setVisibility(View.VISIBLE);
            detailDate.setVisibility(View.VISIBLE);
            detailOverview.setVisibility(View.VISIBLE);
            detailBack.setVisibility(View.VISIBLE);
        } else {
            detailFavorie.setVisibility(View.INVISIBLE);
            detailTitle.setVisibility(View.INVISIBLE);
            detailRv.setVisibility(View.INVISIBLE);
            detailRating.setVisibility(View.INVISIBLE);
            detailDate.setVisibility(View.INVISIBLE);
            detailOverview.setVisibility(View.INVISIBLE);
            detailBack.setVisibility(View.INVISIBLE);
        }
    }
}
