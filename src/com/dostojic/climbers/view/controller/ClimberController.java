/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dostojic.climbers.view.controller;

import com.dostojic.climbers.controller.Controller;
import com.dostojic.climbers.domain.Climber;
import static com.dostojic.climbers.view.constant.Constants.FORM_CLIMBER;
import static com.dostojic.climbers.view.constant.Constants.SELECTED_CLIMBER_ID;
import com.dostojic.climbers.view.coordinator.MainCoordinator;
import com.dostojic.climbers.view.coordinator.Session;
import com.dostojic.climbers.view.form.climber.PanelClimber;
import com.dostojic.climbers.view.form.util.FormMode;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;
import javax.swing.JOptionPane;

/**
 *
 * @author planina
 */
public class ClimberController {

    private final PanelClimber panelClimber;
    private Climber climber;

    public ClimberController(PanelClimber panelClimber) {
        this.panelClimber = panelClimber;
        addActionListeners();
    }

    public void openForm(FormMode formMode) {
        prepareView(formMode);
        System.out.println("climber controller open ask main coordinator to set main content!");
        MainCoordinator.getInstance().setMainContent(panelClimber, FORM_CLIMBER);
    }

    private void addActionListeners() {
        panelClimber.buttonCancelAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancel();
            }

            private void cancel() {
                MainCoordinator.getInstance().openPreviousForm();
            }
        });

        panelClimber.buttonEnableChangesAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setupComponents(FormMode.FORM_EDIT);
            }
        });

        panelClimber.buttonDeleteAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteClimber();
            }

            private void deleteClimber() throws HeadlessException {
                String deleteQuestion = "Are you sure you want to delete climber: " + climber.getFirstName() + " " + climber.getLastName();
                int showConfirmDialog = JOptionPane.showConfirmDialog(panelClimber, deleteQuestion, "Confirm delete", JOptionPane.YES_NO_CANCEL_OPTION);
                if (JOptionPane.YES_OPTION == showConfirmDialog) {
                    Controller.getInstance().delteClimberById(climber.getId());
                    JOptionPane.showMessageDialog(panelClimber, "Climber " + climber.getFirstName() + " " + climber.getLastName() + " deleted!", "Deleted climber", JOptionPane.INFORMATION_MESSAGE);
                    MainCoordinator.getInstance().openPreviousForm();
                }

            }
        });

    }

    private void prepareView(FormMode formMode) {
        System.out.println("preparing view for climber : " + formMode);

        setupComponents(formMode);
        if (FormMode.FORM_VIEW.equals(formMode) || FormMode.FORM_EDIT.equals(formMode)) {
            climber = getClimberFromParam();
            fromModelToView(climber);
        }

    }

    private void setupComponents(FormMode formMode) {
        switch (formMode) {
            case FORM_ADD:
                panelClimber.getButtonCancel().setEnabled(true);
                panelClimber.getButtonDelete().setEnabled(false);
                panelClimber.getButtonEdit().setEnabled(false);
                panelClimber.getButtonEnableChanges().setEnabled(false);
                panelClimber.getButtonSave().setEnabled(true);

                break;
            case FORM_VIEW:
                panelClimber.getButtonCancel().setEnabled(true);
                panelClimber.getButtonDelete().setEnabled(true);
                panelClimber.getButtonEdit().setEnabled(false);
                panelClimber.getButtonEnableChanges().setEnabled(true);
                panelClimber.getButtonSave().setEnabled(false);

                setEnabledInputFields(false);
                break;
            case FORM_EDIT:
                panelClimber.getButtonCancel().setEnabled(true);
                panelClimber.getButtonDelete().setEnabled(false);
                panelClimber.getButtonEdit().setEnabled(true);
                panelClimber.getButtonEnableChanges().setEnabled(false);
                panelClimber.getButtonSave().setEnabled(false);

                setEnabledInputFields(true);
                break;
        }
    }

    private void fromModelToView(Climber climber) {
        panelClimber.getTextFirstName().setText(climber.getFirstName());
        panelClimber.getTextLastName().setText(climber.getLastName());
        panelClimber.getTextYearOfBirth().setText(String.valueOf(climber.getYearOfBirth()));
    }

    private Climber getClimberFromParam() {
        Integer climberId = (Integer) Session.getInstance().getParam(SELECTED_CLIMBER_ID);
        Optional<Climber> climber = Controller.getInstance().findClimberById(climberId);
        if (climber.isPresent()) {
            return climber.get();
        } else {
            throw new RuntimeException("Could not load climber!"); // TODO: Throw proper business exception and handle!
        }
    }

    private void setEnabledInputFields(boolean enabled) {
        panelClimber.getTextFirstName().setEnabled(enabled);
        panelClimber.getTextLastName().setEnabled(enabled);
        panelClimber.getTextYearOfBirth().setEnabled(enabled);
    }
}
