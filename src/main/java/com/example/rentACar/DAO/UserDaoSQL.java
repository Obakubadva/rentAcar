package com.example.rentACar.DAO;

import com.example.rentACar.DB.DatabaseConnection;
import com.example.rentACar.Models.Requests.UserRegisterRequestModel;
import com.example.rentACar.Models.Requests.UserUpdateRequestModel;
import com.example.rentACar.Models.Responses.CarResponseModel;
import com.example.rentACar.Models.Responses.UserGetAllResponseModel;
import com.example.rentACar.Models.Responses.UserInfoResponseModel;
import com.example.rentACar.Models.Responses.UserResponseModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDaoSQL implements UserDao {

    private static final Connection conn = DatabaseConnection.getConnection();


    @Override
    public void register(UserRegisterRequestModel user) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("INSERT INTO users " +
                    "VALUES (?,?,?)");
            st.setString(1, user.getUsername());
            st.setString(2, user.getEmail());
            st.setString(3, user.getPassword());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void login() {

    }

    @Override
    public void updateUser(UserUpdateRequestModel user, UUID userId) {
        try {
            PreparedStatement st = conn.prepareStatement("UPDATE user " +
                    "SET username = ?, " +
                    "password = ?, " +
                    "new_password = ?, " +
                    "first_name = ?, " +
                    "last_name = ? " +
                    "phone_number = ? " +
                    "image = ? " +

                    "WHERE car_id = ?");
            st.setString(1, user.getUsername());
            st.setString(2, user.getPassword());
            st.setString(3, user.getNewPassword());
            st.setString(4, user.getFirstName());
            st.setString(5, user.getLastName());
            st.setString(6, user.getPhoneNumber());
            st.setString(7, user.getImage());
            st.setString(14, String.valueOf(userId) );
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    public UserInfoResponseModel getUser(UUID userId) {
        PreparedStatement st = null;
            try {
                st = conn.prepareStatement("SELECT * FROM users WHERE users_id = ?");
                st.setString(1,String.valueOf(userId));
                ResultSet rs = st.executeQuery();
                rs.next();
                UserInfoResponseModel user= new UserInfoResponseModel(rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7), rs.getString(8));
                return user;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

    @Override
    public List<UserResponseModel> getAllUsers() {
        List<UserResponseModel> users = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                users.add(new UserResponseModel(
                        UUID.fromString(rs.getString(1)),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getBoolean(10)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }


    @Override
    public List<UserGetAllResponseModel> allUsers() {
        List<UserGetAllResponseModel> users = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                users.add(new UserGetAllResponseModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
