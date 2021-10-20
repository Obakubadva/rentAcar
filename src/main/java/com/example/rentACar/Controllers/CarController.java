package com.example.rentACar.Controllers;
import com.example.rentACar.Models.Responses.UserResponseModel;
import org.springframework.web.server.ResponseStatusException;

import com.example.rentACar.DAO.CarDao;
import com.example.rentACar.DAO.CarDaoSQL;
import com.example.rentACar.DAO.UserDao;
import com.example.rentACar.DAO.UserDaoSQL;
import com.example.rentACar.Models.Responses.CarResponseModel;
import com.example.rentACar.Models.Requests.CarUpdateRequestModel;
import com.example.rentACar.Models.Responses.UserGetAllResponseModel;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
public class CarController {

    public static final CarDao cd = new CarDaoSQL();
    public static final UserDao ud = new UserDaoSQL();
//*
    @GetMapping("/cars")
    public List<CarResponseModel> allCars() {
        return cd.getAllCars();
    }

//*
    @GetMapping("/cars/search")
    public List<CarResponseModel> searchForCar(@RequestParam(required = false) Integer year,
                                               @RequestParam(required = false) String make,
                                               @RequestParam(required = false) String model,
                                               @RequestParam(required = false) Boolean automatic,
                                               @RequestParam(required = false) Double price,
                                               @RequestParam(required = false) Integer power,
                                               @RequestParam(required = false) Integer doors){
        return cd.searchCars(year,make, model, automatic, price, power, doors);
}
    //*
    @GetMapping("/cars/{carId}")
    public CarResponseModel getCarByID(@PathVariable("carId") UUID carID) {
        return cd.getCar(carID);
    }

   //*
    @PatchMapping("/cars/{carId}")
    public void updateCar(@RequestHeader(value = "authorization") UUID admin, @PathVariable("carId") UUID carID,
                          @RequestBody CarUpdateRequestModel car) {
        List<UserResponseModel> users = ud.getAllUsers();
        for (UserResponseModel userModel : users) {
            if (userModel.getUserId().equals(admin) && userModel.isAdmin())
            cd.updateCar(car, carID);
        }
    }

 //*
    @DeleteMapping("/cars/{carId}")
    public void carDelete(@RequestHeader(value = "authorization") UUID admin, @PathVariable UUID carID) {
        List<UserResponseModel> users = ud.getAllUsers();
        for (UserResponseModel userModel : users) {
            if (userModel.getUserId().equals(admin) && userModel.isAdmin())
            cd.deleteCar(carID);
        }
    }
  //*
    @PostMapping("/cars")
    public void addCar(@RequestHeader(value = "authorization") UUID admin, @RequestBody CarUpdateRequestModel car){
        List<UserResponseModel> users = ud.getAllUsers();
        for (UserResponseModel userModel : users) {
            if (userModel.getUserId().equals(admin) && userModel.isAdmin())
                cd.addCar(car);
        }
    }

   //*
    @GetMapping("/cars/available")
    public List<CarResponseModel> carsAvailable(@RequestParam (value = "startDate") Date start,
                                            @RequestParam (value = "endDate") Date end){
        return cd.availableCars(start, end);
}

    //*
@GetMapping("/cars/available/search")
public List<CarResponseModel> carsAvailable(@RequestParam Date start,
                                            @RequestParam Date end,
                                            @RequestParam(required = false) Integer year,
                                            @RequestParam(required = false) String make,
                                            @RequestParam(required = false) String model,
                                            @RequestParam(required = false) Boolean automatic,
                                            @RequestParam(required = false) Double price,
                                            @RequestParam(required = false) Integer power,
                                            @RequestParam(required = false) Integer doors){
       List<CarResponseModel> cars = cd.availableCars(start, end);
       cars = cd.searchCars(year, make, model, automatic, price,power, doors);
    return cars;
}

    //*
    @GetMapping("/cars/{carId}/calendar")
    public List<Date> notAvailable(@PathVariable("carId") UUID carID) {
        return cd.calendar(carID);
    }
}