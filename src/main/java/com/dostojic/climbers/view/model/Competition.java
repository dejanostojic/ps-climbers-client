/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dostojic.climbers.view.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Dejan.Ostojic
 */
public class Competition {
    private Integer id;
    private String name;
    private String description;
    private Date registrationOpen;
    private Date registrationClose;
    private Date eventStart;
    private List<Route> routes;
    private List<RegistrationFee> registrationFees;
    private List<Registration> registrations;

    public Competition() {
        routes = new ArrayList<>();
        registrationFees = new ArrayList<>();
        registrations = new ArrayList();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getRegistrationOpen() {
        return registrationOpen;
    }

    public void setRegistrationOpen(Date registrationOpen) {
        this.registrationOpen = registrationOpen;
    }

    public Date getRegistrationClose() {
        return registrationClose;
    }

    public void setRegistrationClose(Date registrationClose) {
        this.registrationClose = registrationClose;
    }

    public Date getEventStart() {
        return eventStart;
    }

    public void setEventStart(Date eventStart) {
        this.eventStart = eventStart;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public List<RegistrationFee> getRegistrationFees() {
        return registrationFees;
    }

    public void setRegistrationFees(List<RegistrationFee> registrationFees) {
        this.registrationFees = registrationFees;
    }

    public List<Registration> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(List<Registration> registrations) {
        this.registrations = registrations;
    }

    @Override
    public String toString() {
        return "Competition{" + "id=" + id + ", name=" + name + ", description=" + description + ", registrationOpen=" + registrationOpen + ", registrationClose=" + registrationClose + ", eventStart=" + eventStart + ", routes=" + routes + ", registrationFees=" + registrationFees + '}';
    }
    
    
}
