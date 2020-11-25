/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dostojic.climbers.controller;

import com.dostojic.climbers.LoginException;
import com.dostojic.climbers.domain.ClimberRepository;
import com.dostojic.climbers.domain.CompetitionRepository;
import com.dostojic.climbers.domain.User;
import com.dostojic.climbers.domain.UserRepository;
import com.dostojic.climbers.repository.ClimberRepositoryInMemoryImpl;
import com.dostojic.climbers.repository.CompetitionRepositoryInMemoryImpl;
import com.dostojic.climbers.repository.UserRepositoryInMemoryImpl;
import java.util.List;

/**
 *
 * @author planina
 */
public class Controller {

    private ClimberRepository climberRepository;
    private CompetitionRepository competitionRepository;
    private UserRepository userRepository;

    private static Controller instance;
    
    private Controller() {
        climberRepository = new ClimberRepositoryInMemoryImpl();
        competitionRepository = new CompetitionRepositoryInMemoryImpl();
        userRepository = new UserRepositoryInMemoryImpl();
    }
    
    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }


    public User login(String username, String password) throws Exception {
        //        new LoginCredentials(usernameField.getText(), String.valueOf(passwordField.getPassword()));

       List<User> users = userRepository.getAll();
        
        for (User user : users) {
            if (user.getUsername().equals(username)) {
              if (user.getPassword().equals(password)){
                  return user;
              }  else {
                  throw new LoginException("Inccorect password"); // TODO: add business exception
              }
            } 
        }
        
        throw new LoginException("Inccorect username");
    }
    
}
