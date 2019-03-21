package com.example.popularmovies;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.popularmovies.Model.MoviesPojo;

/**
 * Created by ADMIN on 12/5/2018.
 */

@Database(entities = {MoviesPojo.class}, version = 1, exportSchema = false)
public abstract class MoviesDatabase extends RoomDatabase {
    private static final String DATABASE = "movies_database";
    public abstract MovieDAO myMovieDaoMehod();
    private static volatile MoviesDatabase INSTANCE;
    static MoviesDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MoviesDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MoviesDatabase.class, DATABASE)
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;

    }
}