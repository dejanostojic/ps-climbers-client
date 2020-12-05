/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dostojic.climbers.view.coordinator;

import com.dostojic.climbers.domain.User;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author planina
 */
public class Session {
    
    private static Session instance;
    private final Map<String, Object> params;

    private Session() {
        params = new HashMap<>();
    }

    public static Session getInstance() {
        if (instance == null){
            instance = new Session();
        }
        return instance;
    }

    public void addParam(String key, Object param) {
        params.put(key, param);
    }
    // TODO: think abour using Optional, think about exposing concrete methods setActiveUser(User), getActiveUser()
    public Object getParam(String key) {
        return params.get(key);
    }
    
}
