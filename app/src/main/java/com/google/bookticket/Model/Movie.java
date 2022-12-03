package com.google.bookticket.Model;

import java.util.List;

public class Movie {
    String movieName, movieKind, movieUrlImage, movieTime, movieID;
    List<String> movieTimeStart;


    public Movie() {

    }

    public Movie(String movieID) {
        this.movieID = movieID;
    }

    public Movie(String movieName, String movieKind, String movieUrlImage, String movieTime, List<String> movieTimeStart) {
        this.movieName = movieName;
        this.movieKind = movieKind;
        this.movieUrlImage = movieUrlImage;
        this.movieTime = movieTime;
        this.movieTimeStart = movieTimeStart;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieKind() {
        return movieKind;
    }

    public void setMovieKind(String movieKind) {
        this.movieKind = movieKind;
    }

    public String getMovieUrlImage() {
        return movieUrlImage;
    }

    public void setMovieUrlImage(String movieUrlImage) {
        this.movieUrlImage = movieUrlImage;
    }

    public String getMovieTime() {
        return movieTime;
    }

    public void setMovieTime(String movieTime) {
        this.movieTime = movieTime;
    }

    public List<String> getMovieTimeStart() {
        return movieTimeStart;
    }

    public void setMovieTimeStart(List<String> movieTimeStart) {
        this.movieTimeStart = movieTimeStart;
    }

    public String getMovieID() {
        return movieID;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }
}
