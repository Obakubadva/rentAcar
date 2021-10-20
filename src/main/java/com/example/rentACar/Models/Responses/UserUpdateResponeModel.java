package com.example.rentACar.Models.Responses;

public class UserUpdateResponeModel {
    private boolean successful;
    private String info;

    public UserUpdateResponeModel(boolean successful, String info) {
        this.successful = successful;
        this.info = info;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
