/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dostojic.climbers.view.controller;

import com.dostojic.climbers.communication.Communication;
import com.dostojic.climbers.view.constant.Constants;
import com.dostojic.climbers.view.coordinator.MainCoordinator;
import com.dostojic.climbers.view.coordinator.Session;
import com.dostojic.climbers.view.form.competition.PanelCompetition;
import com.dostojic.climbers.view.form.competition.PanelRegFees;
import com.dostojic.climbers.view.form.competition.PanelRegistrations;
import com.dostojic.climbers.view.form.competition.PanelRoutes;
import com.dostojic.climbers.view.form.util.FormMode;
import com.dostojic.climbers.view.model.Competition;
import com.dostojic.climbers.view.model.JDateChooserCellEditorFormatted;
import com.dostojic.climbers.view.model.RegistrationFee;
import com.dostojic.climbers.view.model.TableModelRegistrationFee;
import com.dostojic.climbers.view.model.TableModelRegistrations;
import com.dostojic.climbers.view.model.TableModelRoutes;
import com.toedter.calendar.JDateChooser;
import com.toedter.components.JSpinField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Dejan.Ostojic
 */
public class CompetitionController {

    private final PanelCompetition panelCompetition;
    private Competition competition;
    private PanelRegFees panelRegFees;
    private PanelRoutes panelRoutes;
    private PanelRegistrations panelRegistrations;
    private final String DATE_FORMAT = "dd.MM.yyyy";
    private final DateFormat df;

    public CompetitionController(PanelCompetition panelCompetition) {
        this.panelCompetition = panelCompetition;
        df = new SimpleDateFormat(DATE_FORMAT);

        addActionListeners();
    }

    // TODO: Need to be DRY. Should abstract this to superType and implement only specifics in concrete controller
    public void openForm(FormMode formMode) {
        try {
            prepareView(formMode);
            System.out.println("competition controller open ask main coordinator to set main content!");
            MainCoordinator.getInstance().setMainContent(panelCompetition, Constants.FORM_COMPETITION);
        } catch (Exception ex) {
            Logger.getLogger(CompetitionController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(panelCompetition, ex.getMessage(), "Error opening form.", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addActionListeners() {
        panelCompetition.buttonCancelAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancel();
            }

            private void cancel() {
                MainCoordinator.getInstance().openPreviousForm();
            }
        });

        panelCompetition.buttonSaveAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }

            private void save() {
                try {
                    Competition competition = fromViewToModel();
                    System.out.println("Comp: " + competition);
                    try {
                        Communication.getInstance().saveCompetition(competition);
                        JOptionPane.showMessageDialog(panelCompetition, "Compettition succesfully saved", "Saving competition.", JOptionPane.INFORMATION_MESSAGE);
                        MainCoordinator.getInstance().openListCompetitionsForm();

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(panelCompetition, e.getMessage(), "Error saving competition.", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(panelCompetition, ex.getMessage(), "Error validating form.", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panelCompetition.buttonEnableChangesAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setupComponents(FormMode.FORM_EDIT);
            }
        });
        
        panelCompetition.buttonEditAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                edit();
            }

            private void edit() {
                try {
                    Competition competition = fromViewToModel();
                    System.out.println("Comp: " + competition);
                    try {
                        Communication.getInstance().updateCompetition(competition);
                        JOptionPane.showMessageDialog(panelCompetition, "Compettition succesfully saved", "Saving competition.", JOptionPane.INFORMATION_MESSAGE);
                        MainCoordinator.getInstance().openListCompetitionsForm();

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(panelCompetition, e.getMessage(), "Error saving competition.", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(panelCompetition, ex.getMessage(), "Error validating form.", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private Competition fromViewToModel() throws Exception {
        competition.setName(panelCompetition.getTextName().getText());
        competition.setDescription(panelCompetition.getTextDescription().getText());
        competition.setRegistrationOpen(
                dateFromView(panelCompetition.getDateRegistrationOpen(),
                        panelCompetition.getSpinnerHourRegistrationOpen(),
                        panelCompetition.getSpinnerMinuteRegistrationOpen())
        );

        competition.setRegistrationClose(
                dateFromView(panelCompetition.getDateRegistrationClose(),
                        panelCompetition.getSpinnerHourRegistrationClose(),
                        panelCompetition.getSpinnerMinuteRegistrationClose())
        );

        competition.setEventStart(
                dateFromView(panelCompetition.getDateEventStart(),
                        panelCompetition.getSpinnerHourEventStart(),
                        panelCompetition.getSpinnerMinuteEventStart())
        );

        competition.setRoutes(((TableModelRoutes) panelRoutes.getTableRoutes().getModel()).getData());
        competition.setRegistrationFees(((TableModelRegistrationFee) panelRegFees.getTableRegFees().getModel()).getData());

        return competition;
    }

    private Date dateFromView(JDateChooser dateChooser, JSpinField hoursSpin, JSpinField minutesSpin) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateChooser.getDate());
        cal.set(Calendar.HOUR_OF_DAY, hoursSpin.getValue());
        cal.set(Calendar.MINUTE, minutesSpin.getValue());

        return cal.getTime();
    }

    // TODO: this should be part of the common algorithm
    private void prepareView(FormMode formMode) throws Exception {
        System.out.println("preparing view for competition : " + formMode);
        // set format to date editor
        
        panelCompetition.getDateEventStart().setDateFormatString(DATE_FORMAT);
        panelCompetition.getDateRegistrationOpen().setDateFormatString(DATE_FORMAT);
        panelCompetition.getDateRegistrationClose().setDateFormatString(DATE_FORMAT);
        
       

        setupComponents(formMode); // this shoud be specific and concrete (apart from buttons, they share common functinallity!
        if (FormMode.FORM_VIEW.equals(formMode) || FormMode.FORM_EDIT.equals(formMode)) {
            competition = getCompetitionFromParam();
            fromModelToView(competition);
        } else {
            competition = new Competition();
        }
         addPanelsToTabbedPane(competition);

    }
    
    private void dateToView(Date date, JDateChooser dateChooser, JSpinField hoursSpin, JSpinField minutesSpin) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        
        dateChooser.setDate(date);
        hoursSpin.setValue(cal.get(Calendar.HOUR_OF_DAY));
        minutesSpin.setValue(cal.get(Calendar.MINUTE));
    }


    private void setupComponents(FormMode formMode) {
        switch (formMode) {
            case FORM_ADD:
                panelCompetition.getButtonCancel().setEnabled(true);
                panelCompetition.getButtonDelete().setEnabled(false);
                panelCompetition.getButtonEdit().setEnabled(false);
                panelCompetition.getButtonEnableChanges().setEnabled(false);
                panelCompetition.getButtonSave().setEnabled(true);

                break;
            case FORM_VIEW:
                panelCompetition.getButtonCancel().setEnabled(true);
                panelCompetition.getButtonDelete().setEnabled(true);
                panelCompetition.getButtonEdit().setEnabled(false);
                panelCompetition.getButtonEnableChanges().setEnabled(true);
                panelCompetition.getButtonSave().setEnabled(false);

                setEnabledInputFields(false);
                break;
            case FORM_EDIT:
                panelCompetition.getButtonCancel().setEnabled(true);
                panelCompetition.getButtonDelete().setEnabled(false);
                panelCompetition.getButtonEdit().setEnabled(true);
                panelCompetition.getButtonEnableChanges().setEnabled(false);
                panelCompetition.getButtonSave().setEnabled(false);

                setEnabledInputFields(true);
                break;
        }
    }

    private Competition getCompetitionFromParam() throws Exception {
        Integer competitionId = (Integer) Session.getInstance().getParam(Constants.SELECTED_COMPETITION_ID);
        return Communication.getInstance().findCompetitionById(competitionId);
    }

    private void fromModelToView(Competition competition) {
        panelCompetition.getTextName().setText(competition.getName());
        panelCompetition.getTextDescription().setText(competition.getDescription());

        
        dateToView(competition.getRegistrationOpen(),
                panelCompetition.getDateRegistrationOpen(), 
                panelCompetition.getSpinnerHourRegistrationOpen(), 
                panelCompetition.getSpinnerMinuteRegistrationOpen());
        
        dateToView(competition.getRegistrationClose(),
                panelCompetition.getDateRegistrationClose(), 
                panelCompetition.getSpinnerHourRegistrationClose(), 
                panelCompetition.getSpinnerMinuteRegistrationClose());
        
        dateToView(competition.getEventStart(),
                panelCompetition.getDateEventStart(), 
                panelCompetition.getSpinnerHourEventStart(), 
                panelCompetition.getSpinnerMinuteEventStart());
        
        
        
    }

    private void setEnabledInputFields(boolean enabled) {
        panelCompetition.getTextName().setEnabled(enabled);
        panelCompetition.getTextDescription().setEnabled(enabled);
        panelCompetition.getDateRegistrationOpen().setEnabled(enabled);
        panelCompetition.getDateRegistrationClose().setEnabled(enabled);
        panelCompetition.getDateEventStart().setEnabled(enabled);

        panelCompetition.getSpinnerHourRegistrationOpen().setEnabled(enabled);
        panelCompetition.getSpinnerHourRegistrationClose().setEnabled(enabled);
        panelCompetition.getSpinnerHourEventStart().setEnabled(enabled);

        panelCompetition.getSpinnerMinuteRegistrationOpen().setEnabled(enabled);
        panelCompetition.getSpinnerMinuteRegistrationClose().setEnabled(enabled);
        panelCompetition.getSpinnerMinuteEventStart().setEnabled(enabled);

//        System.out.println("TODO: ADD REST OF THE ENABLED DISABLED FIELDS");
    }

    private void addPanelsToTabbedPane(Competition competition) {
        panelRegFees = setupPanelRegFees(competition);
        panelRoutes = setupPanelRoutes(competition);
        panelRegistrations = setupPanelRegistrations(competition);

        panelCompetition.addPannelsToTabbedPane(panelRegFees, panelRoutes, panelRegistrations);

    }

    private PanelRegFees setupPanelRegFees(Competition competition) {
        PanelRegFees panelRegFees = new PanelRegFees();
        JTable tableRegFees = panelRegFees.getTableRegFees();
        TableModelRegistrationFee model = new TableModelRegistrationFee(competition.getRegistrationFees());
        tableRegFees.setModel(model);
        tableRegFees.setDefaultEditor(Date.class, new JDateChooserCellEditorFormatted(DATE_FORMAT));

        tableRegFees.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer(){
            @Override
            protected void setValue(Object value) {
                 setText((value == null) ? "" : df.format((Date)value));
            }
        });
        tableRegFees.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer(){
            @Override
            protected void setValue(Object value) {
                 setText((value == null) ? "" : df.format((Date)value));
            }
        });
        
        panelRegFees.addRegFeeActionListener((e) -> {
            model.addRow();
        });

        panelRegFees.deleteRegFeeActionListener((e) -> {
            int row = tableRegFees.getSelectedRow();

            if (row != -1) {
                model.deleteRow(row);
            }
        });

//        panelRegFees.setVisible(true);
        return panelRegFees;
    }

    private PanelRoutes setupPanelRoutes(Competition competition) {
        PanelRoutes panelRoutes = new PanelRoutes();

        JTable tableRoutes = panelRoutes.getTableRoutes();
        TableModelRoutes model = new TableModelRoutes(competition.getRoutes());
        tableRoutes.setModel(model);

        tableRoutes.setDefaultEditor(Date.class, new JDateChooserCellEditorFormatted("dd.MM.yyyy"));

        panelRoutes.addRouteActionListener((e) -> {
            model.addRow();
        });

        panelRoutes.deleteRouteActionListener((e) -> {
            int row = tableRoutes.getSelectedRow();

            if (row != -1) {
                model.deleteRow(row);
            }
        });
        return panelRoutes;
    }

    private PanelRegistrations setupPanelRegistrations(Competition competition) {
        PanelRegistrations panelRegistrations = new PanelRegistrations();

        JTable tableRegistrations = panelRegistrations.getTableRegistrations();
        TableModelRegistrations model = new TableModelRegistrations(competition.getRegistrations());
        tableRegistrations.setModel(model);
        
        tableRegistrations.setDefaultEditor(Date.class, new JDateChooserCellEditorFormatted("dd.MM.yyyy"));

        JComboBox<RegistrationFee> registrationFeesCombo = new JComboBox<>();
        
        competition.getRegistrationFees()
                .forEach(registrationFeesCombo::addItem);
        
        tableRegistrations.getColumnModel().getColumn(6)
                .setCellEditor(new DefaultCellEditor(registrationFeesCombo));
        
        panelRegistrations.addRegistrationActionListener((e) -> {
            model.addRow();
        });

        panelRegistrations.deleteRegistrationActionListener((e) -> {
            int row = tableRegistrations.getSelectedRow();

            if (row != -1) {
                model.deleteRow(row);
            }
        });
        return panelRegistrations;
    }

}
