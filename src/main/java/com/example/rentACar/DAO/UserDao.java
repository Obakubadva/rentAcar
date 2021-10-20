package com.example.rentACar.DAO;

import com.example.rentACar.Models.Requests.UserRegisterRequestModel;
import com.example.rentACar.Models.Requests.UserUpdateRequestModel;
import com.example.rentACar.Models.Responses.UserGetAllResponseModel;
import com.example.rentACar.Models.Responses.UserInfoResponseModel;
import com.example.rentACar.Models.Responses.UserResponseModel;

import java.util.List;
import java.util.UUID;

public interface UserDao {
    void register(UserRegisterRequestModel user);
    void login();
    void updateUser(UserUpdateRequestModel user, UUID userId);
    UserInfoResponseModel getUser(UUID userId);
    List<UserResponseModel> getAllUsers();
    List<UserGetAllResponseModel> allUsers();
}
