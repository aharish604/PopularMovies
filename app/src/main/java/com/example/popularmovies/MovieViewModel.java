package com.example.popularmovies;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.popularmovies.Model.MoviesPojo;

import java.util.List;

/**
 * Created by ADMIN on 12/5/2018.
 */

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository mRepository;
    private LiveData<List<MoviesPojo>> list;
    private  List<MoviesPojo> allmovies;

    public List<MoviesPojo> getAllmovies() {
        return allmovies;
    }

    public MovieViewModel(@NonNull Application application) {
        super(application);
        mRepository = new MovieRepository(application);
        list = mRepository.getAllWords();
        allmovies=mRepository.getAllmovies();

    }

    public LiveData<List<MoviesPojo>> getList()
    {
        return list;
    }
   public void insert(MoviesPojo moviesInfo){
        mRepository.insert(moviesInfo);
    }
    public void delete(MoviesPojo moviesInfo){
        mRepository.delete(moviesInfo);
    }

    public boolean checkFav(int id) {
        return mRepository.checkFav(id);
    }
}
