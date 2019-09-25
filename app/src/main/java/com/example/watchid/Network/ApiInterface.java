package com.example.watchid.Network;


import com.example.watchid.Model.Movie;
import com.example.watchid.Response.MovieCreditsResponse;
import com.example.watchid.Response.MovieNowPlayingResponse;
import com.example.watchid.Response.MoviePopularResponse;
import com.example.watchid.Response.MovieUpComingResponse;
import com.example.watchid.Response.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("discover/movie")
    Call<MoviesResponse> getMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("movie/now_playing")
    Call<MovieNowPlayingResponse> getNowPlaying(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );


    @GET("movie/upcoming")
    Call<MovieUpComingResponse> getUpComing(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("movie/top_rated")
    Call<MoviePopularResponse> getPopular(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("search/movie")
    Call<MoviesResponse> searchMovie(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("query") String query
    );




    @GET("movie/{movie_id}")
    Call<Movie> getMovie(
            @Path("movie_id") int id,
            @Query("api_key") String apiKEy,
            @Query("language") String language
    );

    @GET("movie/{movie_id}/credits")
    Call<MovieCreditsResponse> getCredits(
            @Path("movie_id") int id,
            @Query("api_key") String apiKey
    );
}