package com.example.watchid.Activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.watchid.Fragment.FavoriteFragment;
import com.example.watchid.Fragment.HomeFragment;
import com.example.watchid.Fragment.MovieFragment;
import com.example.watchid.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    Toolbar toolbar;
    TextView tvToolbar;
    String index = "0";
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    fragment = new HomeFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;
                case R.id.navigation_movie:
                    fragment = new MovieFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;
                case R.id.navigation_favorite:
                    fragment = new FavoriteFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, fragment, fragment.getClass().getSimpleName())
                            .commit();

                    return true;
            }
            return false;
        }
    };


    private void setupToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setElevation(1);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tvToolbar.setText(getResources().getString(R.string.app_name));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_search:
                SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
                searchView.setOnQueryTextListener(this);
                break;

            case R.id.action_change_settings:
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        toolbar = findViewById(R.id.toolbar);
        tvToolbar = findViewById(R.id.tvToolbarTitle);
        setupToolbar();

        if  (savedInstanceState == null) {
            navView.setSelectedItemId(R.id.navigation_home);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Intent i = new Intent(MainActivity.this, ResultSearchActivity.class);
        i.putExtra(ResultSearchActivity.KEYWORD, query);
        i.putExtra(ResultSearchActivity.INDEX, index);
        Log.i("INDEX", " " + index);
        startActivity(i);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}
