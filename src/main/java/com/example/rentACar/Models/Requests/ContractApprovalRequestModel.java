package com.example.rentACar.Models.Requests;

public class ContractApprovalRequestModel {
    private boolean approval;

    public ContractApprovalRequestModel(boolean approval) {
        this.approval = approval;
    }

    public boolean isApproval() {
        return approval;
    }
}
