/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dostojic.climbers.view.form;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author planina
 */
public class FormStyleHelper {

    public static final Color BASE_MENU_COLOR = new Color(169, 184, 210);
    public static final Color MENU_ENTER_COLOR = new Color(169, 184, 210);
    public static final Color TITLE_BAR_BUTTONS_ENTER = new Color(25, 29, 74);

    public static void styleButtonComponent(JComponent component, Color enterColor) {

        component.addMouseListener(
                new ButtonStyleMouseAdapter(enterColor,
                        component.getBackground()));

    }

    public static void styleButtonComponents(List<JComponent> components, Color enterColor) {

        components.stream().forEach(component -> {
            component.addMouseListener(
                    new ButtonStyleMouseAdapter(enterColor,
                            component.getBackground()));

        });

    }
    
    public static void makeComponentTransparent(JComponent component){
        component.setBackground(new Color(255, 255, 255, Math.round(255 * 0.8F)));
    }

    public static class ButtonStyleMouseAdapter extends MouseAdapter {

        private Color enterColor;
        private Color exitColor;

        public ButtonStyleMouseAdapter(Color enterColor, Color exitColor) {
            this.enterColor = enterColor;
            this.exitColor = exitColor;
        }

        @Override
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            Component component = evt.getComponent();
            exitColor = component.getBackground(); // set exit color back to initial
            component.setBackground(enterColor);
            System.out.println("mouse enter: " + component.getName());
        }

        @Override
        public void mouseExited(MouseEvent evt) {
            Component component = evt.getComponent();
            component.setBackground(exitColor);
            System.out.println("mouse exit: " + component.getName());

        }
    }
    
}
