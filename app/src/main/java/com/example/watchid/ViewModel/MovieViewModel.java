package com.example.watchid.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.watchid.BuildConfig;
import com.example.watchid.Model.Movie;
import com.example.watchid.Model.MovieCredits;
import com.example.watchid.Model.MovieNowPlaying;
import com.example.watchid.Model.MoviePopular;
import com.example.watchid.Model.MovieUpComing;
import com.example.watchid.Network.ApiInterface;
import com.example.watchid.Response.MovieCreditsResponse;
import com.example.watchid.Response.MovieNowPlayingResponse;
import com.example.watchid.Response.MoviePopularResponse;
import com.example.watchid.Response.MovieUpComingResponse;
import com.example.watchid.Response.MoviesResponse;
import com.example.watchid.Utils.ApiUtils;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@SuppressWarnings("ALL")
public class MovieViewModel extends ViewModel {

    private final String API_KEY = BuildConfig.TMDB_API_KEY;
    private final MutableLiveData<Movie> movie = new MutableLiveData<>();
    private static final ApiInterface apiInterface = new ApiUtils().getApi();
    private final MutableLiveData<ArrayList<Movie>> listMovies = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<MovieCredits>> listMovieCredits = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<MoviePopular>> listMoviePopular = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<MovieUpComing>> listMovieUpcoming = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<MovieNowPlaying>> listMoviewNowPlaying = new MutableLiveData<>();

    public LiveData<ArrayList<Movie>> getMovies() {
        return listMovies;
    }

    public LiveData<ArrayList<MovieNowPlaying>> getNowPlaying() {
        return listMoviewNowPlaying;
    }

    public LiveData<ArrayList<MovieUpComing>> getUpComing() {
        return listMovieUpcoming;
    }

    public LiveData<ArrayList<MovieCredits>> getCredits() {
        return listMovieCredits;
    }

    public LiveData<ArrayList<MoviePopular>> getPopular() { return listMoviePopular; }

    public void setMovies(final String language) {
        apiInterface.getMovies(API_KEY, language).enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<Movie> movies = new ArrayList<>(Objects.requireNonNull(response.body()).getMovies());
                    listMovies.postValue(movies);
                    Log.d("Movie", "success loading from API");

                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.d("Movie", "error loading from API");

            }
        });

    }


    public void setPopular(final String language) {
        apiInterface.getPopular(API_KEY, language).enqueue(new Callback<MoviePopularResponse>() {
            @Override
            public void onResponse(Call<MoviePopularResponse> call, Response<MoviePopularResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<MoviePopular> moviePopulars = new ArrayList<>(Objects.requireNonNull(response.body()).getPopular());
                    listMoviePopular.postValue(moviePopulars);
                    Log.d("setPopular", "success");
                } else {

                }
            }

            @Override
            public void onFailure(Call<MoviePopularResponse> call, Throwable t) {
                Log.d("setPopular", t.getMessage());
            }
        });
    }


    public void setNowPlaying(final String language) {
        apiInterface.getNowPlaying(API_KEY, language).enqueue(new Callback<MovieNowPlayingResponse>() {
            @Override
            public void onResponse(Call<MovieNowPlayingResponse> call, Response<MovieNowPlayingResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<MovieNowPlaying> movieNowPlayings = new ArrayList<>(Objects.requireNonNull(response.body()).getNowPlaying());
                    listMoviewNowPlaying.postValue(movieNowPlayings);
                    Log.d("setNowPlaying", "success");
                } else {

                }
            }

            @Override
            public void onFailure(Call<MovieNowPlayingResponse> call, Throwable t) {
                Log.d("setNowPlaying", t.getMessage());
            }
        });
    }


    public void setUpComing(final String language) {
        apiInterface.getUpComing(API_KEY, language).enqueue(new Callback<MovieUpComingResponse>() {
            @Override
            public void onResponse(Call<MovieUpComingResponse> call, Response<MovieUpComingResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<MovieUpComing> movieUpComings = new ArrayList<>(Objects.requireNonNull(response.body()).getNowPlaying());
                    listMovieUpcoming.postValue(movieUpComings);
                    Log.d("setUpComing", "success");
                } else {

                }
            }

            @Override
            public void onFailure(Call<MovieUpComingResponse> call, Throwable t) {
                Log.d("setUpComing", t.getMessage());
            }
        });

    }

    public void setCredits(int id) {
        apiInterface.getCredits(id, API_KEY).enqueue(new Callback<MovieCreditsResponse>() {
            @Override
            public void onResponse(Call<MovieCreditsResponse> call, Response<MovieCreditsResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<MovieCredits> movieCredits = new ArrayList<>(Objects.requireNonNull(response.body()).getCredits());
                    listMovieCredits.postValue(movieCredits);
                    Log.d("setCredits", "success");
                }
            }

            @Override
            public void onFailure(Call<MovieCreditsResponse> call, Throwable t) {
                    Log.d("setCredits", t.getMessage());
            }
        });
    }


    public LiveData<Movie> getMovie() {
        return movie;
    }

    public void searchMovies(final String language, String query) {
        apiInterface.searchMovie(API_KEY, language, query).enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<Movie> movies = new ArrayList<>(Objects.requireNonNull(response.body()).getMovies());
                    listMovies.postValue(movies);
                    Log.d("Movie", "success loading from API");

                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.d("Movie", "error loading from API");

            }
        });

    }


    public void setMovie(int id, String language) {
        apiInterface.getMovie(id, API_KEY, language).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()) {
                    movie.postValue(response.body());
                    Log.d("MainActivity", "posts loaded from API");
                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.d("MainActivity", "error loading from API");

            }
        });
    }


    }



