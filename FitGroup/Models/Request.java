package FitGroup.models;

public class Request {


    private User user;
    private Group group;

    public Request (User user, Group group) {
        this.user = user;
        this.group = group;

    }   

    public User getUser() {
        return user;
    }

    public Group getGroup() {
        return group;
    }


}



