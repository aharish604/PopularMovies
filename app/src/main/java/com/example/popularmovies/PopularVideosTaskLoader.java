package com.example.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PopularVideosTaskLoader extends AsyncTaskLoader<String> {

    int id;
    public PopularVideosTaskLoader(@NonNull Context context, int id) {
        super(context);
        this.id = id;
    }

    @Nullable
    @Override
    public String loadInBackground() {


        try {
            URL url = new URL("https://api.themoviedb.org/3/movie/"+id+"/videos?api_key=a1a7e470568d9fac3287fcc3471147b9");
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
    protected void onStartLoading() {



        super.onStartLoading();
        forceLoad();

    }
}
