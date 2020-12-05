/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dostojic.climbers.view.controller;

import com.dostojic.climbers.controller.Controller;
import com.dostojic.climbers.domain.Climber;
import com.dostojic.climbers.view.constant.Constants;
import static com.dostojic.climbers.view.constant.Constants.LIST_CLIMBER;
import static com.dostojic.climbers.view.constant.Constants.SELECTED_CLIMBER_ID;
import com.dostojic.climbers.view.coordinator.MainCoordinator;
import com.dostojic.climbers.view.coordinator.Session;
import com.dostojic.climbers.view.form.climber.PanelListClimbers;
import com.dostojic.climbers.view.form.util.FormMode;
import com.dostojic.climbers.view.model.TableModelClimberList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        prepareView();
        panelListClimbers.setVisible(true);
        MainCoordinator.getInstance().setMainContent(panelListClimbers, LIST_CLIMBER);
    }

    private void addActionListeners() {
        System.out.println("todo add listeneras!!!");
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
        panelListClimbers.deleteClimberAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteClimber();
            }

            private void deleteClimber() {
                int selectedRow = panelListClimbers.getTableClimberList().getSelectedRow();
                if (selectedRow != -1) {
                    TableModelClimberList model = (TableModelClimberList) panelListClimbers.getTableClimberList().getModel();
                    Climber climber = model.getClimberAt(selectedRow);
                    String deleteQuestion = "Are you sure you want to delete climber: " + climber.getFirstName() + " " + climber.getLastName();
                    int showConfirmDialog = JOptionPane.showConfirmDialog(panelListClimbers, deleteQuestion, "Confirm delete", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (JOptionPane.YES_OPTION == showConfirmDialog) {
                        Controller.getInstance().delteClimberById(climber.getId());
                        prepareView();
                    }

                } else {
                    // TODO: Show message in status bar!
                    JOptionPane.showMessageDialog(panelListClimbers, "Please select the climber from the table.", "Details error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void prepareView() {
        JTable table = panelListClimbers.getTableClimberList();
        table.setModel(new TableModelClimberList(Controller.getInstance().getAllClimbers()));
    }

}
