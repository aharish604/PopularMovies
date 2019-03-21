package com.example.popularmovies.Model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "movies_table")
public class MoviesPojo {


    String originalTitle, backGroundpath,overView,releaseDate,posatePath,voteAverage;

    @PrimaryKey
    int id;

    public MoviesPojo()
    {


    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getBackGroundpath() {
        return backGroundpath;
    }

    public void setBackGroundpath(String backGroundpath) {
        this.backGroundpath = backGroundpath;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosatePath() {
        return posatePath;
    }

    public void setPosatePath(String posatePath) {
        this.posatePath = posatePath;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
