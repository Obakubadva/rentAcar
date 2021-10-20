package com.example.rentACar.Models.Requests;

import java.util.Date;
import java.util.UUID;

public class SignedContractRequestModel {
    private UUID userId, carId;
    private Date startDate, endDate;
    private double totalPrice;
    private boolean signed;

    public SignedContractRequestModel(UUID userId, UUID carId, Date startDate, Date endDate,
                                      double totalPrice, boolean signed) {
        this.userId = userId;
        this.carId = carId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
        this.signed = signed;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getCarId() {
        return carId;
    }

    public void setCarId(UUID carId) {
        this.carId = carId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isSigned() {
        return signed;
    }

    public void setSigned(boolean signed) {
        this.signed = signed;
    }
}
