package com.example.rentACar.DAO;

import com.example.rentACar.DB.DatabaseConnection;
import com.example.rentACar.Models.Responses.CarResponseModel;
import com.example.rentACar.Models.Requests.CarUpdateRequestModel;
import com.example.rentACar.Models.Responses.UserResponseModel;


import java.sql.*;
import java.util.*;
import java.util.Date;


public class CarDaoSQL implements CarDao{

    private static final Connection conn = DatabaseConnection.getConnection();


    @Override
    public List<CarResponseModel> getAllCars() {
        List<CarResponseModel> allCars = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM cars");
            while (rs.next()) {
                allCars.add(new CarResponseModel(
                        UUID.fromString(rs.getString(1)),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getString(7),
                        rs.getDouble(8),
                        rs.getInt(9),
                        rs.getString(10),
                        rs.getInt(11),
                        rs.getBoolean(12),
                        rs.getString(13),
                        rs.getString(14)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCars;
    }

    @Override
    public List<CarResponseModel> searchCars( Integer year, String make, String model,
                                              Boolean automatic, Double price,
                                              Integer power, Integer doors) {
        List<CarResponseModel> lista = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM cars WHERE year >= " + year +
                    " OR make LIKE '%" + make + "%'" +
                    " OR model LIKE '%" + model + "%'" +
                    " OR automatic = " + automatic +
                    " OR price <= " + price +
                    " OR power <= " + power +
                    " OR doors = " + doors);
            while (rs.next()) {
                lista.add(new CarResponseModel(
                        UUID.fromString(rs.getString(1)),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getString(7),
                        rs.getDouble(8),
                        rs.getInt(9),
                        rs.getString(10),
                        rs.getInt(11),
                        rs.getBoolean(12),
                        rs.getString(13),
                        rs.getString(14)
                ));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public CarResponseModel getCar(UUID carId) {

            PreparedStatement st = null;
            try {
                st = conn.prepareStatement("SELECT * FROM cars WHERE car_id = ?");
                st.setString(1, String.valueOf(carId));
                ResultSet rs = st.executeQuery();
                rs.next();
                CarResponseModel car = new CarResponseModel(UUID.fromString(rs.getString(1)), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6), rs.getString(7),
                        rs.getDouble(8), rs.getInt(9), rs.getString(10), rs.getInt(11),
                        rs.getBoolean(12), rs.getString(13), rs.getString(14));
                return car;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return null;
        }

    @Override
    public void updateCar(CarUpdateRequestModel car, UUID carId) {
        try {
            PreparedStatement st = conn.prepareStatement("UPDATE cars " +
                    "SET licence_plate = ?, " +
                    "make = ?, " +
                    "model = ?, " +
                    "year = ?, " +
                    "engine_capacity = ? " +
                    "color = ? " +
                    "price = ? " +
                    "doors = ? " +
                    "size = ? " +
                    "power = ? " +
                    "automatic = ? " +
                    "fuel = ? " +
                    "image = ? " +
                    "WHERE car_id = ?");
            st.setString(1, car.getLicencePlate());
            st.setString(2, car.getMake());
            st.setString(3, car.getModel());
            st.setInt(4, car.getYear());
            st.setInt(5, car.getEngineCapacity());
            st.setString(6, car.getColor());
            st.setDouble(7, car.getPrice());
            st.setInt(8, car.getDoors());
            st.setString(9, car.getSize());
            st.setInt(10, car.getPower());
            st.setBoolean(11, car.isAutomatic());
            st.setString(12, car.getFuel());
            st.setString(13, car.getImage());
            st.setString(14, String.valueOf(carId) );
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCar(UUID carId) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM cars WHERE car_id = ?");
            st.setString(1, String.valueOf(carId));
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addCar(CarUpdateRequestModel car) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("INSERT INTO cars " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            st.setString(1, car.getLicencePlate());
            st.setString(2, car.getMake());
            st.setString(3, car.getModel());
            st.setInt(4, car.getYear());
            st.setInt(5, car.getEngineCapacity());
            st.setString(6, car.getColor());
            st.setDouble(7, car.getPrice());
            st.setInt(8, car.getDoors());
            st.setString(9, car.getSize());
            st.setInt(10, car.getPower());
            st.setBoolean(11, car.isAutomatic());
            st.setString(12, car.getFuel());
            st.setString(13, car.getImage());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<CarResponseModel> availableCars(Date startDate, Date endDate) {
        List<CarResponseModel> carsAvailable = new ArrayList<>();
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("SELECT * FROM cars\n" +
                    "JOIN contracts \n" +
                    "ON cars.car_id = contracts.car_id\n" +
                    "WHERE (contracts.start_date = ? AND contracts.end_date = ? AND contracts.approved = false)");
            st.setDate(1, (java.sql.Date) startDate);
            st.setDate(2, (java.sql.Date) endDate);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                carsAvailable.add(new CarResponseModel(
                        UUID.fromString(rs.getString(1)),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getString(7),
                        rs.getDouble(8),
                        rs.getInt(9),
                        rs.getString(10),
                        rs.getInt(11),
                        rs.getBoolean(12),
                        rs.getString(13),
                        rs.getString(14)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carsAvailable;
    }

    @Override
    public List<Date> calendar(UUID carId) {
        List<Date> dates = new ArrayList<Date>();
        Calendar calendar = new GregorianCalendar();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("SELECT generate_series(start_date, end_date, interval '1 day')::" +
                    "date AS dates FROM contracts WHERE car_id = ?");
            ps.setString(1, String.valueOf(carId));
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                dates.add(rs.getDate(1));
                dates.add(new Date());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dates;
    }

}

