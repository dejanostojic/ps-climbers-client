/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dostojic.climbers.repository;

import com.dostojic.climbers.domain.Competition;
import com.dostojic.climbers.domain.CompetitionRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author planina
 */
public class CompetitionRepositoryInMemoryImpl implements CompetitionRepository {

    private List<Competition> competitions;

    public CompetitionRepositoryInMemoryImpl() {
        competitions = new ArrayList<>();
        competitions.add(new Competition(1, "Bolder fest Vrsac", 
                java.util.Date.from(Instant.now()), 
                java.util.Date.from(Instant.now()), 
        java.util.Date.from(Instant.now())));
    }

    public List<Competition> getCompetitions() {
        return competitions;
    }

    @Override
    public List<Competition> getAll() {
        return competitions;
    }

}
