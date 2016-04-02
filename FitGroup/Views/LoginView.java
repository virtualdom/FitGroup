package FitGroup.views;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.util.*;

import FitGroup.models.*;
import FitGroup.controllers.LoginController;

public class LoginView extends FitGroupView {

    private JLabel messageText;
    private JTextField username;
    private JPasswordField password;
    private LoginController controller;

    public LoginView (Database db) {
        controller = new LoginController(db, this);
        prepareGUI();
    }

    private void prepareGUI (){
        mainFrame = new JFrame("FitGroup | Social Workouts");
        mainFrame.setSize(400,200);
        mainFrame.setResizable(false);
        mainFrame.setLayout(new FlowLayout());

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing (WindowEvent windowEvent) {
                System.exit(0);
            }        
        });
        
        messageText = new JLabel("                                   ",JLabel.CENTER);
        messageText.setForeground(Color.red);
        JPanel messagePanel = new JPanel();
        messagePanel.add(messageText);
        
        username = new JTextField(20);
        JLabel usernameLabel = new JLabel("Username: ",JLabel.CENTER);        
        JPanel usernamePanel = new JPanel();
        usernamePanel.add(usernameLabel);
        usernamePanel.add(username);
        
        password = new JPasswordField(20);
        JLabel passwordLabel = new JLabel("Password: ",JLabel.CENTER);        
        JPanel passwordPanel = new JPanel();
        passwordPanel.add(passwordLabel);
        passwordPanel.add(password);
        
        JPanel buttonPanel = new JPanel();

        JButton logIn = new JButton("Log in");
        JButton signUp = new JButton("Sign up");
        
        buttonPanel.add(signUp);
        buttonPanel.add(logIn);

        logIn.setActionCommand("login");
        signUp.setActionCommand("signup");

        logIn.addActionListener(new ButtonClickListener()); 
        signUp.addActionListener(new ButtonClickListener()); 

        mainFrame.add(messageText);
        mainFrame.add(usernamePanel, BorderLayout.CENTER);
        mainFrame.add(passwordPanel, BorderLayout.CENTER);
        mainFrame.add(buttonPanel, BorderLayout.SOUTH);
        mainFrame.setVisible(true);  
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals( "login" ))  {
                int loginStatus = controller.logIn(username.getText(), new String(password.getPassword()));
                if (loginStatus == 0 ) messageText.setText("Username does not exist.");
                else if (loginStatus < 0) messageText.setText("Username and password do not match.");
                else System.out.println("Successful login: " + username.getText());
            }
            else {
                controller.signUp();
            } 
        }     
    }
}