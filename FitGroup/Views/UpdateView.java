package FitGroup.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.jws.soap.SOAPBinding.Use;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import FitGroup.controllers.SignUpController;
import FitGroup.controllers.UpdateController;
import FitGroup.models.Database;
import FitGroup.models.User;

public class UpdateView extends FitGroupView {
	private JLabel messageText;
	private JTextField username;
	private JTextField age;
	private JTextField weight;
	private JTextField userType;
	private JPasswordField password;
	private UpdateController controller;
	private JComboBox UserTypeComboBox;
	private static boolean instantiated = false;
	private static UpdateView instance;
	private User loggedUser;
	private JFrame mainFrame;

	public UpdateView(Database db, User loggedInUser) {
		loggedUser = loggedInUser;
		controller = new UpdateController(db, this);
		prepareGUI();
	}

	public static UpdateView createWindow(Database db, User loggedInUser) {
		if (!instantiated) {
			instance = new UpdateView(db, loggedInUser);
			instantiated = true;
		}

		return instance;
	}

	private void prepareGUI() {
		mainFrame = new JFrame("FitGroup | Social Workouts");
		mainFrame.setSize(400, 300);
		mainFrame.setResizable(false);
		mainFrame.setLayout(new FlowLayout());

		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				mainFrame.setVisible(false);
				instance = null;
				instantiated = false;
			}
		});

		messageText = new JLabel("                                   ", JLabel.CENTER);
		messageText.setForeground(Color.red);
		JPanel messagePanel = new JPanel();
		messagePanel.add(messageText);

		username = new JTextField(20);
		username.setText(loggedUser.getUsername());
		username.setEditable(false);
		JLabel usernameLabel = new JLabel("Username: ", JLabel.CENTER);
		JPanel usernamePanel = new JPanel();
		usernamePanel.add(usernameLabel);
		usernamePanel.add(username);

		age = new JTextField(10);
		age.setText(Integer.toString(loggedUser.getAge()));
		JLabel ageLabel = new JLabel("Age: ", JLabel.CENTER);
		JPanel agePanel = new JPanel();
		agePanel.add(ageLabel);
		agePanel.add(age);

		weight = new JTextField(10);
		weight.setText(Integer.toString(loggedUser.getWeight()));
		JLabel weightLabel = new JLabel("Weight: ", JLabel.CENTER);
		JPanel weightPanel = new JPanel();
		weightPanel.add(weightLabel);
		weightPanel.add(weight);

		JPanel buttonPanel = new JPanel();

		JButton cancel = new JButton("Cancel");
		JButton signUp = new JButton("Update");

		buttonPanel.add(cancel);
		buttonPanel.add(signUp);

		cancel.setActionCommand("cancel");
		signUp.setActionCommand("update");

		cancel.addActionListener(new ButtonClickListener());
		signUp.addActionListener(new ButtonClickListener());

		mainFrame.add(usernamePanel, BorderLayout.CENTER);
		mainFrame.add(agePanel, BorderLayout.CENTER);
		mainFrame.add(weightPanel, BorderLayout.CENTER);
		mainFrame.add(buttonPanel, BorderLayout.SOUTH);
		mainFrame.setVisible(true);
	}

	private class ButtonClickListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();

			if (command.equals("update")) {
				int newAge, newWeight;
				try {
					newAge = Integer.parseInt(age.getText().trim());
					newWeight = Integer.parseInt(weight.getText().trim());
				} catch (Exception ex) {
					newAge = -1;
					newWeight = -1;
				}
				if (newAge < 0 || newWeight < 0)
					messageText.setText("Please fill all fields correctly.");
				else {
					controller.update(loggedUser, newAge, newWeight);
				}
			} else {
				mainFrame.setVisible(false);
				instance = null;
				instantiated = false;
			}
		}
	}
}
