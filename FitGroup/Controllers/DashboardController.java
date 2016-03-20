package FitGroup.controllers;

import java.awt.event.*;
import java.util.ArrayList;

import FitGroup.models.*;
import FitGroup.views.CreateView;
import FitGroup.views.DashboardView;
import FitGroup.views.JoinView;

public class DashboardController {
    private User loggedInUser;
    private Database db;

    public DashboardController (User user, Database database) {
    	this.loggedInUser = user;
    	this.db = database;
    }

    public User getLoggedInUser () {
    	return loggedInUser;
    }

    public Database getDatabase () {
    	return db;
    }

    public int checkIn () {
        int newScore = loggedInUser.getScore() + 1;
    	loggedInUser.setScore(newScore);
        db.updateUser(loggedInUser);
    	return newScore;
    }

    public void joinGroup () {
    	JoinView joinWindow = JoinView.createWindow(db,loggedInUser);
    }

    public void createGroup () {
    	CreateView createWindow = CreateView.createWindow(db,loggedInUser);
    }

    public String[] getGroupNames () {
        ArrayList<Group> groups = db.searchGroupsByUser(loggedInUser.getUsername());
        String[] groupNames = new String[groups.size()];
        for (int i = 0; i < groups.size(); i++) {
            groupNames[i] = groups.get(i).getname(); 
        }
        return groupNames;
    }

    public ArrayList<String[]> getMemberScores (String groupName) {
        ArrayList<User> users = db.searchUsersByGroup(groupName);
        ArrayList<String[]> data = new ArrayList<String []>();
        String[] input;
        for (int j = 0; j < users.size(); j++) {
            input = new String[2];
            input [0] = users.get(j).getUsername();
            input [1] = new Integer(users.get(j).getScore()).toString();
            data.add(input);
        }
        return data;
    }
}