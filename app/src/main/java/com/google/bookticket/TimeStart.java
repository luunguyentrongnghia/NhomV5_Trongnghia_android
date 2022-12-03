package com.google.bookticket;

public class TimeStart {
    String time, room,date;

    public TimeStart(String time) {
        this.time = time;
    }

    public TimeStart(String time, String room, String date) {
        this.time = time;
        this.room = room;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
