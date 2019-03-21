package com.example.popularmovies.Activities;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Debug;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.popularmovies.JsonUtils;
import com.example.popularmovies.Model.MoviesPojo;
import com.example.popularmovies.MovieViewModel;
import com.example.popularmovies.PopularMoviesReviewsTaskLoader;
import com.example.popularmovies.PopularVideosTaskLoader;
import com.example.popularmovies.R;
import com.example.popularmovies.ReviewInfo;
import com.example.popularmovies.ReviewsAdapter;
import com.example.popularmovies.TrailerAdapter;
import com.example.popularmovies.TrailerInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks {

    ImageView imageView, favimage;

    TextView title, date, vote, overview;
    String Image, Title, Overview, Date, Average, posterpath;
    public int ID;
    RecyclerView trailerRecyclerview, reviewsRecyclerview;
    private int Video_LOADER_ID = 1;
    private int REVIEW_LOADER_ID = 2;
    public MovieViewModel moviesPojo;

    boolean var=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        trailerRecyclerview = findViewById(R.id.rvtrailer);
        reviewsRecyclerview = findViewById(R.id.rvreview);
        favimage = findViewById(R.id.fav_img_id);


        title = findViewById(R.id.title);
        date = findViewById(R.id.date);
        vote = findViewById(R.id.votes);
        overview = findViewById(R.id.overview);
        imageView = findViewById(R.id.backgroundimage);

        moviesPojo = ViewModelProviders.of(this)
                .get(MovieViewModel.class);


        Image = getIntent().getStringExtra("image");
        Title = getIntent().getStringExtra("title");
        Overview = getIntent().getStringExtra("overview");
        Date = getIntent().getStringExtra("Date");
        Average = getIntent().getStringExtra("Average");
        ID = getIntent().getIntExtra("id", 0);
        posterpath = getIntent().getStringExtra("posterpath");

        Log.d("id", "" + ID);

        title.setText(Title);
        date.setText(Date);
        overview.setText(Overview);
        vote.setText(Average);
        Picasso.with(this)
                .load("https://image.tmdb.org/t/p/w500" + Image)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error_image)
                .into(imageView);

        List<MoviesPojo> pojos=moviesPojo.getAllmovies();

        for(int i=0;i<pojos.size();i++)
        {

            if(ID==pojos.get(i).getId())
            {

                var=true;
                favimage.setImageResource(R.drawable.fav_fill);
                break;
            }

        }

       /* if (moviesPojo.checkFav(ID)) {
            favimage.setImageResource(R.drawable.fav_fill);
        }
*/

        Toast.makeText(this, ""+var, Toast.LENGTH_SHORT).show();
        if (isNetworkAvailable()) {
            getSupportLoaderManager().initLoader(Video_LOADER_ID, null, this);
            getSupportLoaderManager().initLoader(REVIEW_LOADER_ID, null, this);
        } else {
            showAlert();
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

    private boolean isNetworkAvailable() {
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(MainActivity.CONNECTIVITY_SERVICE);

        if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        } else {
            return false;
        }
    }

    @NonNull
    @Override
    public Loader onCreateLoader(int i, @Nullable Bundle bundle) {
        if (i == Video_LOADER_ID) {
            return new PopularVideosTaskLoader(this, ID);

        } else if (i == REVIEW_LOADER_ID) {
            return new PopularMoviesReviewsTaskLoader(this, ID);

        }
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader loader, Object o) {



        int id = loader.getId();


        if (id == Video_LOADER_ID) {
            ArrayList<TrailerInfo> list = JsonUtils.parseTrailerInfoJson(o.toString());
            if (list.size() > 0) {


                trailerRecyclerview.setLayoutManager(new LinearLayoutManager(this));
                trailerRecyclerview.setAdapter(new TrailerAdapter(this, list));
            } else {
                //trailers.setVisibility(View.GONE);
                Toast.makeText(this, R.string.notrailers, Toast.LENGTH_SHORT).show();
            }

        }

        if (id == REVIEW_LOADER_ID) {

            ArrayList<ReviewInfo> list = JsonUtils.parseReviewInfoJson(o.toString());
            if (list.size() > 0) {

                reviewsRecyclerview.setLayoutManager(new LinearLayoutManager(this));
                reviewsRecyclerview.setAdapter(new ReviewsAdapter(this, list));
            } else {
                //reviewss.setVisibility(View.GONE);
                Toast.makeText(this, R.string.noreviews, Toast.LENGTH_LONG).show();
            }


        }


    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }

    public MoviesPojo setValuesToPojo() {
        MoviesPojo info = new MoviesPojo();

        info.setId(ID);
        info.setBackGroundpath(Image);
        info.setOverView(Overview);
        info.setReleaseDate(Date);
        info.setVoteAverage(Average);
        info.setOriginalTitle(Title);
        info.setPosatePath(posterpath);


        return info;
    }

    public void favMethod(View view) {

       // moviesPojo.checkFav(ID);
        if (var) {
            favimage.setImageResource(R.drawable.fav_button);

            moviesPojo.delete(setValuesToPojo());
            Toast.makeText(this, R.string.removed, Toast.LENGTH_SHORT).show();

            var=false;
        } else {
            favimage.setImageResource(R.drawable.fav_fill);

            moviesPojo.insert(setValuesToPojo());

            Toast.makeText(this, "Saved Favourites", Toast.LENGTH_SHORT).show();
            Log.d("insert", "data inserted");
            var=true;
        }
    }
}
