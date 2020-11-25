/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dostojic.climbers.domain;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author planina
 */
public class RouteClimbed {
    private Registration registration;
    private Route route;
    private Integer fromAttempt;
    private BigDecimal points;

    public RouteClimbed() {
    }

    public RouteClimbed(Registration registration, Route route, Integer fromAttempt, BigDecimal points) {
        this.registration = registration;
        this.route = route;
        this.fromAttempt = fromAttempt;
        this.points = points;
    }

    public BigDecimal getPoints() {
        return points;
    }

    public void setPoints(BigDecimal points) {
        this.points = points;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Integer getFromAttempt() {
        return fromAttempt;
    }

    public void setFromAttempt(Integer fromAttempt) {
        this.fromAttempt = fromAttempt;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.registration);
        hash = 89 * hash + Objects.hashCode(this.route);
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
        final RouteClimbed other = (RouteClimbed) obj;
        if (!Objects.equals(this.registration, other.registration)) {
            return false;
        }
        if (!Objects.equals(this.route, other.route)) {
            return false;
        }
        return true;
    }
    
    
}
