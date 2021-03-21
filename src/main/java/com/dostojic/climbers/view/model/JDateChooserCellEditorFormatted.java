/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dostojic.climbers.view.model;

import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.util.Date;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Dejan.Ostojic
 */
public class JDateChooserCellEditorFormatted extends AbstractCellEditor implements
        TableCellEditor {

    private static final long serialVersionUID = 917881575221755609L;

    private final JDateChooser dateChooser;

    public JDateChooserCellEditorFormatted(String format) {
        this.dateChooser = new JDateChooser(null, format);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {

        Date date = null;
        if (value instanceof Date) {
            date = (Date) value;
        }

        dateChooser.setDate(date);

        return dateChooser;
    }

    @Override
    public Object getCellEditorValue() {
        return dateChooser.getDate();
    }    
}
