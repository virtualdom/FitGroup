import FitGroup.models.Database;
import FitGroup.views.LoginView;

public class main {
    public static void main (String[] args) {
        Database db = new Database();
        LoginView window = new LoginView(db);  
    }
}