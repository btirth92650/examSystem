package com.exam.model;

public class jwtResponse {

    String token;

    public jwtResponse(String token) {
        this.token = token;
    }

    public jwtResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
