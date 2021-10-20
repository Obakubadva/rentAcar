package com.example.rentACar.DAO;

import com.example.rentACar.Models.Responses.CarResponseModel;
import com.example.rentACar.Models.Requests.CarUpdateRequestModel;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface CarDao {

    List<CarResponseModel> getAllCars();
    List<CarResponseModel> searchCars( Integer year, String make, String model,
                                      Boolean automatic, Double price, Integer power, Integer doors);
    CarResponseModel getCar(UUID carId);
    void updateCar(CarUpdateRequestModel car, UUID carId);
    void deleteCar(UUID carId);
    void addCar(CarUpdateRequestModel car);
    List<CarResponseModel> availableCars(Date startDate, Date endDate);
    List<Date> calendar(UUID carId);
}
