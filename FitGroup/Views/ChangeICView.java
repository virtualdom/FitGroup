package FitGroup.views;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

import FitGroup.views.DashboardView;
import FitGroup.controllers.ChangeICController;
import FitGroup.controllers.LoginController;
import FitGroup.controllers.SignUpController;
import FitGroup.models.Database;
import FitGroup.models.Group;

import java.util.*;

public class ChangeICView extends FitGroupView {
    private JTextField inputIC;
    private static boolean instantiated = false;
    private static ChangeICView instance;
    private ChangeICController controller;
    private DashboardView dashboard;

    private ChangeICView (Database db, Group group, DashboardView dbv) {
        controller = new ChangeICController(db, this, group);
        dashboard = dbv;
        prepareGUI();
    }

    public static ChangeICView createWindow (Database db, Group group, DashboardView dbv) {
        if (!instantiated) {
            instantiated = true;
            instance = new ChangeICView(db, group, dbv);
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
                
        inputIC = new JTextField(20);
        JLabel inputICLabel = new JLabel("New IC: ",JLabel.CENTER);        
        JPanel inputICPanel = new JPanel();
        inputICPanel.add(inputICLabel);
        inputICPanel.add(inputIC);
        
        JPanel buttonPanel = new JPanel();
        JButton create = new JButton("Change IC");
        create.setActionCommand("change");
        create.addActionListener(new ButtonClickListener()); 
        JButton cancel = new JButton("Cancel");
        cancel.setActionCommand("cancel");
        cancel.addActionListener(new ButtonClickListener()); 
        buttonPanel.add(cancel);
        buttonPanel.add(create);

        mainFrame.add(inputICPanel, BorderLayout.CENTER);
        mainFrame.add(buttonPanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);  
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals( "change" ))  {
                controller.changeIC(inputIC.getText().trim());
            }
            mainFrame.setVisible(false);
            instance = null;
            instantiated = false;
        }     
    }
}