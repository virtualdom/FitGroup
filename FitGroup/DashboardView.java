package FitGroup;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DashboardView {

    private JFrame mainFrame;
    private JPanel userPanel;
    private JPanel buttonPanel;
    private JLabel workoutCounter;
    private JLabel actionLabel;
    private int workoutCount;
    private User user;
    private Database db;

    public DashboardView (User user, Database db) {
        this.user = user;
        this.db = db;
        workoutCount = 0;
        prepareGUI();
    }

    private void prepareGUI () {
        mainFrame = new JFrame("FitGroup | Social Workouts");
        mainFrame.setSize(400,600);
        mainFrame.setResizable(false);
        mainFrame.setLayout(new GridLayout(2, 1));

        mainFrame.addWindowListener (new WindowAdapter() {
            public void windowClosing (WindowEvent windowEvent) {
                System.exit(0);
            }        
        });
        
        actionLabel = new JLabel("",JLabel.CENTER);        
        userPanel = new JPanel();
        buttonPanel = new JPanel();
        userPanel.setLayout(new GridLayout(1, 2));
        buttonPanel.setLayout(new GridLayout(1, 2));
        workoutCounter = new JLabel(Integer.toString(workoutCount), JLabel.CENTER);        

        JButton checkinButton = new JButton("Check in");
        JButton deleteButton = new JButton("Delete");
        
        userPanel.add(workoutCounter);
        buttonPanel.add(checkinButton);
        buttonPanel.add(deleteButton);
        userPanel.add(buttonPanel);

        checkinButton.setActionCommand("Check in");
        deleteButton.setActionCommand("Delete");

        checkinButton.addActionListener(new ButtonClickListener()); 
        deleteButton.addActionListener(new ButtonClickListener()); 

        mainFrame.add(userPanel);
        mainFrame.add(actionLabel);
        mainFrame.setVisible(true);  
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            String command = e.getActionCommand();  
            if( command.equals( "Check in" ))  {
                workoutCount++;
                actionLabel.setText("Check in Button clicked.");
                workoutCounter.setText(Integer.toString(workoutCount));
           }
           else {
                if (workoutCount > 0) workoutCount--;
                actionLabel.setText("Delete Button clicked."); 
                workoutCounter.setText(Integer.toString(workoutCount));
           } 
       }     
   }
}