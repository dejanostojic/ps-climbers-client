/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dostojic.climbers.view.controller;

import com.dostojic.climbers.domain.User;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author planina
 */
public class ViewController {
    
    private static final String ACTIVE_USER = "activeUser";
    private static final String ACTIVE_USE_CASE = "useCase";
    
    private static ViewController instance;
    
    private final Map<String, Object> sessionAttributes;
    
    private ViewController() {
        sessionAttributes = new HashMap<>();
    }
    
    public static ViewController getInstance() {
        if (instance == null) {
            instance = new ViewController();
        }
        return instance;
    }

    public Map<String, Object> getSessionAttributes() {
        return sessionAttributes;
    }
    
    public void setActiveUser(User user){
        sessionAttributes.put(ACTIVE_USER, user);
    }
    
    public User getActiveUser(){
        return (User) sessionAttributes.get(ACTIVE_USER);
    }
    
    public void setActiveUseCase(String useCase){
        sessionAttributes.put(ACTIVE_USE_CASE, useCase);
    }
    
    public String getActiveUseCase(){
        return  (String) sessionAttributes.get(ACTIVE_USE_CASE);
    }
    
    
}
