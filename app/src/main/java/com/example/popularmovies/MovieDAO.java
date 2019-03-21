package com.example.popularmovies;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.popularmovies.Model.MoviesPojo;

import java.util.List;

/**
 * Created by ADMIN on 12/5/2018.
 */
@Dao
public interface MovieDAO {
    @Query("SELECT * FROM movies_table")
    LiveData<List<MoviesPojo>> getAllMovies();
    @Query("SELECT * FROM movies_table")
    List<MoviesPojo> getMovies();

    @Query("SELECT * FROM movies_table WHERE id =:idd")
    boolean checkFav(int idd);

    @Insert
    void saveAsFavourates(MoviesPojo moviesInfo);

    @Delete
    void removeFromFavourates(MoviesPojo moviesInfo);
}