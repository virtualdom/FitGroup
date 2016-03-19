package FitGroup.models;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Database {
    private ArrayList<User> users;
    private ArrayList<Group> groups;
    private ArrayList<Membership> memberships;

    public Database () {
        importUsers();
        importGroups();
        importMemberships();
    }

    private void importUsers () {
        users = new ArrayList<User>(5);
        int age, weight, score;
        String username, password;
        Scanner userScanner;    
        
        try {
            userScanner = new Scanner(new File("./FitGroup/Models/users.txt"));
        } catch (FileNotFoundException e) {
            userScanner = new Scanner(System.in);
            System.out.println("ERROR: could not open users.txt");
            System.exit(0);
        }

        while (userScanner.hasNext()) {
            username = userScanner.next();
            password = userScanner.next();
            age = userScanner.nextInt();
            weight = userScanner.nextInt();
            score = userScanner.nextInt();
            users.add(new User(username, password, age, weight, score));
        }
        
        userScanner.close();
    }

    private void importGroups () {
        groups = new ArrayList<Group>(3);
        String groupname, IC, createdate;
        Scanner groupScanner;
        
        try {
            groupScanner = new Scanner(new File("./FitGroup/Models/groups.txt"));
        } catch (FileNotFoundException e) {
            groupScanner = new Scanner(System.in);
            System.out.println("ERROR: could not open groups.txt");
            System.exit(0);
        }

        while (groupScanner.hasNext()) {
            groupname = groupScanner.next();
            createdate = groupScanner.next();
            IC = groupScanner.next();
            DateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
            Date date = null;

            try {
                date = formatter.parse(createdate);
            } catch (ParseException e) {
                System.out.println("ERROR: group.txt experienced a parsing error");
                System.exit(0);
            }
                
            groups.add(new Group(groupname, date, IC));
        }
        groupScanner.close();
    }

    private void importMemberships () {
        memberships = new ArrayList<Membership>(3);
        String memberGroup;
        String memberUser;
        int isadmin;
        Scanner memberScanner;

        try {
            memberScanner = new Scanner(new File("./FitGroup/Models/memberships.txt"));
        } catch (FileNotFoundException e) {
            memberScanner = new Scanner(System.in);
            System.out.println("ERROR: could not open memberships.txt");
            System.exit(0);
        }
        while (memberScanner.hasNext()) {
            memberGroup = memberScanner.next();
            memberUser = memberScanner.next();
            isadmin = memberScanner.nextInt();
            User tmpuser = searchUser(memberUser);
            Group tmpgroup = searchGroup(memberGroup);
            
            memberships.add(new Membership(tmpuser, tmpgroup, isadmin));
        }
        memberScanner.close();
    }

    public User searchUser (String username) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) return users.get(i);
        }
        return null;
    }

    public Group searchGroup (String groupname) {
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i).getname().equals(groupname)) return groups.get(i);
        }
        return null;
    }

    public ArrayList<Group> getGroups () {
        return groups;
    }

    public void addUser (User user) {
        BufferedWriter userTextfile;
        try {
            userTextfile = new BufferedWriter(new FileWriter("./FitGroup/Models/users.txt", true));
            userTextfile.write(user.getUsername() + "\t" + user.getPassword() + "\t" + user.getWeight() + "\t" + user.getAge() + "\t" + user.getScore() + "\n");
            userTextfile.close();
            users.add(user);
        } catch (IOException error) {
            System.out.println("ERROR: could not open users.txt");
            System.exit(0);
        }
    }

    public void updateUser (User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(user.getUsername())) {
                users.set(i, user);
                break;
            }

            Scanner userReader = null;    
            BufferedWriter userWriter = null;
            File file = null;
            try {
                file = new File("./FitGroup/Models/users.txt");
                userReader = new Scanner(file);
                userWriter = new BufferedWriter(new FileWriter("./FitGroup/Models/users2.txt", true));
            } catch (Exception e) {
                System.out.println("ERROR: could not open users.txt for update user");
                System.exit(0);
            }

            String username, line;
            while (userReader.hasNext()) {
                username = userReader.next();
                try {
                    if (username.equals(user.getUsername())) {
                        userWriter.write(user.getUsername() + "\t" + user.getPassword() + "\t" + user.getWeight() + "\t" + user.getAge() + "\t" + user.getScore() + "\n");
                        username = userReader.nextLine();
                    }
                    else
                        userWriter.write(username + userReader.nextLine() + "\n");
                } catch (IOException e) {
                    System.out.println("ERROR: could not finish updating users.txt for update user");
                    System.exit(0);
                }
            }

            try {
                userReader.close();
                userWriter.close();
            } catch (IOException e) {
                System.out.println("ERROR: could not close files for update user");
                System.exit(0);
            }

            file.delete();
            // try {
            file = new File("./FitGroup/Models/users2.txt");
            // } catch (FileNotFoundException e) {
            //     System.out.println("ERROR: could not rename users2.txt");
            //     System.exit(0);
            // }
            file.renameTo(new File("./FitGroup/Models/users.txt"));
        }
    }

    public ArrayList<User> searchUsersByGroup (String groupname) {
        ArrayList<User> tmpusers = new ArrayList<User>(0);
        for (int i = 0; i < memberships.size(); i++) {
            if (memberships.get(i).getGroup().getname().equals(groupname))
            {
                tmpusers.add(memberships.get(i).getUser());
            }             
        }
        return tmpusers;
    }
}