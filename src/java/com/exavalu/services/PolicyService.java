/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exavalu.services;

import com.exavalu.models.Country;
import com.exavalu.models.Policy;
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
public class PolicyService {
     public static PolicyService policyService = null;
    
    private PolicyService(){}
    
    public static PolicyService getInstance()
    {
        if(policyService==null)
        {
            return new PolicyService();
        }
        else
        {
            return policyService;
        }
    }

    public ArrayList getAllPolicies() {
       ArrayList polList = new ArrayList();
        try {

            Connection con = JDBCConnectionManager.getConnection();

            String sql = "Select * from policy";

            PreparedStatement preparedStatement = con.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Policy pol =new Policy();
                
                pol.setPolicyName(rs.getString("policyName"));
                pol.setPolicyId(rs.getInt("policyId"));
                pol.setSumAssured(rs.getInt("sumAssured"));
                pol.setTime(rs.getString("time"));
                

                polList.add(pol);
               

            }

        } catch (SQLException ex) {
            Logger log=Logger.getLogger(UserService.class.getName());
            log.error(LocalDateTime.now()+ "@"+ex);
            ex.printStackTrace();
        }

        
        return polList;
    }
}
