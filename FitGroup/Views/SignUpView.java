package FitGroup.views;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.util.*;


import FitGroup.models.*;
import FitGroup.controllers.SignUpController;

public class SignUpView extends FitGroupView {

    private JLabel messageText;
    private JTextField username;
    private JTextField age;
    private JTextField weight;
    private JPasswordField password;
    private SignUpController controller;

    public SignUpView (Database db) {
        controller = new SignUpController(db, this);
        prepareGUI();
    }

    private void prepareGUI (){
        mainFrame = new JFrame("FitGroup | Social Workouts");
        mainFrame.setSize(400,300);
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
        
        age = new JTextField(10);
        JLabel ageLabel = new JLabel("Age: ",JLabel.CENTER);        
        JPanel agePanel = new JPanel();
        agePanel.add(ageLabel);
        agePanel.add(age);
        
        weight = new JTextField(10);
        JLabel weightLabel = new JLabel("Weight: ",JLabel.CENTER);        
        JPanel weightPanel = new JPanel();
        weightPanel.add(weightLabel);
        weightPanel.add(weight);
        
        JPanel buttonPanel = new JPanel();

        JButton cancel = new JButton("Cancel");
        JButton signUp = new JButton("Sign up");
        
        buttonPanel.add(cancel);
        buttonPanel.add(signUp);

        cancel.setActionCommand("cancel");
        signUp.setActionCommand("signup");

        cancel.addActionListener(new ButtonClickListener()); 
        signUp.addActionListener(new ButtonClickListener()); 

        mainFrame.add(messageText);
        mainFrame.add(usernamePanel, BorderLayout.CENTER);
        mainFrame.add(passwordPanel, BorderLayout.CENTER);
        mainFrame.add(agePanel, BorderLayout.CENTER);
        mainFrame.add(weightPanel, BorderLayout.CENTER);
        mainFrame.add(buttonPanel, BorderLayout.SOUTH);
        mainFrame.setVisible(true);  
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            String command = e.getActionCommand();
            
            if (command.equals( "signup" ))  {
                String newUsername = username.getText().trim();
                String newPass = new String(password.getPassword());
    
                int newAge, newWeight;
                try {
                    newAge = Integer.parseInt(age.getText().trim());
                    newWeight = Integer.parseInt(weight.getText().trim());
                } catch (Exception ex) {
                    newAge = -1;
                    newWeight = -1;
                }

                if (newUsername.equals("") || newPass.equals("") || newAge < 0 || newWeight < 0)
                    messageText.setText("Please fill all fields correctly.");
                else {
                    int status = controller.signUp(newUsername, newPass, newAge, newWeight);
                    if (status == 0) messageText.setText("User " + newUsername + " already exists. Please try another username.");
                }
            }
            else {
                controller.cancel();
            } 
        }     
    }
}