/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dostojic.climbers.view.coordinator;

import com.dostojic.climbers.view.controller.ClimberController;
import com.dostojic.climbers.view.controller.ClimberListController;
import com.dostojic.climbers.view.controller.LoginController;
import com.dostojic.climbers.view.controller.MainController;
import com.dostojic.climbers.view.form.FormLogin;
import com.dostojic.climbers.view.form.MainForm;
import com.dostojic.climbers.view.form.climber.PanelClimber;
import com.dostojic.climbers.view.form.climber.PanelListClimbers;
import com.dostojic.climbers.view.form.util.FormMode;
import java.awt.Component;

/**
 *
 * @author planina
 */
public class MainCoordinator {

    private static MainCoordinator instance;

    private final MainController mainController;

    private MainCoordinator() {
        mainController = new MainController(new MainForm());
    }

    public static MainCoordinator getInstance() {
        if (instance == null) {
            instance = new MainCoordinator();
        }
        return instance;
    }

    public void openLoginForm() {
        LoginController loginController = new LoginController(new FormLogin());
        loginController.openForm();
    }

    public void openMainForm() {
        mainController.openForm();
    }

    public void openClimberForm(FormMode formMode) {
        System.out.println("adding panel climber main coordinator!");
        ClimberController climberController = new ClimberController(new PanelClimber());
        climberController.openForm(formMode);
    }

    public void openListClimberForm() {
        System.out.println("adding panel climber main coordinator!");
        ClimberListController climberController = new ClimberListController(new PanelListClimbers());
        climberController.openForm();
    }

    public void setMainContent(Component panel, String formName) {
        System.out.println("main coordinator set main content: " + formName);
        mainController.setMainComponent(panel, formName);
    }
    
    public void openPreviousForm(){
//        mainController.setMainComponent((String) Session.getInstance().getParam(Constants.ACTIVE_FORM));
        mainController.openPreviousForm();
    }
}
