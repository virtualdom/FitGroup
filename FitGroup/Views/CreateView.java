package FitGroup.views;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

import FitGroup.controllers.CreateController;
import FitGroup.controllers.LoginController;
import FitGroup.controllers.SignUpController;
import FitGroup.models.Database;

import java.util.*;

public class CreateView {
    private JFrame mainFrame;
    private JTextField groupName;
    private JLabel messageText;
    private static boolean instantiated = false;
    private static CreateView instance;
    private CreateController controller;

    private CreateView (Database db) {
        controller = new CreateController(db, this);
        prepareGUI();
    }

    public JFrame getFrame () {
        return mainFrame;
    }

    public static CreateView createWindow (Database db) {
        if (!instantiated) {
            instantiated = true;
            instance = new CreateView(db);
        }
        return instance;
    }
    
    private void prepareGUI (){
        mainFrame = new JFrame("FitGroup | Social Workouts");
        mainFrame.setSize(400,150);
        mainFrame.setResizable(false);
        mainFrame.setLayout(new FlowLayout());

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing (WindowEvent windowEvent) {
                instantiated = false;
            }        
        });
        
        messageText = new JLabel("",JLabel.CENTER);
        messageText.setForeground(Color.red);
        JPanel messagePanel = new JPanel();
        messagePanel.add(messageText);
        
        groupName = new JTextField(20);
        JLabel groupNameLabel = new JLabel("Group Name: ",JLabel.CENTER);        
        JPanel groupNamePanel = new JPanel();
        groupNamePanel.add(groupNameLabel);
        groupNamePanel.add(groupName);
        
        JPanel buttonPanel = new JPanel();
        JButton create = new JButton("Create Group");
        create.setActionCommand("create");
        create.addActionListener(new ButtonClickListener()); 
        JButton cancel = new JButton("Cancel");
        cancel.setActionCommand("cancel");
        cancel.addActionListener(new ButtonClickListener()); 
        buttonPanel.add(cancel);
        buttonPanel.add(create);

        mainFrame.add(groupNamePanel, BorderLayout.CENTER);
        mainFrame.add(buttonPanel, BorderLayout.CENTER);
        mainFrame.add(messagePanel, BorderLayout.SOUTH);
        mainFrame.setVisible(true);  
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals( "create" ))  {
                  //messageText.setText("CREATE BUTTON PRESSED"+groupName.getText());
                  controller.CreateGroup(groupName.getText().trim());
                
            } else {
                mainFrame.setVisible(false);
                instance = null;
                instantiated = false;
            }
        }     
    }
}