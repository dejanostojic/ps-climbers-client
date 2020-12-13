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
public class TableModelClimberList extends AbstractTableModel {

    List<Climber> data;
    private final String[] columnNames = new String[]{"Fist Name", "Last name", "Year of birth"};
    
    public TableModelClimberList(List<Climber> data) {
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
        Climber row = data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return row.getFirstName();
            case 1:
                return row.getLastName();
            case 2:
                return row.getYearOfBirth();
            default:
                return "n/a";
        }
    }
    
    public void addClimber(Climber climber) {
        data.add(climber);
        //fireTableDataChanged();
        fireTableRowsInserted(data.size()-1, data.size()-1);
    }

    public Climber getClimberAt(int row) {
        return data.get(row);
    }

}
