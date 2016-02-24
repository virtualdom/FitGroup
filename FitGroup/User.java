package FitGroup;

public class User {
    private String username;
    private String password;
    private int age;
    private int weight;

    public User (String username, String password, int age, int weight){
        this.username = username;
        this.password = password;
        this.age = age;
        this.weight = weight;
    }

    public String getUsername () {
        return username;
    }

    public String getPassword () {
        return password;
    }

    public int getAge () {
        return age;
    }

    public int getWeight () {
        return weight;
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
}