package FitGroup;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.util.*;

public class Database {
    private ArrayList<User> users;

    public Database () {
        users = new ArrayList<User>(5);

        int age, weight;
        String username, password;
        Scanner userScanner;    
        try {
            userScanner = new Scanner(new File("./FitGroup/users.txt"));
        } catch (FileNotFoundException e) {
            userScanner = new Scanner(System.in);
            System.out.println("ERROR: users.txt does not exist");
            System.exit(0);
        }
        while (userScanner.hasNext()) {
            username = userScanner.next();
            password = userScanner.next();
            age = userScanner.nextInt();
            weight = userScanner.nextInt();
            users.add(new User(username, password, age, weight));
        }
    }

    public User searchUser (String username) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) return users.get(i);
        }
        return null;
    }
}