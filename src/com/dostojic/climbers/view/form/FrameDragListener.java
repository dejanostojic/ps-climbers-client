/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dostojic.climbers.view.form;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author planina
 */
public class FrameDragListener extends MouseAdapter {

    public static FrameDragListener addDragCapability(final JPanel jPanel) {
        return new FrameDragListener(jPanel);
    }

    private final JFrame topFrame;
    private Point mouseDownCompCoords = null;

    private FrameDragListener(final JPanel jPanel) {
        this.topFrame = (JFrame) SwingUtilities.getWindowAncestor(jPanel);

        jPanel.addMouseListener(this);
        jPanel.addMouseMotionListener(this);
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
        mouseDownCompCoords = null;
    }

    @Override
    public void mousePressed(final MouseEvent e) {
        mouseDownCompCoords = e.getPoint();
    }

    @Override
    public void mouseDragged(final MouseEvent e) {
        final Point currCoords = e.getLocationOnScreen();
        try {
            topFrame.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
        } catch (final NullPointerException e1) {

        }
    }
}
