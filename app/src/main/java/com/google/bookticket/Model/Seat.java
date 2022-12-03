package com.google.bookticket.Model;

public class Seat {
    String seat_txt;
    int seat_position;

    public Seat(String seat_txt, int seat_position) {
        this.seat_txt = seat_txt;
        this.seat_position = seat_position;
    }

    public String getSeat_txt() {
        return seat_txt;
    }

    public void setSeat_txt(String seat_txt) {
        this.seat_txt = seat_txt;
    }

    public int getSeat_position() {
        return seat_position;
    }

    public void setSeat_position(int seat_position) {
        this.seat_position = seat_position;
    }
}
