/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dostojic.climbers.repository;

import com.dostojic.climbers.domain.User;
import com.dostojic.climbers.domain.UserRepository;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Korisnik
 */
public class UserRepositoryInMemoryImpl implements UserRepository{
    
    private final List<User> users;

    public UserRepositoryInMemoryImpl() {
        users = new ArrayList<>();
        users.add(new User(1l, "Admin", "Admin", "admin", "admin"));
    }
     
    
    public List<User> getAll() {
        return this.users;
    }
     
}
