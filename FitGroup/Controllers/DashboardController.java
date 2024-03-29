package FitGroup.controllers;

import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import FitGroup.models.*;
import FitGroup.views.CreateView;
import FitGroup.views.ChangeICView;
import FitGroup.views.DashboardView;
import FitGroup.views.JoinView;
import FitGroup.views.ApproveRequestsView;
import FitGroup.views.UpdateView;

public class DashboardController {
    private User loggedInUser;
    private Database db;
    private DashboardView view;

    public DashboardController (User user, Database database, DashboardView view) {
    	this.loggedInUser = user;
    	this.db = database;
        this.view = view;
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

    private boolean testAdmin (String groupName) {
        if (db.searchMembership(groupName, loggedInUser.getUsername()).getIsAdmin() == 0) {
            JOptionPane.showMessageDialog(null, "You are not an admin for this group.", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }

    public void makeAdmin (String username, String groupName) {
        if (!testAdmin(groupName)) return;

        Membership membership = db.searchMembership(groupName, username);
        if (membership.getIsAdmin() == 1) {
            JOptionPane.showMessageDialog(null, "This member is already an admin.", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
            return;
        } else {
            membership.setAdmin(1);
            db.updateMembership(membership);
            JOptionPane.showMessageDialog(null, username + " is now an admin.", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void approveRequests (String groupName) {
        if (!testAdmin(groupName)) return;

        ApproveRequestsView approveRequestsView = ApproveRequestsView.createWindow(db, loggedInUser, view);
    }

    public void joinGroup () {
        JoinView joinWindow = JoinView.createWindow(db,loggedInUser, view);
    }

    public void changeIC (String groupname) {
        if (!testAdmin(groupname)) return;
    	ChangeICView changeICView = ChangeICView.createWindow(db, db.searchGroup(groupname), view);
    }

    public void createGroup () {
    	CreateView createWindow = CreateView.createWindow(db,loggedInUser, view);
    }

    public void leaveGroup(String string){
        db.leaveGroup(string,loggedInUser);
    }

    public void updateInformation () {
        UpdateView updateView = UpdateView.createWindow(db,loggedInUser);    
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