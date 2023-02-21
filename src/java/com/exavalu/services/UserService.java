/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exavalu.services;

import com.exavalu.models.Country;
import com.exavalu.models.District;
import com.exavalu.models.State;
import com.exavalu.models.User;
import com.exavalu.utils.JDBCConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 *
 * @author LENOVO
 */
public class UserService {

    public static UserService userService = null;

    private UserService() {
    }

    public static UserService getInstance() {
        if (userService == null) {
            return new UserService();
        } else {
            return userService;
        }
    }

    public boolean doLogin(User user) {
        boolean success = false;

        String sql = "Select * from users where emailAddress=? and password=?";

        try {
            Connection con = JDBCConnectionManager.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getEmailAddress());
            ps.setString(2, user.getPassword());

            System.out.println("LoginService :: " + ps);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                success = true;
            }

        } catch (SQLException ex) {
            Logger log = Logger.getLogger(UserService.class.getName());
            log.error(LocalDateTime.now() + "@" + ex);
            ex.printStackTrace();
        }

        return success;
    }

    public boolean doSignup(User user) {
        boolean result = false;
        String sql = "INSERT INTO users (emailAddress,password,firstName,lastName,countryCode,stateCode,districtCode,status)"
                + "VALUES (?,?,?,?,?,?,?,?)";
        try {

            Connection con = JDBCConnectionManager.getConnection();

            PreparedStatement preparedStatement;
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, user.getEmailAddress());
            preparedStatement.setString(2, user.getPassword());

            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setString(5, user.getCountryCode());
            preparedStatement.setString(6, user.getStateCode());
            preparedStatement.setString(7, user.getDistCode());
            preparedStatement.setInt(8, 1);

            int row = preparedStatement.executeUpdate();
            if (row == 1) {
                result = true;
            }
        } catch (SQLException ex) {
            Logger log = Logger.getLogger(UserService.class.getName());
            log.error(LocalDateTime.now() + "@" + ex);
            ex.printStackTrace();
        }

        return result;
    }

    public User getUser(String emailAddress) {
        User user = new User();
        Connection con = JDBCConnectionManager.getConnection();
        try {

            String sql = "Select * from users where emailAddress=?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, emailAddress);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {

                user.setRoleId(rs.getInt("roleId"));
                user.setEmailAddress(rs.getString("emailAddress"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
            }

        } catch (SQLException ex) {

            Logger log = Logger.getLogger(UserService.class.getName());
            log.error(LocalDateTime.now() + "@" + ex);
            ex.printStackTrace();
        }

        return user;
    }

    public ArrayList getAllCountries() {
        ArrayList cnList = new ArrayList();
        try {

            Connection con = JDBCConnectionManager.getConnection();

            String sql = "Select * from countries";

            PreparedStatement preparedStatement = con.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Country cn = new Country();
                cn.setCountryCode(rs.getString("countryCode"));

                cn.setCountryName(rs.getString("countryName"));

                cnList.add(cn);

            }

        } catch (SQLException ex) {
            Logger log = Logger.getLogger(UserService.class.getName());
            log.error(LocalDateTime.now() + "@" + ex);
            ex.printStackTrace();
        }

        System.out.println("country List size =" + cnList.size());
        return cnList;
    }

    public ArrayList getAllStates(String countryCode) {
        ArrayList stateList = new ArrayList();
        try {

            Connection con = JDBCConnectionManager.getConnection();

            String sql = "Select * from states where countryCode=? ";

            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, countryCode);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {

                State state = new State();
                state.setStateCode(rs.getString("stateCode"));

                state.setStateName(rs.getString("stateName"));

                state.setCountryCode(rs.getString("countryCode"));
                stateList.add(state);

            }

        } catch (SQLException ex) {
            Logger log = Logger.getLogger(UserService.class.getName());
            log.error(LocalDateTime.now() + "@" + ex);
            ex.printStackTrace();
        }

        System.out.println("state  list size=" + stateList.size());
        return stateList;
    }

    public ArrayList getAlldistricts(String stateCode) {
        ArrayList distList = new ArrayList();
        try {

            Connection con = JDBCConnectionManager.getConnection();

            String sql = "Select * from districts where stateCode=? ";

            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, stateCode);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {

                District dist = new District();
                dist.setDistCode(rs.getString("distCode"));
                dist.setDistName(rs.getString("distName"));
                dist.setStateCode(rs.getString("stateCode"));
                distList.add(dist);

            }

        } catch (SQLException ex) {
            Logger log = Logger.getLogger(UserService.class.getName());
            log.error(LocalDateTime.now() + "@" + ex);
            ex.printStackTrace();
        }

        System.out.println("district   list size=" + distList.size());
        return distList;
    }

    public boolean updateUser(String emailAddress) {
        boolean result = false;
        try {
            Connection con = JDBCConnectionManager.getConnection();
            String sql = "UPDATE employeedb.users\n"
                    + "SET policyStatus = 1 where emailAddress=?";

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, emailAddress);

//            System.out.println("sql="+preparedStatement);
            int row = preparedStatement.executeUpdate();

            if (row == 1) {
                result = true;
            }

        } catch (SQLException ex) {
            Logger log = Logger.getLogger(UserService.class.getName());
            log.error(LocalDateTime.now() + "@" + ex);
            ex.printStackTrace();
        }
        return result;
    }

    public boolean checkPolicyStatus(String emailAddress) {
      boolean result=false;
      try {
            Connection con = JDBCConnectionManager.getConnection();
            String sql = "Select * from users where emailAddress=? and policyStatus=1";

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, emailAddress);

            System.out.println("sql="+preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                result = true;
            }

        } catch (SQLException ex) {
            Logger log = Logger.getLogger(UserService.class.getName());
            log.error(LocalDateTime.now() + "@" + ex);
            ex.printStackTrace();
        }
        System.out.println("check policy status ="+result);
        return result;
    }

    public boolean ApplyPolicy(String emailAddress, String firstName) {
        boolean result = false;
        String sql = "INSERT INTO claims (claimStatus,driverName,emailAddress,date)"
                + "VALUES (?,?,?,?)";
        try {

            LocalDateTime currentLocalDateTime = LocalDateTime.now();
 
        // Create DateTimeFormatter instance with specified format
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
 
        // Format LocalDateTime to String
        String formattedDateTime = currentLocalDateTime.format(dateTimeFormatter);
            Connection con = JDBCConnectionManager.getConnection();

            PreparedStatement preparedStatement;
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, "Pending");
            preparedStatement.setString(2, firstName);

            preparedStatement.setString(3, emailAddress);
            preparedStatement.setString(4, formattedDateTime );
        

            int row = preparedStatement.executeUpdate();
            if (row == 1) {
                result = true;
            }
        } catch (SQLException ex) {
            Logger log = Logger.getLogger(UserService.class.getName());
            log.error(LocalDateTime.now() + "@" + ex);
            ex.printStackTrace();
        }

        return result;
    }

    public String checkClaimStatus(String emailAddress) {
       String result="";
      try {
            Connection con = JDBCConnectionManager.getConnection();
            String sql = "Select * from claims where emailAddress=?";

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, emailAddress);

            System.out.println("sql="+preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                
                result = rs.getString("claimStatus");
            }

        } catch (SQLException ex) {
            Logger log = Logger.getLogger(UserService.class.getName());
            log.error(LocalDateTime.now() + "@" + ex);
            ex.printStackTrace();
        }
        System.out.println("check policy status ="+result);
        return result;
    }
}
