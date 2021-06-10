/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dostojic.climbers.view.controller.validators;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 *
 * @author Dejan.Ostojic
 * @param <T>
 */
public class ComponentValidator<T extends JComponent> {

    public static class FormValidator {

        List<ComponentValidator> validators;

        public FormValidator(List<ComponentValidator> validators) {
            this.validators = validators;
        }

        public boolean validate() {

            validators.forEach(v -> System.out.println("validator registred: " + v.rules.toString()));
            boolean isValid = true;
            for (ComponentValidator validator : validators) {
                System.out.println("validator: " + validator.rules.toString() + " is " + (validator.isValid() ? "" : " not ") + " valid: ");
                isValid = isValid && validator.isValid();
            }
            return isValid;
        }

    }
    T sourceComponent;
    JLabel errorInformationHolder;
    Border border;
    List<RuleMesasge> rules;

    public ComponentValidator(T sourceComponent, JLabel errorInformationHolder) {
        this.sourceComponent = sourceComponent;
        this.errorInformationHolder = errorInformationHolder;
        rules = new ArrayList<>();
    }

    public ComponentValidator addRule(Rule<T> rule, String errorMessage) {
        rules.add(new RuleMesasge(rule, errorMessage));
        return this;
    }

    public boolean isValid() {

        boolean isValid = true;
        for (RuleMesasge rm : rules) {
            if (rm.rule.isValid(sourceComponent)) {
                System.out.println("component is valid!");
                clearError();
                isValid = true;

            } else {
                System.out.println("component is not valid!");
                displayError(rm.errorMessage);
                isValid = false;
            }

        }

        if (!isValid) {
            System.out.println("Validation failed!");
            System.out.println("prevent form from submiting!");
        }

        return isValid;

    }

    private void clearError() {
        sourceComponent.setBorder(border);
        errorInformationHolder.setText("");
    }

    private void displayError(String errorMessage) {
        errorInformationHolder.setText(errorMessage);
        sourceComponent.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
    }

    public interface Rule<T extends JComponent> {

        public boolean isValid(T sourceComponent);
    }

    public static class RuleMesasge {

        public Rule rule;
        public String errorMessage;

        public RuleMesasge(Rule rule, String errorMessage) {
            this.rule = rule;
            this.errorMessage = errorMessage;
        }

        @Override
        public String toString() {
            return "rule: " + errorMessage;
        }

    }

}
