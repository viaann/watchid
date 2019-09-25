package com.example.watchid.Callback;

import android.database.Cursor;

public interface LoadFavoriteCallback {
    void preExecute();
    void postExecute(Cursor cursor);
}
