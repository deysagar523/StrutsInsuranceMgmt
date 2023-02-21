/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exavalu.services;

/**
 *
 * @author LENOVO
 */
public class ClaimService {
      public static ClaimService claimService = null;

    public static ClaimService getInstance() {
        if (claimService == null) {
            return new ClaimService();
        } else {
            return claimService;
        }
    }
    
    
}
