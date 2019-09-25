package com.example.watchid.Fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.watchid.Activity.DetailActivity;
import com.example.watchid.Adapter.FavoriteAdapter;
import com.example.watchid.Callback.LoadFavoriteCallback;
import com.example.watchid.Database.FavoriteHelper;
import com.example.watchid.Model.Favorite;
import com.example.watchid.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.watchid.Database.DatabaseContract.FavoriteColumns.CONTENT_URI;
import static com.example.watchid.Helper.MappingHelper.getFavoriteList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    private static final String EXTRA_STATE = "EXTRA_STATE";
    @BindView(R.id.rvFav)
    RecyclerView rvFav;

    private FavoriteAdapter adapter;
    private FavoriteHelper helper;
    private LoadFavoriteCallback callback;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favortie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setupView();

        if (savedInstanceState == null) {
            new LoadFavoriteAsync(getContext(), callback).execute();
        } else {
            ArrayList<Favorite> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setListFavorite(list);
            }
        }
    }

     private void setupView() {
        callback = new LoadFavoriteCallback() {
            @Override
            public void preExecute() {

            }

            @Override
            public void postExecute(Cursor cursor) {
                ArrayList<Favorite> favorites = getFavoriteList(cursor);
                if (favorites.size() > 0) {
                    adapter.setListFavorite(favorites);
                    rvFav.setVisibility(View.VISIBLE);
                } else {
                    adapter.setListFavorite(new ArrayList<>());
                    rvFav.setVisibility(View.GONE);
                }
            }
        };

         HandlerThread handlerThread = new HandlerThread("DataObserver");
         handlerThread.start();
         Handler handler = new Handler(handlerThread.getLooper());
             DataObserver dataObserver = new DataObserver(handler, getContext(), callback);
         Objects.requireNonNull(getActivity()).getContentResolver().registerContentObserver(CONTENT_URI, true, dataObserver);

         rvFav.setLayoutManager(new LinearLayoutManager(getActivity()));
         rvFav.setHasFixedSize(true);

         helper = FavoriteHelper.getInstance(getContext());
         helper.open();

         adapter = new FavoriteAdapter(getActivity(), id -> {
             Intent intent = new Intent(getContext(), DetailActivity.class);
             intent.putExtra(DetailActivity.MID, id);
             startActivity(intent);
         });
         rvFav.setAdapter(adapter);
     }


     private static class LoadFavoriteAsync extends AsyncTask<Void, Void, Cursor> {

         private final WeakReference<Context> weakContext;
         private final WeakReference<LoadFavoriteCallback> weakCallback;

         private LoadFavoriteAsync(Context context, LoadFavoriteCallback callback) {
             weakCallback = new WeakReference<>(callback);
             weakContext = new WeakReference<>(context);
         }

         @Override
         protected void onPreExecute() {
             super.onPreExecute();
             weakCallback.get().preExecute();
         }

         @Override
         protected Cursor doInBackground(Void... voids) {
             Context context = weakContext.get();
             return context.getContentResolver().query(CONTENT_URI, null, null, null, null);
         }

         @Override
         protected void onPostExecute(Cursor cursor) {
             super.onPostExecute(cursor);
             weakCallback.get().postExecute(cursor);
         }
     }
     static class DataObserver extends ContentObserver {
        final Context context;
        final LoadFavoriteCallback callback;

        DataObserver(Handler handler, Context context, LoadFavoriteCallback callback) {
            super(handler);
            this.context = context;
            this.callback = callback;
        }

         @Override
         public void onChange(boolean selfChange) {
             super.onChange(selfChange);
             new LoadFavoriteAsync(context, callback).execute();
         }
     }
}
