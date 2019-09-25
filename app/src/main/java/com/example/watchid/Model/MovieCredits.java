package com.example.watchid.Model;

import com.google.gson.annotations.SerializedName;

public class MovieCredits {
    @SerializedName("id")
    private Long mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("profile_path")
    private String profile_path;

    public Long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getProfilePath() {
        return  profile_path;
    }
}
