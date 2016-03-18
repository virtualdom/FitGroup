package FitGroup.views;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

import FitGroup.models.*;
import FitGroup.controllers.DashboardController;

public class DashboardView extends FitGroupView {

    private DashboardController controller;
    private JLabel workoutCountLabel;
    private DefaultTableModel defaulttable;
    private JComboBox comboBox;

    public DashboardView (User user, Database db) {

        this.controller = new DashboardController(user, db);
        prepareGUI();
    }

    private void prepareGUI () {
        mainFrame = new JFrame("FitGroup | Social Workouts");
        mainFrame.setSize(600,600);
        mainFrame.setResizable(false);
        mainFrame.setLayout(new FlowLayout()); 

        mainFrame.addWindowListener (new WindowAdapter() {
            public void windowClosing (WindowEvent windowEvent) {
                System.exit(0);
            }        
        });
        
        JPanel actionPanel = new JPanel();

        JPanel workoutCountPanel = new JPanel();
        JLabel workoutDisplayLabel = new JLabel("Your workout count: ",JLabel.CENTER);  
        workoutCountLabel = new JLabel(new Integer(controller.getLoggedInUser().getScore()).toString(), JLabel.CENTER);      
        workoutCountPanel.setLayout(new FlowLayout());
        workoutCountPanel.add(workoutDisplayLabel);
        workoutCountPanel.add(workoutCountLabel);
        
        JButton checkinButton = new JButton("Check in");
        checkinButton.setActionCommand("Check in");
        checkinButton.addActionListener(new ButtonClickListener());         
 
        JPanel checkInPanel = new JPanel();
        checkInPanel.setLayout(new GridLayout(1, 2));
        checkInPanel.add(workoutCountPanel);
        checkInPanel.add(checkinButton);

        JPanel groupPanel = new JPanel();
        groupPanel.setLayout(new FlowLayout());
        JButton createGroupButton = new JButton("Create Group");
        createGroupButton.setActionCommand("Create Group");
        createGroupButton.addActionListener(new ButtonClickListener());         
        JButton joinGroupButton = new JButton("Join Group");
        joinGroupButton.setActionCommand("Join Group");
        joinGroupButton.addActionListener(new ButtonClickListener());
        groupPanel.add(createGroupButton);
        groupPanel.add(joinGroupButton);

        actionPanel.add(checkInPanel);
        actionPanel.add(groupPanel);

        JPanel groupView = new JPanel();
        groupView.setLayout(new GridBagLayout());
        
        String[] groupnameArray = controller.getGroupNames();
        comboBox=new JComboBox(groupnameArray); 
        
        JButton actionButton = new JButton("Query");
        actionButton.addActionListener(new ButtonClickListener());
        actionButton.setActionCommand("Query");
        
        JPanel toppanel = new JPanel(); 
        toppanel.setLayout(new BoxLayout(toppanel, BoxLayout.Y_AXIS )); 
        JPanel buttonPanel = new JPanel(); 
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS )); 
        buttonPanel.add(comboBox); 
        
        buttonPanel.add(Box.createHorizontalGlue ()); 
        buttonPanel.add(actionButton); 
        toppanel.add(Box.createVerticalStrut (10)); 
        toppanel.add(buttonPanel); 
        toppanel.add(Box.createVerticalStrut (10));
        
        JPanel bottompanel = new JPanel(); 
        
        defaulttable = new  DefaultTableModel ();
        JTable table = new  JTable(defaulttable); 
        defaulttable.addColumn("member");
        defaulttable.addColumn("scores");
         
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
        bottompanel.setLayout(new BoxLayout(bottompanel, BoxLayout.Y_AXIS)); 
        bottompanel.add(Box.createVerticalStrut(10));
        bottompanel.add(scrollPane); 
        bottompanel.add(Box.createVerticalStrut(10)); 
        
        GridBagConstraints c1 = new GridBagConstraints(); 
        c1.gridx = 0; 
        c1.gridy = 0; 
        c1.gridheight = 1;
        c1.weightx = 1.0; 
        c1.fill = GridBagConstraints.HORIZONTAL ; 
        groupView.add(toppanel,c1); 

        GridBagConstraints c2 = new GridBagConstraints(); 
        c2.gridx = 0; 
        c2.gridy = 1; 
        c2.gridheight = 1;
        c2.weightx = 1.5; 
        c2.weighty = 1; 
        c2.fill = GridBagConstraints.BOTH ;

        groupView.add(bottompanel,c2);

        mainFrame.add(actionPanel);
        mainFrame.add(groupView);
        mainFrame.setVisible(true);  
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            ArrayList<User> tmpusers = new ArrayList<User>(5);
            String command = e.getActionCommand(); 
            if( command.equals("Check in"))  {
                workoutCountLabel.setText(new Integer(controller.checkIn()).toString());
            }
            else if (command.equals("Join Group")) {
                controller.joinGroup();
            } 
            else if (command.equals("Create Group")) {
                controller.createGroup();
            } 
            else {                
                for(int i = defaulttable.getRowCount() - 1; i >=0; i--) {
                    defaulttable.removeRow(i); 
                }

                ArrayList<String[]> tableData = controller.getMemberScores(comboBox.getSelectedItem().toString());
                for (int j = 0; j < tableData.size(); j++)
                    defaulttable.addRow(tableData.get(j));
            }
        }     
    }
}