/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dostojic.climbers.view.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Dejan.Ostojic
 */
public class TableModelRegistrationFee extends AbstractTableModel {

    private final List<RegistrationFee> data;
    private final String[] columnNames = new String[]{"Ord", "Name", "Amount", "Start", "End"};
    private final Class[] columnClasses = new Class[]{Integer.class, String.class, BigDecimal.class, Date.class, Date.class};

    public TableModelRegistrationFee(List<RegistrationFee> data) {
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
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 0;
    }

    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        RegistrationFee row = data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return row.getOrd();
            case 1:
                return row.getName();
            case 2:
                return row.getAmount();
            case 3:
                return row.getStartDate();
            case 4:
                return row.getEndDate();
            default:
                return "n/a";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        RegistrationFee row = data.get(rowIndex);
        switch (columnIndex){
            case 0:
                row.setOrd((Integer) aValue);
                break;
            case 1:
                row.setName((String) aValue);
                break;
            case 2:
                row.setAmount((BigDecimal) aValue);
                break;
            case 3:
                row.setStartDate((Date) aValue);
                break;
            case 4:
                row.setEndDate((Date) aValue);
                break;
        }
    }
    
    public void addRow(){
        System.out.println("Add new row (table model registration fee)");
        RegistrationFee registrationFee = new RegistrationFee();
        registrationFee.setOrd(data.size() + 1);
        data.add(registrationFee);
//        fireTableRowsInserted(data.size() - 1, data.size() - 1);
        fireTableDataChanged();
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

    public List<RegistrationFee> getData() {
        return data;
    }
    
    
    
    
    
    
}
