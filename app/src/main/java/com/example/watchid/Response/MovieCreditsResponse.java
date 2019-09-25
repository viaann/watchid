package com.example.watchid.Response;

import com.example.watchid.Model.MovieCredits;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieCreditsResponse {

    @SerializedName("cast")
    private List<MovieCredits> mCredits;

    public List<MovieCredits> getCredits() {
        return mCredits;
    }

}
