/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exavalu.services;

import com.exavalu.models.Claim;
import com.exavalu.models.Employee;
import com.exavalu.models.Underwriter;
import com.exavalu.models.User;
import com.exavalu.utils.JDBCConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/**
 *
 * @author LENOVO
 */
public class UnderwriterService {
      public static UnderwriterService underwriterService = null;

    public static UnderwriterService getInstance() {
        if (underwriterService == null) {
            return new UnderwriterService();
        } else {
            return underwriterService;
        }
    }


    public boolean doLoginUnderWriter(Underwriter uw) {
       boolean success = false;
        
        String sql = "Select * from underwriters where emailAddress=? and password=?";
        
        try {
            Connection con = JDBCConnectionManager.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, uw.getEmailAddress());
            ps.setString(2, uw.getPassword());
            
            System.out.println("LoginService :: "+ps);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next())
            {
                success = true;
            }
            
        } catch (SQLException ex) {
           Logger log=Logger.getLogger(UnderwriterService.class.getName());
            log.error(LocalDateTime.now()+ "@"+ex);
            ex.printStackTrace();
        }
        
        
        return success;
    }

    public ArrayList getAllPendingClaims() {
      ArrayList pendingPolicyList = new ArrayList();
        try {
            Connection con = JDBCConnectionManager.getConnection();
            String sql = "select * from claims where claimStatus=?";
                    
            PreparedStatement preparedStatement = con.prepareStatement(sql);

             preparedStatement.setString(1, "Pending");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Claim claim = new Claim();
                claim.setClaimId(rs.getInt("claimId"));
                claim.setClaimStatus(rs.getString("claimStatus"));
                claim.setDriverName(rs.getString("driverName"));
                claim.setEmailAddress(rs.getString("emailAddress"));
                claim.setDate(rs.getString("date"));
               
//                System.out.println(claim.getClaimId());
//                 System.out.println(claim.getClaimStatus());
//                  System.out.println(claim.getDriverName());
//                   System.out.println(claim.getEmailAddress());
//                    System.out.println(claim.getDate());
                pendingPolicyList.add(claim);

            }

        } catch (SQLException ex) {
            Logger log=Logger.getLogger(EmployeeService.class.getName());
            log.error(LocalDateTime.now()+ "@"+ex);
            ex.printStackTrace();
        }
        System.out.println("Number of pending list = " + pendingPolicyList.size());
        return pendingPolicyList;
    }

    public boolean approvePolicy(int claimId) {
        boolean result=true;
        try {
            Connection con = JDBCConnectionManager.getConnection();
            String sql = "UPDATE employeedb.claims\n"
                    + "SET claimStatus = ? where claimId=?";

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, "Approved");
            preparedStatement.setInt(2, claimId);

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
    public boolean rejectPolicy(int claimId) {
        boolean result=true;
        try {
            Connection con = JDBCConnectionManager.getConnection();
            String sql = "UPDATE employeedb.claims\n"
                    + "SET claimStatus = ? where claimId=?";

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, "Rejected");
            preparedStatement.setInt(2, claimId);

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


}
