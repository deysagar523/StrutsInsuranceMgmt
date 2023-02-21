/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exavalu.models;

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
public class Underwriter extends ActionSupport implements ApplicationAware, SessionAware, Serializable {

    private String emailAddress;
    private String password;
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

    public String doLoginUnderwriter() throws Exception {
        String result = "FAILURE";

        System.out.println("In the underwriter login page");
        boolean success = UnderwriterService.getInstance().doLoginUnderWriter(this);

        if (success) {
            ArrayList pendingPolicyList = UnderwriterService.getInstance().getAllPendingClaims();
            System.out.println("returning Success from doLogin method in Underwriter");
            sessionMap.put("LoggedinUnderwriter", this);

            result = "SUCCESS";
        } else {
            System.out.println("returning Failure from doLogin method in Underwriter");
            Logger log = Logger.getLogger(Underwriter.class.getName());
            log.error(LocalDateTime.now() + " Wrong Username or Password");
        }

        return result;
    }

    public String getAllPendingClaims() throws Exception {
        String result = "SUCCESS";

        ArrayList pendingPolicyList = UnderwriterService.getInstance().getAllPendingClaims();
         sessionMap.put("PendingPolicyList", pendingPolicyList);
        //System.out.println("returning Success from doLogin method in Underwriter");

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

}
