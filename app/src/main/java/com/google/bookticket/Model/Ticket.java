package com.google.bookticket.Model;

public class Ticket {
    String movieID, movieName, room, seat,date, timestart, userID;

    public Ticket(){

    }

    public Ticket(String movieID, String movieName, String room, String seat, String date, String timestart, String userID) {
        this.movieID = movieID;
        this.movieName = movieName;
        this.room = room;
        this.seat = seat;
        this.date = date;
        this.timestart = timestart;
        this.userID = userID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMovieID() {
        return movieID;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getTimestart() {
        return timestart;
    }

    public void setTimestart(String timestart) {
        this.timestart = timestart;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
