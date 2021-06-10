/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dostojic.climbers.view.controller;

import com.dostojic.climbers.common.dto.ClimberSearchCriteriaDto;
import com.dostojic.climbers.communication.Communication;
import com.dostojic.climbers.view.controller.validators.ComponentValidator;
import com.dostojic.climbers.view.controller.validators.ComponentValidator.FormValidator;
import com.dostojic.climbers.view.form.competition.PanelChooseClimber;
import com.dostojic.climbers.view.form.competition.PanelRegistrationDetails;
import com.dostojic.climbers.view.form.competition.PanelRegistrations;
import com.dostojic.climbers.view.form.util.FormMode;
import com.dostojic.climbers.view.model.Climber;
import com.dostojic.climbers.view.model.Competition;
import com.dostojic.climbers.view.model.JDateChooserCellEditorFormatted;
import com.dostojic.climbers.view.model.Registration;
import com.dostojic.climbers.view.model.RegistrationFee;
import com.dostojic.climbers.view.model.TableModelClimberList;
import com.dostojic.climbers.view.model.TableModelRegistrations;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author Dejan.Ostojic
 */
public class CompetitionRegistrationController {

    private final PanelRegistrations panelRegistrations;
    private final CompetitionController parentController;
    private JDialog dialogRegistrations;
    private PanelRegistrationDetails panelRegistrationDetails;
    private Integer editingRowIndex;
    private JDialog chooseClimberDialog;
    private TableModelRegistrations tableModelRegistrations;
    private Registration registration;

    public CompetitionRegistrationController(CompetitionController parentController, PanelRegistrations panelRegistrations) {
        this.panelRegistrations = panelRegistrations;
        this.parentController = parentController;
        prepareRegistrationTab();
    }

    private void prepareRegistrationTab() {
        setupPanelRegistrations();
    }

    public PanelRegistrations getPanelRegistrations() {
        return panelRegistrations;
    }

    /*
    public void openForm(FormMode formMode) {
        try {
            prepareView(formMode);
            System.out.println("climber controller open ask main coordinator to set main content!");
        } catch (Exception ex) {
            Logger.getLogger(ClimberController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(panelRegistrationDetails, ex.getMessage(), "Error opening form.", JOptionPane.ERROR_MESSAGE);
        }
    }
     */
    private void setupPanelRegistrations() {

        Competition competition = parentController.getCompetition();
        JTable tableRegistrations = panelRegistrations.getTableRegistrations();
        tableModelRegistrations = new TableModelRegistrations(competition.getRegistrations());
        tableRegistrations.setModel(tableModelRegistrations);

        tableRegistrations.setDefaultEditor(Date.class, new JDateChooserCellEditorFormatted("dd.MM.yyyy"));

        JComboBox<RegistrationFee> registrationFeesCombo = new JComboBox<>();

        competition.getRegistrationFees()
                .forEach(registrationFeesCombo::addItem);

        tableRegistrations.getColumnModel().getColumn(6)
                .setCellEditor(new DefaultCellEditor(registrationFeesCombo));

        panelRegistrations.addRegistrationActionListener((e) -> {
            openRegistrationDetails(FormMode.FORM_ADD);
        });

        panelRegistrations.updateRegistrationActionListener((e) -> {

            editingRowIndex = tableRegistrations.getSelectedRow();

            if (editingRowIndex != -1) {

                registration = tableModelRegistrations.getObjectAt(editingRowIndex);
                openRegistrationDetails(FormMode.FORM_EDIT, registration);

            } else {
                // TODO: Show message in status bar!
                JOptionPane.showMessageDialog(tableRegistrations, "Please select the climber from the table.", "Details error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panelRegistrations.deleteRegistrationActionListener((e) -> {
            int row = tableRegistrations.getSelectedRow();

            if (row != -1) {
                tableModelRegistrations.deleteRow(row);
            }
        });

    }

    private void prepareChooseClimberView(PanelChooseClimber panelChooseClimber) throws Exception {
        System.out.println("set table model for climbrs");
        JTable table = panelChooseClimber.getTableClimberList();
        System.out.println("MODEL SET! SHOW CLIMBERS IN THE TABLE!");
        String firstName = panelRegistrations.getTextFilterClimberName().getText().trim();
        List<Climber> climbers = Communication.getInstance().getClimbers(
                            new ClimberSearchCriteriaDto(firstName, null));
        if(climbers.isEmpty()){
            JOptionPane.showMessageDialog(panelRegistrations, "System con not find climbers for search critera.", "No search results", JOptionPane.ERROR_MESSAGE);
        }
        table.setModel(new TableModelClimberList(climbers));
        table.setRowHeight(32);

    }

    private void openRegistrationDetails(FormMode formMode) {
        openRegistrationDetails(formMode, null);
    }

    private void openRegistrationDetails(FormMode formMode, Registration registration) {

//            model.addRow();
        dialogRegistrations = new JDialog((JDialog) null, "Add new registration", true);
        panelRegistrationDetails = new PanelRegistrationDetails();

        setupRegistrationDetailsComponents(formMode);

        addActionListenersForPanelRegistrationDetails();

        fromModelToView(registration);

        dialogRegistrations.add(panelRegistrationDetails);
        dialogRegistrations.pack();
        dialogRegistrations.setVisible(true);
    }

    private void setupRegistrationDetailsComponents(FormMode formMode) {

        panelRegistrationDetails.getTextStartNumber().setEnabled(false);
        panelRegistrationDetails.getDateCreated().setEnabled(false);
        panelRegistrationDetails.getDatePaid().setEnabled(false);
        panelRegistrationDetails.getComboClimber().setEnabled(false);

        switch (formMode) {
            case FORM_ADD:
                panelRegistrationDetails.getButtonCancel().setEnabled(true);
                panelRegistrationDetails.getButtonDelete().setEnabled(false);
                panelRegistrationDetails.getButtonEdit().setEnabled(false);
                panelRegistrationDetails.getButtonEnableChanges().setEnabled(false);
                panelRegistrationDetails.getButtonSave().setEnabled(true);

                break;
            case FORM_VIEW:
                panelRegistrationDetails.getButtonCancel().setEnabled(true);
                panelRegistrationDetails.getButtonDelete().setEnabled(true);
                panelRegistrationDetails.getButtonEdit().setEnabled(false);
                panelRegistrationDetails.getButtonEnableChanges().setEnabled(true);
                panelRegistrationDetails.getButtonSave().setEnabled(false);

                setEnabledInputFields(panelRegistrationDetails, false);
                break;
            case FORM_EDIT:
                panelRegistrationDetails.getButtonCancel().setEnabled(true);
                panelRegistrationDetails.getButtonDelete().setEnabled(false);
                panelRegistrationDetails.getButtonEdit().setEnabled(true);
                panelRegistrationDetails.getButtonEnableChanges().setEnabled(false);
                panelRegistrationDetails.getButtonSave().setEnabled(false);

                setEnabledInputFields(panelRegistrationDetails, true);
                break;
        }
    }

    private void setEnabledInputFields(PanelRegistrationDetails panelRegistrationDetails, boolean enabled) {
        panelRegistrationDetails.getComboRegistrationFees().setEnabled(enabled);
        panelRegistrationDetails.getButtonFindClimber().setEnabled(enabled);

    }

    private void addActionListenersForPanelRegistrationDetails() {

        FormValidator registrationDetailsValidator = createValidationForRegistrationDetails();

        panelRegistrationDetails.addFindClimberActionListener((findClimberEvent) -> {
            PanelChooseClimber panelChooseClimber = new PanelChooseClimber();

            chooseClimberDialog = new JDialog(dialogRegistrations, "Choose climber to register", true);

            try {
                prepareChooseClimberView(panelChooseClimber);
                // TODO move this to separate controler only concerned with registrations for competition. Too much logic for single class!
                panelChooseClimber.getButtonAdd().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectClimber();
                    }

                    private void selectClimber() {
                        int selectedRow = panelChooseClimber.getTableClimberList().getSelectedRow();

                        if (selectedRow != -1) {
                            TableModelClimberList model = (TableModelClimberList) panelChooseClimber.getTableClimberList().getModel();
                            Climber climber = model.getClimberAt(selectedRow);
                            JComboBox<Climber> comboClimber = panelRegistrationDetails.getComboClimber();
                            comboClimber.removeAllItems();
                            comboClimber.addItem(climber);
                            panelRegistrationDetails.getComboClimber().setSelectedItem(climber);
                            chooseClimberDialog.dispose();
                        } else {
                            // TODO: Show message in status bar!
                            JOptionPane.showMessageDialog(panelChooseClimber, "Please select the climber from the table.", "Details error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                chooseClimberDialog.add(panelChooseClimber);
                chooseClimberDialog.pack();
                chooseClimberDialog.setVisible(true);

            } catch (Exception ex) {
                Logger.getLogger(CompetitionController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(panelRegistrations, ex.getMessage(), "Error opening choose climbers form.", JOptionPane.ERROR_MESSAGE);

            }

        });

        // standard buttons
        panelRegistrationDetails.buttonCancelAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancel();
            }

            private void cancel() {
                dialogRegistrations.dispose();
            }
        });

        panelRegistrationDetails.buttonEnableChangesAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setupRegistrationDetailsComponents(FormMode.FORM_EDIT);
            }
        });

        panelRegistrationDetails.buttonEditAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                edit();
            }

            private void edit() {
                try {

                    boolean valid = registrationDetailsValidator.validate();

                    if (!valid) {
                        dialogRegistrations.pack();
                        return;
                    }

                    Registration registration = fromViewToModel();
                    try {
                        tableModelRegistrations.updateRow(registration, editingRowIndex);

                        JOptionPane.showMessageDialog(panelRegistrationDetails, "Registration succesfully edited", "Editing registration.", JOptionPane.INFORMATION_MESSAGE);
                        dialogRegistrations.dispose();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(panelRegistrationDetails, e.getMessage(), "Error editing registration.", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(panelRegistrationDetails, ex.getMessage(), "Error validating form registration.", JOptionPane.ERROR_MESSAGE);
                }
            }

        });

        panelRegistrationDetails.buttonSaveAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }

            private void save() {
                try {

                    System.out.println("save panel registration");
                    System.out.println("validate");
                    boolean valid = registrationDetailsValidator.validate();

                    if (!valid) {
                        System.out.println("is not valid");
                        dialogRegistrations.pack();
                        return;
                    }
                    System.out.println("is  valid");

                    Registration registration = fromViewToModel();
                    try {

                        tableModelRegistrations.addRow(registration);

//                        JOptionPane.showMessageDialog(panelRegistrationDetails, "Registration succesfully saved", "Saving climber.", JOptionPane.ERROR_MESSAGE);
                        dialogRegistrations.dispose();

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(panelRegistrationDetails, e.getMessage(), "Error saving climber.", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(panelRegistrationDetails, ex.getMessage(), "Error validating form.", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panelRegistrationDetails.buttonDeleteAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delete();
            }

            private void delete() {
                Registration registration = fromViewToModel();
                String deleteQuestion = "Are you sure you want to delete registration for climber: " + registration.getClimber().getFirstName() + " " + registration.getClimber().getLastName();
                int showConfirmDialog = JOptionPane.showConfirmDialog(panelRegistrationDetails, deleteQuestion, "Confirm delete", JOptionPane.YES_NO_CANCEL_OPTION);
                if (JOptionPane.YES_OPTION == showConfirmDialog) {
                    try {
                        tableModelRegistrations.deleteRow(editingRowIndex);
//                        JOptionPane.showMessageDialog(panelRegistrationDetails, "Climber " + climber.getFirstName() + " " + climber.getLastName() + " deleted!", "Deleted climber", JOptionPane.INFORMATION_MESSAGE);
                        dialogRegistrations.dispose();
                    } catch (Exception ex) {
                        Logger.getLogger(ClimberController.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(panelRegistrationDetails, ex.getMessage(), "Error deleting climber.", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

    }

    public FormValidator createValidationForRegistrationDetails() {
        List<ComponentValidator> validators = new ArrayList<>();
        ComponentValidator<JComboBox<Climber>> comboClimbers
                = new ComponentValidator<JComboBox<Climber>>(panelRegistrationDetails.getComboClimber(),
                        panelRegistrationDetails.getLabelComboClimber())
                        .addRule((comp) -> {
                            System.out.println("Cheking combo climber. SelectedItem: " + comp.getSelectedItem());
                            System.out.println("Cheking combo climber. SelectedIndex: " + comp.getSelectedIndex());
                            return comp.getSelectedIndex() != -1;
                        }, "You must select climber!");
        validators.add(comboClimbers);

        ComponentValidator<JComboBox<RegistrationFee>> comboFees
                = new ComponentValidator<JComboBox<RegistrationFee>>(panelRegistrationDetails.getComboRegistrationFees(),
                        panelRegistrationDetails.getLabelComboRegistrationFee())
                        .addRule((comp) -> {
                            System.out.println("Cheking combo reg fee. SelectedItem: " + comp.getSelectedItem());
                            System.out.println("Cheking combo reg fee. SelectedIndex: " + comp.getSelectedIndex());
                            return comp.getSelectedIndex() != -1;
                        }, "You must select registration fee!");
        validators.add(comboFees);

        FormValidator formValidator = new FormValidator(validators);
        
        return formValidator;

    }

    private Registration fromViewToModel() {
        Registration registration = new Registration();
        registration.setClimber((Climber) panelRegistrationDetails.getComboClimber().getSelectedItem());
        registration.setStartNumber(Integer.parseInt(panelRegistrationDetails.getTextStartNumber().getText()));
        registration.setPaid(panelRegistrationDetails.getCheckPaid().isSelected());
        registration.setCreatedDate(panelRegistrationDetails.getDateCreated().getDate());
        registration.setPaidDate(panelRegistrationDetails.getDatePaid().getDate());
        registration.setRegistrationFee((RegistrationFee) panelRegistrationDetails.getComboRegistrationFees().getSelectedItem());
        return registration;
    }

    private void fromModelToView(Registration registration) {
        if (registration == null) {

            registration = new Registration();
            registration.setCreatedDate(new Date());
            registration.setStartNumber(tableModelRegistrations.getRowCount() + 1);

        }

        panelRegistrationDetails.getComboClimber().addItem(registration.getClimber());
        panelRegistrationDetails.getComboClimber().setSelectedIndex(0);
        panelRegistrationDetails.getTextStartNumber().setText("" + registration.getStartNumber());
        panelRegistrationDetails.getCheckPaid().setSelected(registration.getPaid());
        panelRegistrationDetails.getDateCreated().setDate(registration.getCreatedDate());
        panelRegistrationDetails.getDatePaid().setDate(registration.getPaidDate());

        parentController.getCompetition().getRegistrationFees().forEach(fee
                -> panelRegistrationDetails.getComboRegistrationFees().addItem(fee));

        panelRegistrationDetails.getComboRegistrationFees().setSelectedItem(registration.getRegistrationFee());

    }
}
