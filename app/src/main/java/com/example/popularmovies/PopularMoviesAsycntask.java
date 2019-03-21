package com.example.popularmovies;

import android.os.AsyncTask;

import com.example.popularmovies.Activities.MainActivity;
import com.example.popularmovies.Model.MoviesPojo;
import com.example.popularmovies.RecyclerAdapter.MoviesAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PopularMoviesAsycntask extends AsyncTask<String, Void, String> {

    String VoteAverage, BackGroundpath, PosatePath, OriginalTitle, ReleaseDate, OverView;
 int ID;
    MainActivity mainActivity;
    public PopularMoviesAsycntask(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            InputStream stream = con.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while (line != null) {
                line = reader.readLine();
                buffer.append(line);
            }
            return buffer.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);


        List<MoviesPojo> data = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(s);
            JSONArray array = object.getJSONArray("results");
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                ID=jsonObject.optInt("id");
                OriginalTitle = jsonObject.getString("original_title");
                PosatePath = jsonObject.getString("poster_path");
                BackGroundpath = jsonObject.getString("backdrop_path");
                OverView = jsonObject.getString("overview");
                ReleaseDate = jsonObject.getString("release_date");
                VoteAverage = jsonObject.getString("vote_average");
                MoviesPojo moviesPojo=new MoviesPojo();
                moviesPojo.setPosatePath(PosatePath);
                moviesPojo.setOriginalTitle(OriginalTitle);
                moviesPojo.setBackGroundpath(BackGroundpath);
                moviesPojo.setOverView(OverView);
                moviesPojo.setVoteAverage(VoteAverage);
                moviesPojo.setId(ID);
                moviesPojo.setReleaseDate(ReleaseDate);
                data.add(moviesPojo);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mainActivity.recyclerView.setAdapter(new MoviesAdapter(mainActivity, data));
mainActivity.dialog.dismiss();

    }
}
