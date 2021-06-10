/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dostojic.climbers.view.controller;

import com.dostojic.climbers.view.constant.Constants;
import com.dostojic.climbers.view.coordinator.MainCoordinator;
import com.dostojic.climbers.view.coordinator.Session;
import com.dostojic.climbers.view.form.MainForm;
import com.dostojic.climbers.view.form.PanelMainContent;
import com.dostojic.climbers.view.form.util.FormMode;
import com.dostojic.climbers.view.model.User;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import javax.swing.JScrollPane;

/**
 *
 * @author planina
 */
public class MainController {

    private final MainForm mainForm;
    private final Map<String,Component> forms; // TODO, this should go to session probably
    private final LinkedList<HistoryItem> formList; // TODO, this should go to session probably
    
    public MainController(MainForm mainForm) {
        this.mainForm = mainForm;
        forms = new HashMap<>();
        formList = new LinkedList<>();
        addActionListeners();
    }

    public void openForm() {
        User user = (User) Session.getInstance().getParam(Constants.CURRENT_USER);
        System.out.println("TODO! ADD USER IN STATUS BAR!!!");
        mainForm.getLabelTitle().setText("Climbing competitions app (" + user.getFirstName() + " " + user.getLastName() + ")");
//        mainForm.getLblCurrentUser().setText(user.getFirstname() + ", " + user.getLastname());
        mainForm.setVisible(true);
    }

    private void addActionListeners() {
        
        
        mainForm.menuPanelClimbersMainAddMouseClicked((MouseEvent e) -> {
            MainCoordinator.getInstance().openListClimberForm();
        });
        
        mainForm.menuNewClimberAddMouseClicked((MouseEvent e) -> {
            MainCoordinator.getInstance().openClimberForm(FormMode.FORM_ADD);
        });
        
        mainForm.menuSearchClimberAddMouseClicked((MouseEvent e) -> {
            MainCoordinator.getInstance().openListClimberForm();
        });
        
        mainForm.menuPanelCompetitonMainAddMouseClicked((MouseEvent e) -> {
            MainCoordinator.getInstance().openListCompetitionsForm();
        }); 
        
        mainForm.menuNewCompetitionAddMouseClicked((MouseEvent e) -> {
            MainCoordinator.getInstance().openCompetitionForm(FormMode.FORM_ADD);
        });
        
        mainForm.menuSearchCompetitionAddMouseClicked((MouseEvent e) -> {
            MainCoordinator.getInstance().openListCompetitionsForm();
        });
        
    }

    public MainForm getMainForm() {
        return mainForm;
    }
   
    public void setMainComponent(Component form, String formName){
        // TODO: maybe allways save in context the new component, so on cancel button
        // or in save , close we can present back the previous component, handle this!!!
        PanelMainContent panelMainContent = mainForm.getPanelMainContent();
        panelMainContent.removeAll();
        
        JScrollPane scrollPanel = new JScrollPane(form);
        scrollPanel.getVerticalScrollBar().setUnitIncrement(16);
        forms.put(formName, form);
        formList.add(new HistoryItem(form, formName));
        panelMainContent.removeAll();
        panelMainContent.revalidate(); // added
        panelMainContent.repaint(); // added
        panelMainContent.add(scrollPanel);
        panelMainContent.revalidate();
        panelMainContent.repaint();
        Session.getInstance().addParam(Constants.ACTIVE_FORM, formName);
//        panelMainContent.repaint(); // added
    }

    
    public void setMainComponent(String formName){ 
       Component form = forms.get(formName);
        // TODO: maybe allways save in context the new component, so on cancel button
        // or in save , close we can present back the previous component, handle this!!!
        setMainComponent(form, formName);
    }
    
    // TODO: Too ugly. Refactor. Rethink. CREATE NAVIGATION / HISTORY class with responsibility of managing history
    public void openPreviousForm(){ 
       forms.remove((String) Session.getInstance().getParam(Constants.ACTIVE_FORM));
        formList.removeLast();

        if (!formList.isEmpty()){

            HistoryItem prev = formList.getLast();
            setMainComponent(prev.component, prev.formName);
        } else {
            // remove all
            PanelMainContent panelMainContent = mainForm.getPanelMainContent();
            panelMainContent.removeAll();
            panelMainContent.revalidate(); // added
            panelMainContent.repaint(); // added
        }
    }
    
    public static class HistoryItem {
        public Component component;
        public String formName;

        public HistoryItem(Component component, String formName) {
            this.component = component;
            this.formName = formName;
        }
        
    }


}
