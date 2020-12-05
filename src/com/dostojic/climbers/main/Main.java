/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dostojic.climbers.main;

import com.dostojic.climbers.controller.Controller;
import com.dostojic.climbers.domain.ClimberRepository;
import com.dostojic.climbers.domain.CompetitionRepository;
import com.dostojic.climbers.domain.UserRepository;
import com.dostojic.climbers.repository.ClimberRepositoryInMemoryImpl;
import com.dostojic.climbers.repository.CompetitionRepositoryInMemoryImpl;
import com.dostojic.climbers.repository.UserRepositoryInMemoryImpl;
import com.dostojic.climbers.view.coordinator.MainCoordinator;
import com.dostojic.climbers.view.form.FormLogin;
import com.dostojic.climbers.view.form.MainForm;
import com.dostojic.climbers.view.form.PanelMainContent;
import jiconfont.icons.font_awesome.FontAwesome;
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
