/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dostojic.climbers.domain;

import java.util.Objects;

/**
 *
 * @author planina
 */
public class Route {
    private Competition competition;
    private Integer ord;
    private String name;
    private String grade;

    public Route() {
    }

    public Route(Competition competition, Integer ord, String name, String grade) {
        this.competition = competition;
        this.ord = ord;
        this.name = name;
        this.grade = grade;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public Integer getOrd() {
        return ord;
    }

    public void setOrd(Integer ord) {
        this.ord = ord;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.competition);
        hash = 79 * hash + Objects.hashCode(this.ord);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Route other = (Route) obj;
        if (!Objects.equals(this.competition, other.competition)) {
            return false;
        }
        if (!Objects.equals(this.ord, other.ord)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Route{" + "competition=" + competition + ", ord=" + ord + ", name=" + name + ", grade=" + grade + '}';
    }
    
    
}
