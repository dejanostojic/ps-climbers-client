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
 * @author Dejan.Ostojic
 */
public class TableModelRoutes extends AbstractTableModel {
    
    private final List<Route> data;
    private final String[] columnNames = new String[]{"Ord", "Name", "Grade"};
    private final Class[] columnClasses = new Class[]{Integer.class, String.class, String.class};
 
    public TableModelRoutes(List<Route> data) {
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
        Route row = data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return row.getOrd();
            case 1:
                return row.getName();
            case 2:
                return row.getGrade();
            default:
                return "n/a";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Route row = data.get(rowIndex);
        switch (columnIndex){
            case 0:
                row.setOrd((Integer) aValue);
                break;
            case 1:
                row.setName((String) aValue);
                break;
            case 2:
                row.setGrade((String) aValue);
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 0;
    }
    
    
    
     public void addRow(){
        Route registrationFee = new Route();
        registrationFee.setOrd(data.size() + 1);
        data.add(registrationFee);
        fireTableRowsInserted(data.size() - 1, data.size() - 1);
    }
    
    public void deleteRow(int index){
        data.remove(index);
        renumerate();
        fireTableDataChanged();
    }
        
    public void renumerate(){
        for (int i=0; i < data.size(); i++){
            data.get(i).setOrd(i + 1);
        }
    }

    public List<Route> getData() {
        return data;
    }
    
    
}
