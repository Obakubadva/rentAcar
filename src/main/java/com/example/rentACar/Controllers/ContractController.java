package com.example.rentACar.Controllers;

import com.example.rentACar.DAO.ContractDao;
import com.example.rentACar.DAO.ContractDaoSQL;
import com.example.rentACar.DAO.UserDao;
import com.example.rentACar.DAO.UserDaoSQL;
import com.example.rentACar.Models.Requests.ContractApprovalRequestModel;
import com.example.rentACar.Models.Requests.ContractSampleRequestModel;
import com.example.rentACar.Models.Requests.SignedContractRequestModel;
import com.example.rentACar.Models.Responses.ContractMode;
import com.example.rentACar.Models.Responses.ContractSampleResponseModel;
import com.example.rentACar.Models.Responses.SignedContractResponseModel;
import com.example.rentACar.Models.Responses.UserResponseModel;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class ContractController {

    public static final ContractDao cd = new ContractDaoSQL();
    public static final UserDao ud = new UserDaoSQL();

    @PostMapping("/contracts/sample")
    ContractSampleResponseModel sample(@RequestBody ContractSampleRequestModel model) {
        return cd.sample(model);
    }

    @PostMapping("/contacts")
    public SignedContractResponseModel signedContract(@RequestBody ContractSampleResponseModel contract) {
        List<ContractMode> list = cd.getAllContracts();
        for (ContractMode c : list) {
            if (c.getContractId().equals(contract.getContractId()))
                cd.addNewContract(contract);
            return new SignedContractResponseModel(true, "Contract is  waiting for approval");

        }
        return new SignedContractResponseModel(false, "Contract isn`t created");
    }

    @GetMapping("/contracts ")
    public List<ContractMode> allContracts(@RequestHeader(value = "authorization") UUID admin) {
        List<UserResponseModel> users = ud.getAllUsers();
        for (UserResponseModel userModel : users) {
            if (userModel.getUserId().equals(admin) && userModel.isAdmin())
                return cd.getAllContracts();
        }
        return null;
    }

    @GetMapping("/contracts/pending")
    public List<ContractMode> pendingContracts(@RequestHeader(value = "authorization") UUID admin) {
        List<ContractMode> list = this.allContracts(admin);
        List<ContractMode> pendingList = new ArrayList<>();
        for (ContractMode contract : list) {
            if (!contract.isApproved())
                pendingList.add(contract);
        }
        return pendingList;
    }

    @GetMapping("/contracts/{contractId}/approval")
    public void approvingContract(@RequestHeader("authorization") UUID admin, @PathVariable("contractId") UUID contract,
                                  @RequestBody ContractApprovalRequestModel approval) {
        List<UserResponseModel> users = ud.getAllUsers();
        for (UserResponseModel userModel : users) {
            if (userModel.getUserId().equals(admin) && userModel.isAdmin()) {
                if (approval.isApproval())
                    cd.updateContract(contract);
                else
                    cd.deleteContract(contract);
            }

        }
    }

    @GetMapping("/contracts/{userId}/history")
    public List<ContractMode> history(@RequestHeader("authorization") UUID admin,
                                      @PathVariable("userId") UUID user) {
        List<UserResponseModel> users = ud.getAllUsers();
        for (UserResponseModel userModel : users) {
            if (userModel.getUserId().equals(admin) && userModel.isAdmin()) {
                return cd.userHistory(user);
            }
        }
        return null;
    }
}
