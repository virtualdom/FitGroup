package FitGroup.models;

public class User {
    private String username;
    private String password;
    private int age;
    private int weight;
    private int score;
    private int userType;

    public User (String username, String password, int age, int weight, int userType, int score) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.weight = weight;
        this.userType = userType;
        this.score = score;
    }

    public User (String username, String password, int age, int weight, int userType) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.weight = weight;
        this.userType = userType;
        this.score = 0;
    }

    public String getUsername () {
        return username;
    }

    public String getPassword () {
        return password;
    }

    public int getType () {
        return userType;
    }

    public int getAge () {
        return age;
    }

    public int getWeight () {
        return weight;
    }
    
    public int getScore () {
        return score;
    }

    public void setUsername (String username) {
        this.username = username;
    }

    public void setAge (int age) {
        this.age = age;
    }

    public void setWeight (int weight) {
        this.weight = weight;
    }
    
    public void setScore (int score) {
        this.score = score;
    } 
}