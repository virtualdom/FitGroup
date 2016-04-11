package FitGroup.controllers;

import javax.swing.JOptionPane;

import FitGroup.models.Database;
import FitGroup.models.User;
import FitGroup.views.CoachDashboardView;
import FitGroup.views.DashboardView;
import FitGroup.views.LoginView;
import FitGroup.views.SignUpView;
import FitGroup.views.UpdateView;

public class UpdateController {
    private Database db;
    private UpdateView view;
    private User loggedUser;

    public UpdateController (Database database, UpdateView view) {
    	this.db = database;
        this.view = view;
    }

    public void update (User LoggedUser, int age, int weight) {
    	int oldAge = LoggedUser.getAge();
    	int oldWeight = LoggedUser.getWeight();
    	LoggedUser.setAge(age);
    	LoggedUser.setWeight(weight);
    	db.updateUser(LoggedUser);
    	JOptionPane.showMessageDialog(null, "Successfully update information", "InfoBox: " , JOptionPane.INFORMATION_MESSAGE);
     }

    public void cancel () {
       // view.getFrame().setVisible(false);
      //  UpdateView window = new UpdateView(db);
    }
}