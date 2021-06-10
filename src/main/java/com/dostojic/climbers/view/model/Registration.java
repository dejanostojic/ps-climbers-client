/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dostojic.climbers.view.model;

import java.util.Date;

/**
 *
 * @author Dejan.Ostojic
 */
public class Registration {
    
    private Climber climber;
    private Integer startNumber;
    private Integer totalOrd;
    private Boolean paid;
    private Date createdDate;
    private Date paidDate;
    private RegistrationFee registrationFee;

    public Registration() {
        paid = false;
    }

    
    public Climber getClimber() {
        return climber;
    }

    public void setClimber(Climber climber) {
        this.climber = climber;
    }

    public Integer getStartNumber() {
        return startNumber;
    }

    public void setStartNumber(Integer startNumber) {
        this.startNumber = startNumber;
    }

    public Integer getTotalOrd() {
        return totalOrd;
    }

    public void setTotalOrd(Integer totalOrd) {
        this.totalOrd = totalOrd;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

    public RegistrationFee getRegistrationFee() {
        return registrationFee;
    }

    public void setRegistrationFee(RegistrationFee registrationFee) {
        this.registrationFee = registrationFee;
    }

}
