/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dostojic.climbers.repository;

import com.dostojic.climbers.domain.Climber;
import com.dostojic.climbers.domain.ClimberRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author planina
 */
public class ClimberRepositoryInMemoryImpl implements ClimberRepository {

    private List<Climber> climbers;

    public ClimberRepositoryInMemoryImpl() {
        climbers = new ArrayList<>();
        climbers.add(new Climber(1, "Ivana", "Samardzija Ostojic", 1984));
        climbers.add(new Climber(2, "Stasa", "Gejo", 1990));
    }

    public List<Climber> getClimbers() {
        return climbers;
    }

    @Override
    public List<Climber> getAll() {
        return climbers;
    }

    @Override
    public void deleteById(Integer id) {
        Optional<Climber> climber = climbers.stream().filter(c -> c.getId().equals(id)).findFirst();
        if (climber.isPresent()){
            climbers.remove(climber.get());
        }
    }

}
