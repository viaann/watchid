package com.example.watchid.Utils;

import com.example.watchid.Network.ApiClient;
import com.example.watchid.Network.ApiInterface;

public class ApiUtils {

    public static final String IMAGE_URL = "https://image.tmdb.org/t/p/original";
    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    public static ApiInterface getApi() {
        return ApiClient.getClient(BASE_URL).create(ApiInterface.class);
    }
}
