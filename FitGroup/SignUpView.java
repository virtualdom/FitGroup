package FitGroup;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.util.*;

public class SignUpView {

    private JLabel messageText;
    private JFrame mainFrame;
    private JTextField username;
    private JTextField age;
    private JTextField weight;
    private JPasswordField password;
    private Database db;

    public SignUpView (Database db) {
        this.db = db;
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
            User loginUser;
            String newUsername, newPass;
            int newAge, newWeight;
            if (command.equals( "signup" ))  {
                username.setText(username.getText().trim());
                age.setText(age.getText().trim());
                weight.setText(weight.getText().trim());

                newUsername = username.getText();
                newPass = new String(password.getPassword());

                loginUser = db.searchUser(username.getText());
                if (loginUser != null) messageText.setText("Username " + username.getText() + " already exists.");
            	else if (newUsername.equals("") || newPass.equals("") || age.getText().equals("") || weight.getText().equals(""))
                    messageText.setText("Not all fields are filled.");
                else {
                    try {
                        newAge = Integer.parseInt(age.getText());
                        newWeight = Integer.parseInt(weight.getText());
                    } catch (Exception ex) {
                        newAge = -1;
                        newWeight = -1;
                    }

                    if (newAge < 1 || newWeight < 1) messageText.setText("Age and weight must be valid integers.");
                    else {
                        db.addUser(new User(newUsername, newPass, newAge, newWeight));
                        loginUser = db.searchUser(newUsername);
            	        mainFrame.setVisible(false);
                        DashboardView application = new DashboardView(loginUser, db);

                    }
                }
            }
            else {
                mainFrame.setVisible(false);
                LoginView window = new LoginView(db);
           	} 
       	}     
   	}
}