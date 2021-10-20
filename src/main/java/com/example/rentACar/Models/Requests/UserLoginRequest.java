package com.example.rentACar.Models.Requests;

public class UserLoginRequest {
    private String identification;
    private String password;

    public UserLoginRequest(String identification, String password) {
        this.identification = identification;
        this.password = password;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
