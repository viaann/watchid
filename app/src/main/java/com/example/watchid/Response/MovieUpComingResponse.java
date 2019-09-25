package com.example.watchid.Response;

import com.example.watchid.Model.MovieNowPlaying;
import com.example.watchid.Model.MovieUpComing;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieUpComingResponse {
    @SerializedName("page")
    private Long mPage;
    @SerializedName("results")
    private List<MovieUpComing> mResults;
    @SerializedName("total_pages")
    private Long mTotalPages;
    @SerializedName("total_results")
    private Long mTotalResults;

    public List<MovieUpComing> getNowPlaying() {
        return mResults;
    }

}
