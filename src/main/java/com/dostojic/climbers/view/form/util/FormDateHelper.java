/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dostojic.climbers.view.form.util;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author Dejan.Ostojic
 */
public class FormDateHelper {
    private final JTextField textFieldDate;
    private final JSpinner spinnerHours;
    private final JSpinner spinnerMinutes;
    private final DateFormat df;
    
    public FormDateHelper(JTextField textFieldDate, JSpinner spinnerHours, 
                        JSpinner spinnerMinutes, String dateFormat) {
        this.textFieldDate = textFieldDate;
        this.spinnerHours = spinnerHours;
        this.spinnerMinutes = spinnerMinutes;
        dateFormat = dateFormat == null ? "dd.MM.yyyy" : dateFormat;
        
        df = new SimpleDateFormat(dateFormat + " HH:mm");

        initializeComponents();
    }
    

    private void initializeComponents() {
        setSpinnerHours();
        setSpinnerMinutes();
        
        textFieldDate.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                try {
                    System.out.println("getDate: " + getDate());
                } catch (ParseException ex) {
                    Logger.getLogger(FormDateHelper.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }

            @Override
            public void focusLost(FocusEvent e) {
//                datePickerOpen.closePopup();
//                System.out.println("popup closed");
            }
        });
        
    }
    
    public Date getDate() throws ParseException{
        String date = textFieldDate.getText();
        String hours = (String)spinnerHours.getValue();
        int minutes = (int)spinnerMinutes.getValue();

        String val = String.format("%s %d:%d", date, hours, minutes);
        System.out.println("Parsing: " + val);
        return df.parse(val);
        
        
    }

    private void setSpinnerHours() {
        int value = (int)spinnerHours.getValue();
        SpinnerNumberModel model = new SpinnerNumberModel(value, 0, 23, 1); 
        spinnerHours.setModel(model);
        
        spinnerHours.setEditor(new JSpinner.NumberEditor(spinnerHours, "00"));

    }

    private void setSpinnerMinutes() {
        int value = (int)spinnerMinutes.getValue();
        SpinnerNumberModel model = new SpinnerNumberModel(value, 0, 59, 1); 
        spinnerMinutes.setModel(model);
        
        spinnerMinutes.setEditor(new JSpinner.NumberEditor(spinnerMinutes, "00"));
    }
    
          
    
    

}
