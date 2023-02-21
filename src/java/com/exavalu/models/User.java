/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exavalu.models;

import com.exavalu.services.EmployeeService;
import com.exavalu.services.PolicyService;
import com.exavalu.services.UnderwriterService;

import com.exavalu.services.UserService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.struts2.dispatcher.ApplicationMap;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author LENOVO
 */
public class User extends ActionSupport implements ApplicationAware, SessionAware, Serializable {

    private String emailAddress, password, firstName, lastName, countryCode, stateCode, distCode;
    private int status, policyStatus, roleId;

    private SessionMap<String, Object> sessionMap = (SessionMap) ActionContext.getContext().getSession();

    private ApplicationMap map = (ApplicationMap) ActionContext.getContext().getApplication();

    @Override
    public void setApplication(Map<String, Object> application) {
        setMap((ApplicationMap) application);
    }

    @Override
    public void setSession(Map<String, Object> session) {
        setSessionMap((SessionMap<String, Object>) (SessionMap) session);
    }

    public String doLogin() throws Exception {
        String result = "FAILURE";

        boolean success = UserService.getInstance().doLogin(this);

        System.out.println("In the user login page");

        if (success) {

            User user = UserService.getInstance().getUser(this.getEmailAddress());

            boolean checkPolicyStatus = UserService.getInstance().checkPolicyStatus(this.getEmailAddress());
            String checkClaimStatus = UserService.getInstance().checkClaimStatus(this.getEmailAddress());
            if (checkClaimStatus.length() > 0) {
                switch (checkClaimStatus) {
                    case "Pending":
                        getSessionMap().put("PolicyApplied", "Pending");
                        break;
                    case "Approved":
                        getSessionMap().put("PolicyApplied", "Approved by Underwriter");
                        break;
                    case "Rejected":
                        getSessionMap().put("PolicyApplied", "Rejected by Underwriter");
                        break;
                    case "Issued":
                        getSessionMap().put("PolicyApplied", "Issued by Insurance Officer");
                        break;
                    default:
                        break;
                }
            }

            if (checkPolicyStatus) {
                getSessionMap().put("PolicyBought", "bought");
            }
            if (user.getRoleId() == 2) {
                ArrayList pendingPolicyList = UnderwriterService.getInstance().getAllPendingClaims();
                sessionMap.put("PendingPolicyList", pendingPolicyList);
            }
            ArrayList policyList = PolicyService.getInstance().getAllPolicies();
            getSessionMap().put("PolicyList", policyList);
            getSessionMap().put("Loggedin", this);
            getSessionMap().put("User", user);
            System.out.println("returning Success from doLogin method");
            System.out.println("logging in");
            result = "SUCCESS";
        } else {
            System.out.println("returning Failure from doLogin method");
            String errorMsg = "Either EmailAddress or Password is wrong";
            Logger log = Logger.getLogger(User.class.getName());
            log.error(LocalDateTime.now() + "Either EmailAddress or Password is wrong");

            getSessionMap().put("ErrorMsg", errorMsg);
        }

        return result;
    }

    public String doPreSignUp() throws Exception {

        String result = "SUCCESS";
        ArrayList countryList = UserService.getInstance().getAllCountries();

        ArrayList stateList = null;
        ArrayList districtList = null;
        getSessionMap().put("CountryList", countryList);
        System.out.println("CountryCode=" + this.getCountryCode());
        System.out.println("StateCode=" + this.getStateCode());
        System.out.println("DistrictCode=" + this.getDistCode());
        if (this.getCountryCode() != null) {
            stateList = UserService.getInstance().getAllStates(this.getCountryCode());
            getSessionMap().put("StateList", stateList);
            getSessionMap().put("User", this);
            result = "STATELIST";

        }

        if (this.getStateCode() != null) {
            districtList = UserService.getInstance().getAlldistricts(this.getStateCode());
            getSessionMap().put("DistList", districtList);
            getSessionMap().put("User", this);
            result = "DISTLIST";

        }
//        if (this.countryCode != null && this.stateCode != null) {
//            districtList = UserService.getInstance().getAllDistricts(this.stateCode);
//            sessionMap.put("DistrictList", districtList);
//            sessionMap.put("User", this);
//
//        }

//        String result = "SUCCESS";
//        ArrayList countryList = UserService.getInstance().getAllCountries();
//        ArrayList stateList = null;
//        ArrayList distList = null;
//        sessionMap.put("CountryList", countryList);
////        System.out.println("CountryCode=" + this.countryCode);
//////        System.out.println("this="+this);
////        System.out.println("StateCode=" + this.stateCode);
////         System.out.println("DistCode=" + this.distCode);
//         
//        
//       if (this.countryCode != null) {
//            stateList = UserService.getInstance().getAllStates(this.countryCode);
//            sessionMap.put("StateList", stateList);
//            
//            sessionMap.put("User", this);
//            result="STATELIST";
//          
//        }
//       
////       if (this.countryCode != null && this.stateCode != null && this.stateCode.length()>0 ) {
////            System.out.print("In the if block");
////            stateList = UserService.getInstance().getAllStates(this.countryCode);
////            sessionMap.put("StateList", stateList);
////            distList = UserService.getInstance().getAlldistricts(this.stateCode);
////            sessionMap.put("DistList", distList);
////            sessionMap.put("User", this);
////        }
//        
//        
//        
        return result;
    }

    public String doLogout() throws Exception {
        String result = "SUCCESS";

        System.out.println("logging out");
        getSessionMap().clear();
        return result;
    }

    public String doSignup() throws Exception {
        String result = "FAILURE";
        boolean success = UserService.getInstance().doSignup(this);
        if (success) {
            result = "SUCCESS";
            String successMsg = "Account Created,Now Login to your Account";

            getSessionMap().put("SignUpSuccessMsg", successMsg);

        } else {
            String errorMsg = "This Email is already registered..Try with another Email";

            getSessionMap().put("SignUpFailureMsg", errorMsg);
            Logger log = Logger.getLogger(User.class.getName());
            log.error(LocalDateTime.now() + "This Email is already registered..Try with another Email");
        }

        return result;
    }

    public String buyPolicy() throws Exception {
        String result = "BOUGHT";

        boolean success = UserService.getInstance().updateUser(this.getEmailAddress());
        if (success) {
            getSessionMap().put("PolicyBought", "bought");
        }

        return result;

    }

    public String applyPolicy() throws Exception {
        String result = "APPLIED";

        User user = UserService.getInstance().getUser(this.getEmailAddress());
        boolean success = UserService.getInstance().ApplyPolicy(this.getEmailAddress(), user.getFirstName() + " " + user.getLastName());
        if (success) {
            getSessionMap().put("PolicyApplied", "Pending");
        }
        return result;

    }

    /**
     * @return the emailAddress
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * @param emailAddress the emailAddress to set
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the countryCode
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * @param countryCode the countryCode to set
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * @return the stateCode
     */
    public String getStateCode() {
        return stateCode;
    }

    /**
     * @param stateCode the stateCode to set
     */
    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    /**
     * @return the distCode
     */
    public String getDistCode() {
        return distCode;
    }

    /**
     * @param distCode the distCode to set
     */
    public void setDistCode(String distCode) {
        this.distCode = distCode;
    }

    /**
     * @return the sessionMap
     */
    public SessionMap<String, Object> getSessionMap() {
        return sessionMap;
    }

    /**
     * @param sessionMap the sessionMap to set
     */
    public void setSessionMap(SessionMap<String, Object> sessionMap) {
        this.sessionMap = sessionMap;
    }

    /**
     * @return the map
     */
    public ApplicationMap getMap() {
        return map;
    }

    /**
     * @param map the map to set
     */
    public void setMap(ApplicationMap map) {
        this.map = map;
    }

    /**
     * @return the policyStatus
     */
    public int getPolicyStatus() {
        return policyStatus;
    }

    /**
     * @param policyStatus the policyStatus to set
     */
    public void setPolicyStatus(int policyStatus) {
        this.policyStatus = policyStatus;
    }

    /**
     * @return the roleId
     */
    public int getRoleId() {
        return roleId;
    }

    /**
     * @param roleId the roleId to set
     */
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

}
