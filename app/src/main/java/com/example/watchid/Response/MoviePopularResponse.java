package com.example.watchid.Response;

import com.example.watchid.Model.MovieNowPlaying;
import com.example.watchid.Model.MoviePopular;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviePopularResponse {
    @SerializedName("page")
    private Long mPage;
    @SerializedName("results")
    private List<MoviePopular> mResults;
    @SerializedName("total_pages")
    private Long mTotalPages;
    @SerializedName("total_results")
    private Long mTotalResults;

    public List<MoviePopular> getPopular() {
        return mResults;
    }

}
