package com.ditra.ditraschool.core.user.models;

public class LoginResponseModel {
    String token ;

    public LoginResponseModel(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
