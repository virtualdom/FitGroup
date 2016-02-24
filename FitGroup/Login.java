package FitGroup;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.util.*;

public class Login {

    private JFrame mainFrame;
    private JTextField username;
    private JPasswordField password;

    public Login(){
        prepareGUI();
        Scanner userScanner;    
        try {
            userScanner = new Scanner(new File("./FitGroup/users.txt"));
        } catch (FileNotFoundException e) {
            userScanner = new Scanner(System.in);
            System.out.println("ERROR: users.txt does not exist");
        }
        System.out.println(userScanner.next());
    }

    private void prepareGUI(){
        mainFrame = new JFrame("FitGroup | Social Workouts");
        mainFrame.setSize(400,200);
        mainFrame.setResizable(false);
        mainFrame.setLayout(new FlowLayout());

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }        
        });
        
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


        mainFrame.add(usernamePanel, BorderLayout.CENTER);
        mainFrame.add(passwordPanel, BorderLayout.CENTER);
        mainFrame.add(buttonPanel, BorderLayout.SOUTH);
        mainFrame.setVisible(true);  
    }

    private class ButtonClickListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();  
            if( command.equals( "login" ))  {
            	System.out.println(username.getText());
            	System.out.println(password.getPassword());
           	}
           	else {
            	System.out.println("SIGNUP");
           	} 
       	}     
   	}
}