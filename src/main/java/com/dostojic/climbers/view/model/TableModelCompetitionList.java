/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dostojic.climbers.view.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author planina
 */
public class TableModelCompetitionList extends AbstractTableModel {

    List<Competition> data;
    private final String[] columnNames = new String[]{"Name", "Description", "Event start"};
    
    public TableModelCompetitionList(List<Competition> data) {
        if (data == null) {
            throw new RuntimeException("The data was not provided for model. This is not expected!.");
        }
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Competition row = data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return row.getName();
            case 1:
                return row.getDescription();
            case 2:
                return row.getEventStart();
            default:
                return "n/a";
        }
    }
    
    public void addCompetition(Competition competition) {
        data.add(competition);
        //fireTableDataChanged();
        fireTableRowsInserted(data.size()-1, data.size()-1);
    }

    public Competition getCompetitionAt(int row) {
        return data.get(row);
    }

}
