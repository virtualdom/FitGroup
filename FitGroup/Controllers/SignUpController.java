package FitGroup.controllers;

import FitGroup.models.*;
import FitGroup.views.DashboardView;
import FitGroup.views.LoginView;
import FitGroup.views.SignUpView;

public class SignUpController {
    private Database db;
    private SignUpView view;

    public SignUpController (Database database, SignUpView view) {
    	this.db = database;
        this.view = view;
    }

    public int signUp (String username, String password, int age, int weight) {
        User user = db.searchUser(username);
        if (user != null) return 0;
        else {
            db.addUser(new User(username, password, age, weight));
            user = db.searchUser(username);
            view.getFrame().setVisible(false);
            DashboardView dashboardWindow = new DashboardView(user, db);
            return 1;
        }
    }

    public void cancel () {
        view.getFrame().setVisible(false);
        LoginView window = new LoginView(db);
    }
}
