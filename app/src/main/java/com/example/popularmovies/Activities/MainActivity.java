package com.example.popularmovies.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.popularmovies.Model.MoviesPojo;
import com.example.popularmovies.MovieViewModel;
import com.example.popularmovies.PopularMoviesAsycntask;
import com.example.popularmovies.R;
import com.example.popularmovies.RecyclerAdapter.MoviesAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public ProgressDialog dialog;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private String SORT_ORDER_KEY = "key";
    static final String POPULAR = "popular";
    static final String TOPRATED = "top_rated";
    static final String FAV_KEY = "favorate";

    MovieViewModel movieViewModel;
    static final String ApiKey = "be3c6c22a3a65531a6b1472d9964c5dd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv);
        movieViewModel = ViewModelProviders.of(this)
                .get(MovieViewModel.class);

        preferences = getSharedPreferences("SaveState", Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.apply();

        if (isNetworkAvailable()) {
            if (preferences.getString(SORT_ORDER_KEY, POPULAR).equals(POPULAR)) {

                orientation();

                dialog = new ProgressDialog(this);
                dialog.setMessage("Loading");
                dialog.show();
                PopularMoviesAsycntask popularMoviesAsycntask = new PopularMoviesAsycntask(this);

                popularMoviesAsycntask.execute("https://api.themoviedb.org/3/movie/" + POPULAR + "?api_key=" + ApiKey);
            } else if (preferences.getString(SORT_ORDER_KEY, TOPRATED).equals(TOPRATED)) {
                orientation();
                dialog = new ProgressDialog(this);
                dialog.setMessage("Loading");
                dialog.show();
                PopularMoviesAsycntask popularMoviesAsycntask = new PopularMoviesAsycntask(this);
                popularMoviesAsycntask.execute("https://api.themoviedb.org/3/movie/" + TOPRATED + "?api_key=" + ApiKey);
            }
        } else {

            showAlert();

        }
        if (preferences.getString(SORT_ORDER_KEY, FAV_KEY).equals(FAV_KEY)) {

            orientation();

            getFavourates();
        }

    }


    public void orientation() {

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }

    }

    private void getFavourates() {
        movieViewModel.getList().observe(this, new Observer<List<MoviesPojo>>() {

            @Override
            public void onChanged(@Nullable List<MoviesPojo> moviesPojos) {

                if (moviesPojos.size() > 0) {
                    MoviesAdapter adapter = new MoviesAdapter(MainActivity.this,
                            (ArrayList<MoviesPojo>) moviesPojos);
                    adapter.setWords(moviesPojos);
                    recyclerView.setAdapter(adapter);
                } else {

                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(MainActivity.this);
                    builder.setTitle(R.string.nofavs);
                    builder.setMessage(R.string.msgnofav);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            editor.putString(SORT_ORDER_KEY, POPULAR);
                            editor.commit();
                            if (isNetworkAvailable()) {
                                PopularMoviesAsycntask popularMoviesAsycntask = new PopularMoviesAsycntask(MainActivity.this);

                                popularMoviesAsycntask.execute("https://api.themoviedb.org/3/movie/" + POPULAR + "?api_key=" + ApiKey);
                            } else {
                                showAlert();
                            }
                        }
                    });
                    builder.show();

                }

            }
        });


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(MainActivity.CONNECTIVITY_SERVICE);

        if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        } else {
            return false;
        }
    }


    private void showAlert() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.nointernet);
        builder.setMessage(R.string.no_internet_message);
        builder.setPositiveButton(R.string.go_to_settings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(
                        Settings.ACTION_WIFI_SETTINGS));
            }
        });
        builder.setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionmenus, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popular: {

                if (isNetworkAvailable()) {

                    editor.putString(SORT_ORDER_KEY, POPULAR);
                    editor.apply();
                    PopularMoviesAsycntask popularMoviesAsycntask = new PopularMoviesAsycntask(this);
                    popularMoviesAsycntask.execute("https://api.themoviedb.org/3/movie/" + POPULAR + "?api_key=" + ApiKey);

                } else {
                    showAlert();
                }

                break;
            }
            case R.id.Toprated: {


                if (isNetworkAvailable()) {
                    editor.putString(SORT_ORDER_KEY, TOPRATED);
                    editor.apply();
                    PopularMoviesAsycntask popularMoviesAsycntask = new PopularMoviesAsycntask(this);
                    popularMoviesAsycntask.execute("https://api.themoviedb.org/3/movie/" + TOPRATED + "?api_key=" + ApiKey);

                } else {
                    showAlert();
                }
                break;
            }
            case R.id.fav_menuId: {
                editor.putString(SORT_ORDER_KEY, FAV_KEY);
                editor.commit();
                getFavourates();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}