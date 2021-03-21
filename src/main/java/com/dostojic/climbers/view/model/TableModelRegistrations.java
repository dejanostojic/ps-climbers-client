/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dostojic.climbers.view.model;

import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Dejan.Ostojic
 */
public class TableModelRegistrations extends AbstractTableModel {
    private final List<Registration> data;
    private final String[] columnNames = new String[]{"Climber", "Start No.", "Total ord", "Paid", "Created date", "Paid date", "Registration fee"};
    private final Class[] columnClasses = new Class[]{Climber.class, Integer.class, Integer.class, Boolean.class, Date.class, Date.class, RegistrationFee.class};

    public TableModelRegistrations(List<Registration> data) {
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
    // {"Climber", "Start No.", "Total ord", "Paid", "Created date", "Paid date", "Registration fee"}
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Registration row = data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return row.getClimber();
            case 1:
                return row.getStartNumber();
            case 2:
                return row.getTotalOrd();
            case 3:
                return row.getPaid();
            case 4:
                return row.getCreatedDate();
            case 5:
                return row.getPaidDate();
            case 6:
                return row.getRegistrationFee() != null ? row.getRegistrationFee().getName() : null;
            default:
                return "n/a";
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return (columnIndex != 1 && columnIndex != 2);
    }

    
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Registration row = data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                row.setClimber((Climber) aValue);
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                row.setPaid((Boolean) aValue);
                break;
            case 4:
                row.setCreatedDate((Date) aValue);
                break;
            case 5:
                row.setPaidDate((Date) aValue);
                break;
            case 6:
                row.setRegistrationFee((RegistrationFee) aValue);
        }
    }
    
    
    
    public void addRow(){
        System.out.println("Add new row (table model registration fee)");
        Registration registration = new Registration();
        registration.setStartNumber(data.size() + 1);
        registration.setCreatedDate(new Date());
        data.add(registration);
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
            data.get(i).setStartNumber(i + 1);
        }
    }

    public List<Registration> getData() {
        return data;
    }
    
}
