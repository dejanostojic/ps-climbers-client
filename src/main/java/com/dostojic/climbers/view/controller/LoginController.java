/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dostojic.climbers.view.controller;

import com.dostojic.climbers.communication.Communication;
import com.dostojic.climbers.view.model.User;
import com.dostojic.climbers.view.constant.Constants;
import com.dostojic.climbers.view.coordinator.MainCoordinator;
import com.dostojic.climbers.view.coordinator.Session;
import com.dostojic.climbers.view.form.FormLogin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author planina
 */
public class LoginController {

    FormLogin formLogin;

    public LoginController(FormLogin formLogin) {
        this.formLogin = new FormLogin();
        addActionListeners();

    }

    public void openForm() {
        formLogin.setVisible(true);
    }

    private void addActionListeners() {

        formLogin.loginAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Login action performed");
                loginUser(actionEvent);
            }
            
            private void loginUser(ActionEvent actionEvent) {
                try {
                    clearErrors();
                    String username = formLogin.getTextUsername().getText().trim();
                    String password = String.valueOf(formLogin.getPfPassword().getPassword());

                    validateForm(username, password);

                    User user = Communication.getInstance().login(username, password);
                    JOptionPane.showMessageDialog(
                            formLogin,
                            "Welcome " + user.getFirstName() + ", " + user.getLastName(),
                            "Login", JOptionPane.INFORMATION_MESSAGE
                    );
                    formLogin.dispose();
                    Session.getInstance().addParam(Constants.CURRENT_USER, user);
                    MainCoordinator.getInstance().openMainForm();
                } catch (IOException ex) {
                    // TODO: THIS WILL BE PRESENT ON ALL Communication calls, should i handle in  Communication class maybe, or declare in Communication?!
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(formLogin, "Error connecting to server", "Login error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(formLogin, ex.getMessage(), "Login error", JOptionPane.ERROR_MESSAGE);

                }
            }
            
            private void clearErrors() {
                formLogin.getLabelUsernameError().setText("");
                formLogin.getLabelPassError().setText("");
            }

            private void validateForm(String username, String password) throws Exception {
                String errorMessage = "";
                if (username.isEmpty()) {
                    formLogin.getLabelUsernameError().setText("Username can not be empty!");
                    errorMessage += "Username can not be empty!\n";
                }
                if (password.isEmpty()) {
                    formLogin.getLabelPassError().setText("Password can not be empty!");
                    errorMessage += "Password can not be empty!\n";
                }
                if (!errorMessage.isEmpty()) {
                    throw new Exception(errorMessage);
                }
            }
        });
    }
}
