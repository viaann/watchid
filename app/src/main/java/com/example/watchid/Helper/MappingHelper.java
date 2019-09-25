package com.example.watchid.Helper;

import android.database.Cursor;

import com.example.watchid.Model.Favorite;

import java.net.IDN;
import java.util.ArrayList;

import retrofit2.http.POST;

import static android.provider.BaseColumns._ID;
import static com.example.watchid.Database.DatabaseContract.FavoriteColumns.ID;
import static com.example.watchid.Database.DatabaseContract.FavoriteColumns.POSTER;
import static com.example.watchid.Database.DatabaseContract.FavoriteColumns.RATING;
import static com.example.watchid.Database.DatabaseContract.FavoriteColumns.TITLE;

public class MappingHelper {
    public static ArrayList<Favorite> getFavoriteList(Cursor cursor) {
        ArrayList<Favorite> favorites = new ArrayList<>();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID));
            int mId = cursor.getInt(cursor.getColumnIndexOrThrow(ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE));
            String poster = cursor.getString(cursor.getColumnIndexOrThrow(POSTER));
            double rating = cursor.getDouble(cursor.getColumnIndexOrThrow(RATING));

            favorites.add(new Favorite(id, mId, title, poster, rating));
        }

        return favorites;
    }
}
