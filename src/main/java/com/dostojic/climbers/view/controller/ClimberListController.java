/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dostojic.climbers.view.controller;

import com.dostojic.climbers.communication.Communication;
import static com.dostojic.climbers.view.constant.Constants.LIST_CLIMBER;
import static com.dostojic.climbers.view.constant.Constants.SELECTED_CLIMBER_ID;
import com.dostojic.climbers.view.coordinator.MainCoordinator;
import com.dostojic.climbers.view.coordinator.Session;
import com.dostojic.climbers.view.form.climber.PanelListClimbers;
import com.dostojic.climbers.view.form.util.FormMode;
import com.dostojic.climbers.view.model.Climber;
import com.dostojic.climbers.view.model.TableModelClimberList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author planina
 */
public class ClimberListController {

    private PanelListClimbers panelListClimbers;

    public ClimberListController(PanelListClimbers panelListClimbers) {
        this.panelListClimbers = panelListClimbers;
        addActionListeners();
    }

    public void openForm() {
        try {
            prepareView();
            panelListClimbers.setVisible(true);
            MainCoordinator.getInstance().setMainContent(panelListClimbers, LIST_CLIMBER);
        } catch (Exception ex) {
            Logger.getLogger(ClimberListController.class.getName()).log(Level.SEVERE, null, ex);
            
            JOptionPane.showMessageDialog(panelListClimbers, ex.getMessage(), "Error opening climber list form", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void addActionListeners() {
        panelListClimbers.newClimberAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFormNewClimber();
            }

            private void openFormNewClimber() {
                MainCoordinator.getInstance().openClimberForm(FormMode.FORM_ADD);
            }
        });
        panelListClimbers.climberDetailsAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDetails();
            }

            private void showDetails() {
                int selectedRow = panelListClimbers.getTableClimberList().getSelectedRow();
                if (selectedRow != -1) {
                    TableModelClimberList model = (TableModelClimberList) panelListClimbers.getTableClimberList().getModel();
                    Climber climber = model.getClimberAt(selectedRow);
                    Session.getInstance().addParam(SELECTED_CLIMBER_ID, climber.getId());
                    MainCoordinator.getInstance().openClimberForm(FormMode.FORM_VIEW);
                } else {
                    // TODO: Show message in status bar!
                    JOptionPane.showMessageDialog(panelListClimbers, "Please select the climber from the table.", "Details error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
    }

    private void prepareView() throws Exception {
        JTable table = panelListClimbers.getTableClimberList();
        table.setModel(new TableModelClimberList(Communication.getInstance().getAllClimbers()));
    }

}
