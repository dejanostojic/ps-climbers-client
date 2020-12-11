/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dostojic.climbers.main;

import com.dostojic.climbers.view.coordinator.MainCoordinator;
import jiconfont.icons.google_material_design_icons.GoogleMaterialDesignIcons;
import jiconfont.swing.IconFontSwing;

/**
 *
 * @author planina
 */
public class Main {
    public static void main(String[] args) {
        IconFontSwing.register(GoogleMaterialDesignIcons.getIconFont());
        MainCoordinator.getInstance().openLoginForm();
    }
}
