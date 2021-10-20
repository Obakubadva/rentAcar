package com.example.rentACar.DAO;

import com.example.rentACar.Models.Requests.ContractSampleRequestModel;
import com.example.rentACar.Models.Requests.SignedContractRequestModel;
import com.example.rentACar.Models.Responses.ContractMode;
import com.example.rentACar.Models.Responses.ContractSampleResponseModel;
import com.example.rentACar.Models.Responses.SignedContractResponseModel;

import java.util.List;
import java.util.UUID;

public interface ContractDao {
    ContractSampleResponseModel sample(ContractSampleRequestModel model);
    void addContract(ContractSampleResponseModel contract);
    void addNewContract(ContractSampleResponseModel contract);
    List<ContractMode> getAllContracts();
    void updateContract(UUID contract);
    void deleteContract(UUID contract);
    List<ContractMode> userHistory(UUID user);
}
