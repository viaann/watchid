package com.example.watchid.Response;

import com.example.watchid.Model.Movie;
import com.example.watchid.Model.MovieNowPlaying;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieNowPlayingResponse {
    @SerializedName("page")
    private Long mPage;
    @SerializedName("results")
    private List<MovieNowPlaying> mResults;
    @SerializedName("total_pages")
    private Long mTotalPages;
    @SerializedName("total_results")
    private Long mTotalResults;

    public List<MovieNowPlaying> getNowPlaying() {
        return mResults;
    }

}
