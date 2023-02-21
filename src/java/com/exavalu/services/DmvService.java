/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exavalu.services;

import com.exavalu.models.APIUser;
import com.exavalu.models.Dmv;
import com.exavalu.models.Employee;
import com.exavalu.models.User;
import com.exavalu.utils.JDBCConnectionManager;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author LENOVO
 */
public class DmvService {

    public static ArrayList getDataFromApi() throws ParseException {
               //String result = null;
     ArrayList<Dmv> dmvList = new ArrayList();
         try {

		URL url = new URL( "https://jsonplaceholder.typicode.com/users");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");

		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
		}
                else{
                    
                    String inline = "";
                    Scanner scanner = new Scanner(url.openStream());
                    while(scanner.hasNext()){
                        inline += scanner.nextLine();
                    }
                    scanner.close();
                    JSONParser parse = new JSONParser();
                    JSONArray jsonArray = (JSONArray)parse.parse(inline);
                    
                    //JSONArray jsonArray = data_obj.getJSONArra("languages");
                    
                    for(int i = 0; i<jsonArray.size(); i++){
                        
                        Dmv dmv = new Dmv();
                        JSONObject obj = (JSONObject)jsonArray.get(i);
                        String driverId = obj.get("id").toString();
                        String driverName = obj.get("name").toString();
                        String email = obj.get("email").toString();
                        String carModel = obj.get("website").toString();
                        String licenseNumber = obj.get("phone").toString();
                        
                        dmv.setDriverId(driverId);
                        dmv.setDriverName(driverName);
                        dmv.setEmail(email);
                        dmv.setLicenseNumber(licenseNumber);
                        dmv.setCarModel(carModel);

                        dmvList.add(dmv);
                        
                    }
                    
                    System.out.println("Size of DMV List"+dmvList.size());
                }

	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	  }
         return dmvList;
    }

    public static boolean insertDataInDB(ArrayList dmvList) {
       boolean result = false;
        try {

            Connection con = JDBCConnectionManager.getConnection();
            String sql = "INSERT INTO dmv(driverId, driverName, email, licenseNumber, carModel)" +"VALUES(?,?,?,?,?)";

            
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            con.setAutoCommit(false);

            Iterator itr = dmvList.iterator();
            while(itr.hasNext()){
                
                Dmv dmv = (Dmv) itr.next();
                preparedStatement.setString(1,dmv.getDriverId());
                preparedStatement.setString(2, dmv.getDriverName());
                preparedStatement.setString(3, dmv.getEmail());
                preparedStatement.setString(4, dmv.getLicenseNumber());
                preparedStatement.setString(5, dmv.getCarModel());
                
                preparedStatement.addBatch();
                
               
            }
                int[] count = preparedStatement.executeBatch();
                con.commit();

                if (count.length >= 1) {
                    result = true;
                    //System.out.println(count.length);
                }
                
                
                
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }

    public static ArrayList getAllData() {
        ArrayList dmvList = new ArrayList();
        try {
            Connection con = JDBCConnectionManager.getConnection();
            String sql = "select * from dmv";
                   
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Dmv dmv = new Dmv();
                dmv.setDriverId(rs.getString("driverId"));
                dmv.setDriverName(rs.getString("driverName"));
                dmvList.add(dmv);

            }

        } catch (SQLException ex) {
            Logger log=Logger.getLogger(EmployeeService.class.getName());
            log.error(LocalDateTime.now()+ "@"+ex);
            ex.printStackTrace();
        }
        //System.out.println("Number of employees = " + empList.size());
        return dmvList;
    }

    public static Dmv getData(String email) {
       Dmv dmv = new Dmv();
        Connection con = JDBCConnectionManager.getConnection();
        try {

            String sql = "Select * from dmv where email=?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, email);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {

                
                dmv.setDriverId(rs.getString("driverId"));
                dmv.setDriverName(rs.getString("driverName"));
                dmv.setCarModel(rs.getString("carModel"));
                 dmv.setLicenseNumber(rs.getString("licenseNumber"));
                  dmv.setEmail(rs.getString("email"));
                
            }

        } catch (SQLException ex) {

            Logger log = Logger.getLogger(UserService.class.getName());
            log.error(LocalDateTime.now() + "@" + ex);
            ex.printStackTrace();
        }

        return dmv;
    }
    
}
