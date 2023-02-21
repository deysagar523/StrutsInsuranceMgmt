/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exavalu.services;

import com.exavalu.models.InsuranceMaster;
import com.exavalu.utils.JDBCConnectionManager;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author LENOVO
 */
public class InsuranceMasterService {

    public static ArrayList getDataFromApi() throws ParseException {
      ArrayList<InsuranceMaster> imList = new ArrayList();
         try {

		URL url = new URL( "https://jsonplaceholder.typicode.com/posts");
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
                    
                    for(int i = 0; i<jsonArray.size(); i++){
                        
                        InsuranceMaster im = new InsuranceMaster();
                        JSONObject obj = (JSONObject)jsonArray.get(i);
                        String id = obj.get("id").toString();
                        String title = obj.get("title").toString();
                        String body = obj.get("body").toString();
                        
                        im.setId(id);
                       im.setTitle(title);
                        im.setBody(body);

                        imList.add(im);
                        
                    }
                    
                    //System.out.println("Size of Poilicy List"+policyList.size());
                }

	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	  }
         return imList;
    }

    public static boolean insertDataInDB(ArrayList ImDetails) {
        boolean result = false;
        try {

            Connection con = JDBCConnectionManager.getConnection();
            String sql = "INSERT INTO ims(id,title,body)" + "VALUES(?,?,?)";

            
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            con.setAutoCommit(false);
            
            Iterator itr = ImDetails.iterator();
            while(itr.hasNext()){
                
                InsuranceMaster im = (InsuranceMaster) itr.next();
                preparedStatement.setString(1,im.getId());
                preparedStatement.setString(2, im.getTitle());
                preparedStatement.setString(3, im.getBody());
                
                preparedStatement.addBatch();
                
               
            }
                int[] count = preparedStatement.executeBatch();
                con.commit();

                if (count.length >= 1) {
                    result = true;
                    System.out.println(count.length);
                }
                
                
                
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
}
