package FitGroup.controllers;

import java.util.*;
import javax.swing.JOptionPane;

import FitGroup.models.Database;
import FitGroup.models.Group;
import FitGroup.models.Membership;
import FitGroup.models.User;
import FitGroup.models.Request;

import FitGroup.views.DashboardView;
import FitGroup.views.ApproveRequestsView;

public class ApproveRequestsController {
	
    private Database db;
    private ApproveRequestsView view;
    private User loggedInUser;	    

    public ApproveRequestsController (Database database, ApproveRequestsView view, User loggedInUser) {
    	this.db = database;
        this.view = view;
        this.loggedInUser = loggedInUser;
    }

    public String [] getAdminGroupNames () {
        ArrayList<Membership> memberships = db.searchMembershipsByUser(loggedInUser.getUsername());
        ArrayList<String> adminGroupNamesList = new ArrayList<String>(0);
        
        for (int i = 0; i < memberships.size(); i++)
        	if (memberships.get(i).getIsAdmin() == 1)
        		adminGroupNamesList.add(memberships.get(i).getGroup().getname());

        String [] groupNames = new String [adminGroupNamesList.size()];
        for (int i = 0; i < adminGroupNamesList.size(); i++)
        	groupNames[i] = adminGroupNamesList.get(i);

        return groupNames;
    }

    public ArrayList<Request> getRequests (String groupName) {
        return db.searchRequestsByGroup (groupName);
    }

    public boolean approve (Request request) {
        Membership newMembership = new Membership(request.getUser(), request.getGroup());
        db.deleteRequest(request);
        JOptionPane.showMessageDialog(null, "Request Approved!", "InfoBox: " , JOptionPane.INFORMATION_MESSAGE);
        return db.addMembership(newMembership);
    }
}
