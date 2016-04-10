package FitGroup.views;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.*;

import FitGroup.controllers.ApproveRequestsController;
import FitGroup.controllers.LoginController;
import FitGroup.controllers.SignUpController;
import FitGroup.models.Database;
import FitGroup.models.User;
import FitGroup.models.Request;

import java.util.*;

public class ApproveRequestsView extends FitGroupView {
    private static boolean instantiated = false;
    private static ApproveRequestsView instance;
    private ApproveRequestsController controller;
    private DashboardView dashboard;

    private JComboBox comboBox;
    private JList requestsList;
    private DefaultListModel listModel;
    private ArrayList<Request> requests;


    private ApproveRequestsView (Database db, User loggedInUser, DashboardView dbv) {
        controller = new ApproveRequestsController(db, this,loggedInUser);
        dashboard = dbv;
        prepareGUI();
    }

    public JFrame getFrame () {
        return mainFrame;
    }

    public static ApproveRequestsView createWindow (Database db, User loggedInUser, DashboardView dbv) {
        if (!instantiated) {
            instantiated = true;
            instance = new ApproveRequestsView(db,loggedInUser, dbv);
        }
        return instance;
    }
    
    private void prepareGUI (){
        mainFrame = new JFrame("FitGroup | Social Workouts");
        mainFrame.setSize(250,300);
        mainFrame.setResizable(false);
        mainFrame.setLayout(new FlowLayout());

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing (WindowEvent windowEvent) {
                instantiated = false;
            }        
        });
        
        String[] groupnameArray = controller.getAdminGroupNames();
        comboBox = new JComboBox(groupnameArray); 
        comboBox.setSelectedIndex(0);
        comboBox.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                requests = controller.getRequests(comboBox.getSelectedItem().toString());
                if (requests == null) requests = new ArrayList<Request>(0);
                listModel.removeAllElements();
                for (int i = 0; i < requests.size(); i++)
                    listModel.addElement(requests.get(i).getUser().getUsername());
            }
        });

        requests = controller.getRequests(comboBox.getSelectedItem().toString());
        if (requests == null) requests = new ArrayList<Request>(0);
        listModel = new DefaultListModel();
        for (int i = 0; i < requests.size(); i++)
            listModel.addElement(requests.get(i).getUser().getUsername());
        requestsList = new JList(listModel);
        requestsList.setVisibleRowCount(10);
        requestsList.setFixedCellWidth(200);
        requestsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JButton approve = new JButton("Approve");
        approve.setActionCommand("approve");
        approve.addActionListener(new ButtonClickListener()); 
        JPanel top = new JPanel();
        top.add(comboBox);
        top.add(approve);
        mainFrame.add(top, BorderLayout.CENTER);
        mainFrame.add(new JScrollPane(requestsList), BorderLayout.CENTER);
        mainFrame.setVisible(true);  
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals( "approve" ))  {
                if (requestsList.getSelectedIndex() < 0) return;
                boolean result = controller.approve(requests.get(requestsList.getSelectedIndex()));
                if (result) {
                    requests = controller.getRequests(comboBox.getSelectedItem().toString());
                    if (requests == null) requests = new ArrayList<Request>(0);
                    listModel.removeAllElements();
                    for (int i = 0; i < requests.size(); i++)
                        listModel.addElement(requests.get(i).getUser().getUsername());
                }
            }
        }     
    }
}