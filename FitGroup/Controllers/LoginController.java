package FitGroup.controllers;

import FitGroup.models.*;
import FitGroup.views.CoachDashboardView;
import FitGroup.views.DashboardView;
import FitGroup.views.LoginView;
import FitGroup.views.SignUpView;

public class LoginController {
    private Database db;
    private LoginView view;

    public LoginController (Database database, LoginView view) {
        this.db = database;
        this.view = view;
    }

    public int logIn (String username, String password) {
        User user = db.searchUser(username);
        if (user == null) return 0;
        else if (user.getPassword().equals(password)) {
            view.getFrame().setVisible(false);
            if (user.getType() == 0){
                DashboardView dashboardWindow = new DashboardView(user, db);}
            else {
                CoachDashboardView CoachdashboardWindow = new CoachDashboardView(user, db);}
            return 1;
        }
        else return -1;
    }

    public void signUp () {
        view.getFrame().setVisible(false);
        SignUpView signUpWindow = new SignUpView(db);
    }
}