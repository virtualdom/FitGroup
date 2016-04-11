package FitGroup.views;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import FitGroup.controllers.CreateController;
import FitGroup.controllers.JoinController;
import FitGroup.models.Database;
import FitGroup.models.User;

import java.util.Scanner; 
import java.io.*;


public class JoinView extends FitGroupView implements ActionListener {
    
	private JPanel contentPane;
	private JPasswordField passwordField;
	private JLabel lblGroupCode;
	private JLabel lblgroup;	
	private JTextField textField;
	private static boolean instantiated = false;
	private static JoinView instance;
	private JoinController controller;

    private JoinView (Database db,User loggedInUser) {
        controller = new JoinController(db, this,loggedInUser);
        prepareGUI();
    }

	public static JoinView createWindow (Database db, User loggedInUser) {
		if (!instantiated) {
			instance = new JoinView(db,loggedInUser);
			instantiated = true;
		}

		return instance;
	}

	private void prepareGUI () {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		mainFrame = new JFrame("Join Group");
		mainFrame.setSize(450, 300);
		mainFrame.setVisible(true);
		
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing (WindowEvent windowEvent) {
                instantiated = false;
            }        
        });

		Container contentPane=mainFrame.getContentPane();
	    contentPane.setLayout(null);
		
		JButton btnJoin = new JButton("JOIN");
		btnJoin.setBounds(300,200,100,40);
		contentPane.add(btnJoin);
		
		lblgroup = new JLabel(" Enter the group you want to join");
		lblgroup.setBounds(5,17,300,20);
		contentPane.add(lblgroup);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(125,100,205,20);
		contentPane.add(passwordField);
		
		lblGroupCode = new JLabel(" Invitation Code  : ");
		lblGroupCode.setBounds(30,100,100,20);
		contentPane.add(lblGroupCode);
		
		JLabel lblNewLabel = new JLabel(" Group Name : ");
		lblNewLabel.setBounds(5, 45, 100, 20);
		mainFrame.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(95, 45, 205, 20);
		mainFrame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JRadioButton rdbtnWithic = new JRadioButton("With Invitation Code");
		rdbtnWithic.setBounds(5, 75, 200, 20);
		JRadioButton rdbtnWithoutic = new JRadioButton("Without Invitation Code");
		rdbtnWithoutic.setBounds(5, 150, 200, 20);

		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnWithic);
		group.add(rdbtnWithoutic);
		
		mainFrame.getContentPane().add(rdbtnWithic);
		mainFrame.getContentPane().add(rdbtnWithoutic);
		
		btnJoin.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//controller.JoinGroup()
		String username = textField.getText();
		String IC = new String(passwordField.getPassword());
		controller.JoinGroup(username, IC);
	}
}
