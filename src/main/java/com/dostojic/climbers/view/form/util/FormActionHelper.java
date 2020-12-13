/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dostojic.climbers.view.form.util;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

/**
 *
 * @author planina
 */
public class FormActionHelper {
    /**
     * Attach mouse clicked handler to the component
     * @param eventSource that accepts the event
     * @param handler that handles the event
     */
    public static void addMouseClicked(Component eventSource, Consumer<MouseEvent> handler){
        eventSource.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handler.accept(e);
            }
        });
    }
    
}
