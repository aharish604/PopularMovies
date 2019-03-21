package com.example.popularmovies;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.popularmovies.Model.MoviesPojo;

import java.util.List;

/**
 * Created by ADMIN on 12/5/2018.
 */

public class MovieRepository {
    private MovieDAO mMovieDao;
    private LiveData<List<MoviesPojo>> mAllWords;
    private  List<MoviesPojo> allmovies;

    public List<MoviesPojo> getAllmovies() {
        return allmovies;
    }

    MovieRepository(Application application) {
        MoviesDatabase db = MoviesDatabase.getDatabase(application);
        mMovieDao = db.myMovieDaoMehod();
        mAllWords = mMovieDao.getAllMovies();
        allmovies=mMovieDao.getMovies();
    }

    LiveData<List<MoviesPojo>> getAllWords() {
        return mAllWords;
    }

    void insert(MoviesPojo word) {
        new insertAsyncTask(mMovieDao).execute(word);
    }
    void delete(MoviesPojo word) {
        new deleteAsyncTask(mMovieDao).execute(word);
    }

    boolean checkFav(int id) {
        return mMovieDao.checkFav(id);
    }

    private static class insertAsyncTask extends AsyncTask<MoviesPojo, Void, Void> {

        private MovieDAO mAsyncTaskDao;

        insertAsyncTask(MovieDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final MoviesPojo... params) {
            mAsyncTaskDao.saveAsFavourates(params[0]);
            return null;
        }
    }
    private static class deleteAsyncTask extends AsyncTask<MoviesPojo, Void, Void> {

        private MovieDAO mAsyncTaskDao;

        deleteAsyncTask(MovieDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final MoviesPojo... params) {
            mAsyncTaskDao.removeFromFavourates(params[0]);
            return null;
        }
    }
}
