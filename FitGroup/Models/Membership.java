package FitGroup.models;

public class Membership {
    private User user;
    private Group group;
    private int isAdmin ;

    public Membership (User user, Group group, int isAdmin) {
        this.user = user;
        this.group = group;
        this.isAdmin = isAdmin;
    }

    public Membership (User user, Group group) {
        this.user = user;
        this.group = group;
        this.isAdmin = 0;
    }

    public User getUser() {
        return user;
    }

    public Group getGroup() {
        return group;
    }

    public int getIsAdmin () {
        return isAdmin;
    }

    public void setAdmin (int isAdmin) {
        this.isAdmin = isAdmin;
    }
}
