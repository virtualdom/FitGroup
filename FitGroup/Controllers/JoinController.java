package FitGroup.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import FitGroup.models.Database;
import FitGroup.models.Group;
import FitGroup.models.Membership;
import FitGroup.models.User;
import FitGroup.views.CreateView;
import FitGroup.views.JoinView;

public class JoinController {

    private User loggedInUser;
    private Database db;
    private JoinView view;
    private String IC,creatdate;
    private Group group;
    

    public JoinController (Database database, JoinView view, User user) {
    	this.db = database;
        this.view = view;
        this.loggedInUser = user;
    }

    public boolean JoinGroup(String groupname,String IC) {
    	ArrayList<User> users = db.searchUsersByGroup(groupname);
        
    	  for (int i = 0; i < users.size(); i++) {
              if (users.get(i).getUsername().equals(loggedInUser.getUsername()))  {
            	  JOptionPane.showMessageDialog(null, "User already in this group", "InfoBox: " , JOptionPane.INFORMATION_MESSAGE);
            	  System.exit(0);
              }
    	  }
          group = db.searchGroup(groupname); 
          
          if (group.getIC().equals(IC)){
	        boolean result = db.addMembership(new Membership(loggedInUser,group,1));
	        if (result == true) {
	        	JOptionPane.showMessageDialog(null, "Successfully Join Group", "InfoBox: " , JOptionPane.INFORMATION_MESSAGE);
                return true;
            }
            return false;
          }
          else{
        	JOptionPane.showMessageDialog(null, "IC is not right ", "InfoBox: " , JOptionPane.INFORMATION_MESSAGE);
            return false;
          }
    }
    
    //Get Current Date As CreateDate.
    private void GetCurrentDate(){  
        creatdate="";  
        Date dt = new Date();  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        creatdate=sdf.format(dt);  
    }  
    
    
    //Generate IC code automatically, Set to constant value for temporary.
    private void GenerateIC(){
    	IC = "123456";
    }
    	
}
