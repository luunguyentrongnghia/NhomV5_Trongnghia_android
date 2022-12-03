package com.google.bookticket.Model;

public class Users {
    String userPhone, userPassword, userEmail, userDoB, userFullname;

    public Users() {

    }

    public Users(String userPhone, String userPassword, String userEmail, String userDoB, String userFullname) {
        this.userPhone = userPhone;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.userDoB = userDoB;
        this.userFullname = userFullname;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserDoB() {
        return userDoB;
    }

    public void setUserDoB(String userDoB) {
        this.userDoB = userDoB;
    }

    public String getUserFullname() {
        return userFullname;
    }

    public void setUserFullname(String userFullname) {
        this.userFullname = userFullname;
    }
}
