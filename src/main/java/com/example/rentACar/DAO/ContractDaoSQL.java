package com.example.rentACar.DAO;

import com.example.rentACar.DB.DatabaseConnection;
import com.example.rentACar.Models.Requests.ContractSampleRequestModel;
import com.example.rentACar.Models.Responses.CarResponseModel;
import com.example.rentACar.Models.Responses.ContractMode;
import com.example.rentACar.Models.Responses.ContractSampleResponseModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ContractDaoSQL implements ContractDao{

    private static final Connection conn = DatabaseConnection.getConnection();

    @Override
    public ContractSampleResponseModel sample(ContractSampleRequestModel model) {
        long difference =  (model.getEndDate().getTime()-model.getStartDate().getTime())/86400000;
        double price = -1;
        List<CarResponseModel> cars = new ArrayList<>();
        for (CarResponseModel car: cars) {
            if (car.getCarId().equals(model.getCarId()))
                price = car.getPrice();
        }
        double totalPrice = price * difference;
        return  new ContractSampleResponseModel(model.getContractId(), model.getUserId(), model.getCarId(),
                model.getStartDate(), model.getEndDate(), totalPrice, false);
    }

    @Override
    public void addContract(ContractSampleResponseModel contract) {
        try {
            PreparedStatement st = conn.prepareStatement("INSERT INTO contracts " +
                    "VALUES (?,?,?,?,?,?,?)");
            st.setString(1, String.valueOf(contract.getUserId()));
            st.setString(2, String.valueOf(contract.getCarId()));
            st.setDate(3, (Date) contract.getStartDate());
            st.setDate(4, (Date) contract.getEndDate());
            st.setDouble(5, contract.getTotalPrice());
            st.setBoolean(6, contract.isSigned());
            st.setBoolean(7, false);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addNewContract(ContractSampleResponseModel contract) {
        try {
            PreparedStatement st = conn.prepareStatement("INSERT INTO contracts " +
                    "VALUES (?,?,?,?,?,?,?)");
            st.setString(1, String.valueOf(contract.getUserId()));
            st.setString(2, String.valueOf(contract.getCarId()));
            st.setDate(3, (Date) contract.getStartDate());
            st.setDate(4, (Date) contract.getEndDate());
            st.setDouble(5, contract.getTotalPrice());
            st.setBoolean(6, true);
            st.setBoolean(7, false);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ContractMode> getAllContracts() {
        List<ContractMode> allContracts = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM contracts");
            while (rs.next()) {
                allContracts.add(new ContractMode(
                        UUID.fromString(rs.getString(1)),
                        UUID.fromString(rs.getString(2)),
                        UUID.fromString(rs.getString(3)),
                        rs.getDate(4),
                        rs.getDate(5),
                        rs.getDouble(6),
                        rs.getBoolean(7),
                        rs.getBoolean(8)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allContracts;
    }

    @Override
    public void updateContract(UUID contract) {
        try {
            PreparedStatement st = conn.prepareStatement("UPDATE contracts \n" +
                    "SET approved = 'true'\n" +
                    "WHERE contract_id = ?");
            st.setString(1, String.valueOf(contract));
            st.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void deleteContract(UUID contract) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM contracts WHERE contract_id = ?");
            st.setString(1, String.valueOf(contract));
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ContractMode> userHistory(UUID user) {
        List<ContractMode> history = new ArrayList<>();

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM contracts WHERE user_id = " + user);
            while(rs.next()){
                history.add(new ContractMode(
                        UUID.fromString(rs.getString(1)),
                        UUID.fromString(rs.getString(2)),
                        UUID.fromString(rs.getString(3)),
                        rs.getDate(4),
                        rs.getDate(5),
                        rs.getDouble(6),
                        rs.getBoolean(7),
                        rs.getBoolean(8)
                ));
        }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return history;
    }
}

