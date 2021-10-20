package com.example.rentACar.Models.Requests;

import java.util.Date;
import java.util.UUID;

public class ContractSampleRequestModel {
    private UUID contractId;
    private UUID userId;
    private UUID carId;
    private Date startDate, endDate;

    public ContractSampleRequestModel(UUID contractId, UUID userId, UUID carId, Date startDate, Date endDate) {
        this.contractId = UUID.randomUUID();
        this.userId = userId;
        this.carId = carId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public UUID getContractId() {
        return contractId;
    }

    public void setContractId(UUID contractId) {
        this.contractId = contractId;
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
}
