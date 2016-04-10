package FitGroup.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.swing.JOptionPane;

import FitGroup.models.Database;
import FitGroup.models.Group;
import FitGroup.models.Membership;
import FitGroup.models.User;
import FitGroup.views.DashboardView;
import FitGroup.views.ChangeICView;

public class ChangeICController {
	
    private Database db;
    private ChangeICView view;
    private Group group;
    

    public ChangeICController (Database database, ChangeICView view, Group group) {
    	this.db = database;
        this.view = view;
        this.group = group;
    }

    public void changeIC(String ic) {
        group.SetIC(ic);
        db.updateGroup(group);
        JOptionPane.showMessageDialog(null, "Changed IC", "InfoBox: " , JOptionPane.INFORMATION_MESSAGE);        
    }
}
