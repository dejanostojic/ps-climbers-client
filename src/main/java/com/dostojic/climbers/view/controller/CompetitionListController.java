/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dostojic.climbers.view.controller;

import com.dostojic.climbers.common.dto.CompetitionSearchCriteriaDto;
import com.dostojic.climbers.communication.Communication;
import com.dostojic.climbers.view.constant.Constants;
import static com.dostojic.climbers.view.constant.Constants.LIST_CLIMBER;
import com.dostojic.climbers.view.coordinator.MainCoordinator;
import com.dostojic.climbers.view.coordinator.Session;
import com.dostojic.climbers.view.form.competition.PanelListCompetitions;
import com.dostojic.climbers.view.form.util.FormMode;
import com.dostojic.climbers.view.model.Competition;
import com.dostojic.climbers.view.model.TableModelCompetitionList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author Dejan.Ostojic
 */
public class CompetitionListController {
    private PanelListCompetitions panelListCompetitions;

    public CompetitionListController(PanelListCompetitions panelListCompetitions) {
        this.panelListCompetitions = panelListCompetitions;
        addActionListeners();
    }

    public void openForm() {
        try {
            prepareView();
            panelListCompetitions.setVisible(true);
            MainCoordinator.getInstance().setMainContent(panelListCompetitions, LIST_CLIMBER);
        } catch (Exception ex) {
            Logger.getLogger(ClimberListController.class.getName()).log(Level.SEVERE, null, ex);
            
            JOptionPane.showMessageDialog(panelListCompetitions, ex.getMessage(), "Error opening competition list form", JOptionPane.ERROR_MESSAGE);

        }
    }
        
    private void addActionListeners() {
        panelListCompetitions.filterSearchActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e) {
                findCompetitionsForSearchCriteria();
            }

            private void findCompetitionsForSearchCriteria() {
                try {
                    JTable table = panelListCompetitions.getTableCompetitionList();
                    String competitionName = panelListCompetitions.getTextFieldFilterName().getText().trim();
                    table.setModel(new TableModelCompetitionList(
                            Communication.getInstance().getCompetitions(
                                    new CompetitionSearchCriteriaDto(competitionName))));
                    table.setRowHeight(32);
                } catch (Exception ex) {
                    Logger.getLogger(CompetitionListController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        
        });
        
        panelListCompetitions.competitionDetailsActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDetails();
            }

            private void showDetails() {
                int selectedRow = panelListCompetitions.getTableCompetitionList().getSelectedRow();
                if (selectedRow != -1) {
                    TableModelCompetitionList model = (TableModelCompetitionList) panelListCompetitions.getTableCompetitionList().getModel();
                    Competition competition = model.getCompetitionAt(selectedRow);
                    System.out.println("selected comp: " + competition);
                    Session.getInstance().addParam(Constants.SELECTED_COMPETITION_ID, competition.getId());
                    MainCoordinator.getInstance().openCompetitionForm(FormMode.FORM_VIEW);
                } else {
                    // TODO: Show message in status bar!
                    JOptionPane.showMessageDialog(panelListCompetitions, "Please select the competition from the table.", "Details error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void prepareView() {
        System.out.println("Fill combo boxes for filtering!");
    }
    
    
}
