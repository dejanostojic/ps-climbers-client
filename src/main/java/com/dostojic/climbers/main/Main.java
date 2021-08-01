/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dostojic.climbers.main;

import com.dostojic.climbers.view.coordinator.MainCoordinator;
import java.awt.Font;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import jiconfont.icons.google_material_design_icons.GoogleMaterialDesignIcons;
import jiconfont.swing.IconFontSwing;

/**
 *
 * @author planina
 */
public class Main {
    public static void main(String[] args) {
        UIDefaults defaults = UIManager.getDefaults();
        /*
        
        
        System.out.println("UI Defaults: " + UIManager.getDefaults());
                //https://docs.oracle.com/javase/tutorial/uiswing/lookandfeel/_nimbusDefaults.html
        FontUIResource sansSerif24 = new FontUIResource(new Font("SansSerif", 0, 24));
        FontUIResource sansSerif36 = new FontUIResource(new Font("SansSerif", 0, 36));
        
        UIManager.put("RootPane.font", sansSerif24);
        UIManager.put("TextPane.font", sansSerif24);
        UIManager.put("Spinner.font", sansSerif24);
        UIManager.put("Table.font", sansSerif24);
        UIManager.put("Button.font", sansSerif24);
        UIManager.put("Panel.font", sansSerif24);
        UIManager.put("ComboBox.font", sansSerif24);
        UIManager.put("TabbedPane.font", sansSerif24);
        UIManager.put("TableHeader.font", sansSerif36);
        UIManager.put("TextField.font", sansSerif24);
        UIManager.put("Label.font", sansSerif24);
        */
        IconFontSwing.register(GoogleMaterialDesignIcons.getIconFont());
        MainCoordinator.getInstance().openLoginForm();
        
    }
}
