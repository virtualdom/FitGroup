package FitGroup.views;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import FitGroup.controllers.CoachDashboardController;
import FitGroup.controllers.DashboardController;
import FitGroup.models.Database;
import FitGroup.models.User;

public class CoachDashboardView extends FitGroupView {

	private CoachDashboardController controller;
	private JLabel workoutCountLabel;
	private DefaultTableModel defaulttable;
	private JComboBox comboBox;
	private String[] groupnameArray;

	public CoachDashboardView(User user, Database db) {

		this.controller = new CoachDashboardController(user, db);
		prepareGUI();
	}

	private void UpdateGroupList() {
		groupnameArray = controller.getGroupNames();
		comboBox.removeAllItems();
		for (int i = 0; i < groupnameArray.length; i++)
			comboBox.addItem(groupnameArray[i]);
		comboBox.repaint();
		comboBox.updateUI();
	}

	private void prepareGUI() {
		mainFrame = new JFrame("FitGroup | Social Workouts");
		mainFrame.setSize(600, 600);
		mainFrame.setResizable(false);
		mainFrame.setLayout(new FlowLayout());

		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});

		JPanel actionPanel = new JPanel();

		JPanel workoutCountPanel = new JPanel();

		JButton updateButton = new JButton("Update info");
		updateButton.setActionCommand("update info");
		updateButton.addActionListener(new ButtonClickListener());

		JPanel updatePanel = new JPanel();
		updatePanel.setLayout(new GridLayout(1, 2));
		updatePanel.add(workoutCountPanel);
		updatePanel.add(updateButton);

		actionPanel.add(updatePanel);

		JPanel groupView = new JPanel();
		groupView.setLayout(new GridBagLayout());

		JButton actionButton = new JButton("Query");
		actionButton.addActionListener(new ButtonClickListener());
		actionButton.setActionCommand("Query");

		JPanel toppanel = new JPanel();
		toppanel.setLayout(new BoxLayout(toppanel, BoxLayout.Y_AXIS));
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(actionButton);
		toppanel.add(Box.createVerticalStrut(10));
		toppanel.add(buttonPanel);
		toppanel.add(Box.createVerticalStrut(10));

		JPanel bottompanel = new JPanel();

		defaulttable = new DefaultTableModel();
		JTable table = new JTable(defaulttable);
		defaulttable.addColumn("Application Date");
		defaulttable.addColumn("Username");

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
		c1.fill = GridBagConstraints.HORIZONTAL;
		groupView.add(toppanel, c1);

		GridBagConstraints c2 = new GridBagConstraints();
		c2.gridx = 0;
		c2.gridy = 1;
		c2.gridheight = 1;
		c2.weightx = 1.5;
		c2.weighty = 1;
		c2.fill = GridBagConstraints.BOTH;

		groupView.add(bottompanel, c2);

		mainFrame.add(actionPanel);
		mainFrame.add(groupView);
		mainFrame.setVisible(true);
	}

	private class ButtonClickListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ArrayList<User> tmpusers = new ArrayList<User>(5);
			String command = e.getActionCommand();

			if (command.equals("update info")) {
				controller.updateInformation();
			} else if (command.equals("Create Group")) {
				// controller.createGroup();
			} else {
				for (int i = defaulttable.getRowCount() - 1; i >= 0; i--) {
					defaulttable.removeRow(i);
				}

				ArrayList<String[]> tableData = controller.getMemberScores(comboBox.getSelectedItem().toString());
				for (int j = 0; j < tableData.size(); j++)
					defaulttable.addRow(tableData.get(j));
			}
		}
	}

	private class listClickListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {

			switch (e.getStateChange()) {
			case ItemEvent.SELECTED:
				System.out.println("select" + e.getItem());
				break;
			}
			// TODO Auto-generated method stub
			// UpdateGroupList();
		}
	}
}