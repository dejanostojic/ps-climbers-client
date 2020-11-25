/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dostojic.climbers.view.model;

import com.dostojic.climbers.domain.Climber;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author planina
 */
public class TableModelClimberList extends AbstractTableModel {

    List<Climber> data;
    private String[] columnNames = new String[]{"Fist Name", "Last name", "Year of birth"};
    private Class[] columnClasses = new Class[]{String.class, String.class, Integer.class};

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
    public Class<?> getColumnClass(int columnIndex) {
        return columnClasses[columnIndex];
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

}
