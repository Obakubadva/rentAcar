package com.example.rentACar.Controllers;

import com.example.rentACar.DAO.UserDao;
import com.example.rentACar.DAO.UserDaoSQL;
import com.example.rentACar.Models.Requests.UserLoginRequest;
import com.example.rentACar.Models.Requests.UserRegisterRequestModel;
import com.example.rentACar.Models.Requests.UserUpdateRequestModel;
import com.example.rentACar.Models.Responses.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class UserController {

    private static UserDao ud = new UserDaoSQL();

//1. /users/register - POST
//username и email морају бити јединствени
//email мора бити валидан
//username мора имати барем 3 карактера
//password мора имати барем 8 карактера, 1 број и 1 слово
//*password желимо да чувамо у бази енкриптован (на било који начин)
//https://www.baeldung.com/spring-security-registration-password-encoding-bcrypt
//    public static final UserDao us = new UserDaoSQL();
//    @PostMapping("/users/register")
//    public UserRegisterResponseModel registerUser(@RequestBody UserRegisterRequestModel user) {
//
//    }
@PostMapping("/users/register")
    public UserRegisterResponseModel register(@RequestBody UserRegisterRequestModel user){
        for (UserGetAllResponseModel userModel: ud.allUsers()) {
            if (userModel.getEmail().equals(user.getEmail()))
            return new UserRegisterResponseModel(false, "This email is already in use!");
            if (userModel.getUsername().equals(user.getUsername()))
            return new UserRegisterResponseModel(false, "This username is already in use!");
     }
        if (user.getEmail().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"))
            return new UserRegisterResponseModel(false, "Email is invalid!");
        if (user.getUsername().length() < 3)
            return new UserRegisterResponseModel(false, "Username must be at least 3 characters long!");
        if (user.getPassword().length() < 8  || !user.getPassword().matches(".*[a-zA-Z].*") || !user.getPassword().matches(".*\\d.*"))
            return new UserRegisterResponseModel(false, "Password must be at least 8 characters long, contain 1 number and at least 1 letter!");
            register(user);
        return new UserRegisterResponseModel(true, user.getUsername() + " - " + user.getEmail() + " has registered successfully!");
}

    //2. /users/login - POST
    //Прослеђујемо или username или email
    @PostMapping("/users/login")
    public UserLoginResponseModel login(@RequestBody UserLoginRequest userInfo){
        for (UserResponseModel userModel: ud.getAllUsers()) {
            if((userModel.getEmail().equals(userInfo.getIdentification()) || userModel.getUsername().equals(userInfo.getIdentification())) && userModel.getPassword().equals(userInfo.getPassword()) )
                return new UserLoginResponseModel(true, String.valueOf(userModel.getUserId()));
        }
        return new UserLoginResponseModel(false, "Invalid data entry!" );
    }

    //3. /users/{id} - PATCH
    //Корисник сам мења на основу свог id-a
    //Мењају се све прослеђене информације за корисника
    //Не можемо мењати email и personal_number (Те ствари мења директно
    //администратор)
    //Водити рачуна о валидности добијених података
    //Радити update само валидних поља
    //Ако шифра није валидна, не радити update уопште
    @PatchMapping("/users/{id}")
    public UserUpdateResponeModel update(@RequestBody UserUpdateRequestModel user, @PathVariable (value = "id") UUID userId){
        for (UserGetAllResponseModel userModel: ud.allUsers()) {
            if (userModel.getUserId().equals(userId))
                update(user, userId);
            return new UserUpdateResponeModel(true, "Data successfully changed");
        }
        return new UserUpdateResponeModel(false, "Data not changed");
    }

    //4. /users/{id} - GET
    //Дохватамо све информације о кориснику
    @GetMapping("/users/{id}")
    public UserInfoResponseModel getUser(@PathVariable UUID userId){
        return ud.getUser(userId);
}
    //5. /users - GET
    //Дохвата листу свих корисника
    //Кроз header се прослеђује id админа - https://www.baeldung.com/spring-rest-http-headers

    //
    @GetMapping("/users")
    public List<UserGetAllResponseModel> users(@RequestHeader(value = "authorization") UUID admin) {
        List<UserResponseModel> users = ud.getAllUsers();
        for (UserResponseModel userModel : users) {
            if (userModel.getUserId().equals(admin) && userModel.isAdmin())
            return ud.allUsers();
        }
        return new ArrayList<>();
    }

}
