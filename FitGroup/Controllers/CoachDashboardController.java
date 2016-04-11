package FitGroup.controllers;

import java.util.ArrayList;

import FitGroup.models.Database;
import FitGroup.models.Group;
import FitGroup.models.User;
import FitGroup.views.CreateView;
import FitGroup.views.JoinView;
import FitGroup.views.UpdateView;

public class CoachDashboardController {
	private User loggedInUser;
	private Database db;

	public CoachDashboardController(User user, Database database) {
		this.loggedInUser = user;
		this.db = database;
	}

	public User getLoggedInUser() {
		return loggedInUser;
	}

	public Database getDatabase() {
		return db;
	}

	public String[] getGroupNames() {
		ArrayList<Group> groups = db.searchGroupsByUser(loggedInUser.getUsername());
		String[] groupNames = new String[groups.size()];
		for (int i = 0; i < groups.size(); i++) {
			groupNames[i] = groups.get(i).getname();
		}
		return groupNames;
	}

	public void updateInformation() {
		UpdateView updateView = UpdateView.createWindow(db, loggedInUser);

	}

	public String[] UpdateInformation() {
		ArrayList<Group> groups = db.searchGroupsByUser(loggedInUser.getUsername());
		String[] groupNames = new String[groups.size()];
		for (int i = 0; i < groups.size(); i++) {
			groupNames[i] = groups.get(i).getname();
		}
		return groupNames;
	}

	public ArrayList<String[]> getMemberScores(String groupName) {
		ArrayList<User> users = db.searchUsersByGroup(groupName);
		ArrayList<String[]> data = new ArrayList<String[]>();
		String[] input;
		for (int j = 0; j < users.size(); j++) {
			input = new String[2];
			input[0] = users.get(j).getUsername();
			input[1] = new Integer(users.get(j).getScore()).toString();
			data.add(input);
		}
		return data;
	}

}
