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
import FitGroup.views.LoginView;
import FitGroup.views.SignUpView;
import FitGroup.views.CreateView;

public class CreateController {
	
    private Database db;
    private CreateView view;
    private User loggedInUser;
    private String IC,creatdate;
    

    public CreateController (Database database, CreateView view, User loggedInUser) {
    	this.db = database;
        this.view = view;
        this.loggedInUser = loggedInUser;
    }

    public void CreateGroup(String groupname) {
        Group group = db.searchGroup(groupname);
        if (group == null)    {
        	//DashboardView dashboardWindow = new DashboardView(user, db);
        	GetCurrentDate();
            GenerateIC(6);
            
            boolean result = db.addGroup(new Group(groupname,creatdate,IC));
	        Group newgroup = db.searchGroup(groupname);
            if (result == true)    {
            	JOptionPane.showMessageDialog(null, "Successfully Create Group Invitiation Code is:"+IC, "InfoBox: " , JOptionPane.INFORMATION_MESSAGE);
            	db.addMembership(new Membership(loggedInUser,newgroup,0));
            	}
            //view.getFrame().setVisible(false);
            }
        else {
        	JOptionPane.showMessageDialog(null, "Group exists in system", "InfoBox: " , JOptionPane.INFORMATION_MESSAGE);
        }
        
    }
    
    //Get Current Date As CreateDate.
    public void GetCurrentDate(){  
        creatdate="";  
        Date dt = new Date();  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        creatdate=sdf.format(dt);  
    }  
    	    
    //Generate IC code automatically
    private void GenerateIC(int length){
    	//IC = "123456";
    	String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";  
        Random random = new Random();  
        StringBuffer buf = new StringBuffer();  
        for (int i = 0; i < length; i++) {  
            int num = random.nextInt(36);  
            buf.append(str.charAt(num));
        }
        IC = buf.toString();  
    }
}
