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
    private ArrayList<Request> requests;

    public Database () {
        importUsers();
        importGroups();
        importMemberships();
        importRequests ();
    }

    private void importUsers () {
        users = new ArrayList<User>(0);
        int age, weight, score, userType;
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
            userType = userScanner.nextInt();
            score = userScanner.nextInt();
            users.add(new User(username, password, age, weight, userType, score));
        }

        userScanner.close();
    }

    private void importGroups () {
        groups = new ArrayList<Group>(0);
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

            groups.add(new Group(groupname, createdate, IC));
        }
        groupScanner.close();
    }

    private void importMemberships () {
        memberships = new ArrayList<Membership>(0);
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

    private void importRequests () {
        requests = new ArrayList<Request>(0);
        String requestGroup;
        String requestUser;
        Scanner requestScanner;

        try {
            requestScanner = new Scanner(new File("./FitGroup/Models/requests.txt"));
        } catch (FileNotFoundException e) {
            requestScanner = new Scanner(System.in);
            System.out.println("ERROR: could not open requests.txt");
            System.exit(0);
        }
        while (requestScanner.hasNext()) {
            requestUser = requestScanner.next();
            requestGroup = requestScanner.next();
            User tmpuser = searchUser(requestUser);
            Group tmpgroup = searchGroup(requestGroup);

            requests.add(new Request(tmpuser, tmpgroup ));
        }
        requestScanner.close();
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
            userTextfile.write(user.getUsername() + "\t" + user.getPassword() + "\t" + user.getWeight() + "\t" + user.getAge() + "\t" + user.getType() + "\t" + user.getScore() + "\n");
            userTextfile.close();
            users.add(user);
        } catch (IOException error) {
            System.out.println("ERROR: could not open users.txt");
            System.exit(0);
        }
    }

    public void updateUser (User user) {
        Scanner userReader = null;    
        BufferedWriter userWriter = null;
        File file = null;
        String username, line;
        
        try {
            file = new File("./FitGroup/Models/users.txt");
            userReader = new Scanner(file);
            userWriter = new BufferedWriter(new FileWriter("./FitGroup/Models/users2.txt", true));
        } catch (Exception e) {
            System.out.println("ERROR: could not open users.txt for update user");
            System.exit(0);
        }
        
        for (int i = 0; i < users.size(); i++)
            if (users.get(i).getUsername().equals(user.getUsername())) {
                users.set(i, user);
                break;
            }

        while (userReader.hasNext()) {
            username = userReader.next();
            try {
                if (username.equals(user.getUsername())) {
                    userWriter.write(user.getUsername() + "\t" + user.getPassword() + "\t" + user.getWeight() + "\t" + user.getAge() + "\t" + user.getType() + "\t" + user.getScore() + "\n");
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

    public void updateGroup (Group group) {
        Scanner groupReader = null;    
        BufferedWriter groupWriter = null;
        File file = null;
        String groupname, date, ic;
        
        try {
            file = new File("./FitGroup/Models/groups.txt");
            groupReader = new Scanner(file);
            groupWriter = new BufferedWriter(new FileWriter("./FitGroup/Models/groups2.txt", true));
        } catch (Exception e) {
            System.out.println("ERROR: could not open groups.txt for update group");
            System.exit(0);
        }
        
        for (int i = 0; i < groups.size(); i++)
            if (groups.get(i).getname().equals(group.getname())) {
                groups.set(i, group);
                break;
            }

        while (groupReader.hasNext()) {
            groupname = groupReader.next();
            date = groupReader.next();
            ic = groupReader.next();
            try {
                if (groupname.equals(group.getname()))
                    groupWriter.write(group.getname() + "\t" + group.getCreateDate() + "\t" + group.getIC() + "\n");
                else
                    groupWriter.write(groupname + "\t" + date + "\t" + ic + "\n");
            } catch (IOException e) {
                System.out.println("ERROR: could not finish updating groups.txt for update group");
                System.exit(0);
            }
        }

        try {
            groupReader.close();
            groupWriter.close();
        } catch (IOException e) {
            System.out.println("ERROR: could not close files for update group");
            System.exit(0);
        }

        file.delete();
    // try {
        file = new File("./FitGroup/Models/groups2.txt");
    // } catch (FileNotFoundException e) {
    //     System.out.println("ERROR: could not rename users2.txt");
    //     System.exit(0);
    // }
        file.renameTo(new File("./FitGroup/Models/groups.txt"));
    }

    public void updateMembership (Membership membership) {
        Scanner membershipReader = null;    
        BufferedWriter membershipWriter = null;
        File file = null;
        String groupname, username, adminStatus;
        
        try {
            file = new File("./FitGroup/Models/memberships.txt");
            membershipReader = new Scanner(file);
            membershipWriter = new BufferedWriter(new FileWriter("./FitGroup/Models/memberships2.txt", true));
        } catch (Exception e) {
            System.out.println("ERROR: could not open memberships.txt for update membership");
            System.exit(0);
        }
        
        for (int i = 0; i < memberships.size(); i++)
            if (memberships.get(i).getUser().getUsername().equals(membership.getUser().getUsername()) && memberships.get(i).getGroup().getname().equals(membership.getGroup().getname())) {
                memberships.set(i, membership);
                break;
            }

        while (membershipReader.hasNext()) {
            groupname = membershipReader.next();
            username = membershipReader.next();
            adminStatus = membershipReader.next();
            try {
                if (username.equals(membership.getUser().getUsername()) && groupname.equals(membership.getGroup().getname())) {
                    membershipWriter.write(membership.getGroup().getname() + "\t" + membership.getUser().getUsername() + "\t" + membership.getIsAdmin() + "\n");
                }
                else
                    membershipWriter.write(groupname + "\t" + username + "\t" + adminStatus + "\n");
            } catch (IOException e) {
                System.out.println("ERROR: could not finish updating users.txt for update user");
                System.exit(0);
            }
        }

        try {
            membershipReader.close();
            membershipWriter.close();
        } catch (IOException e) {
            System.out.println("ERROR: could not close files for update Membership");
            System.exit(0);
        }

        file.delete();
    // try {
        file = new File("./FitGroup/Models/memberships2.txt");
    // } catch (FileNotFoundException e) {
    //     System.out.println("ERROR: could not rename users2.txt");
    //     System.exit(0);
    // }
        file.renameTo(new File("./FitGroup/Models/memberships.txt"));
    }


    public boolean addGroup (Group group) {
        BufferedWriter userTextfile;
        try {
            userTextfile = new BufferedWriter(new FileWriter("./FitGroup/Models/groups.txt", true));
            userTextfile.write(group.getname() + "\t" + group.getCreateDate() + "\t" + group.getIC()+ "\n");
            userTextfile.close();
            boolean result = groups.add(group);
            return result;

        } catch (IOException error) {
            System.out.println("ERROR: could not open groups.txt");
            return false;
        }
    }


    public boolean addMembership (Membership membership) {
        BufferedWriter userTextfile;
        try {
            userTextfile = new BufferedWriter(new FileWriter("./FitGroup/Models/memberships.txt", true));
            userTextfile.write(membership.getGroup().getname() + "\t" + membership.getUser().getUsername() + "\t" + membership.getIsAdmin()+ "\n");
            userTextfile.close();
            boolean result = memberships.add(membership);
            return result;

        } catch (IOException error) {
            System.out.println("ERROR: could not open groups.txt");
            return false;
        }
    }

    public boolean addRequest(Request request){
        BufferedWriter userTextfile;
        try {
            userTextfile = new BufferedWriter(new FileWriter("./FitGroup/Models/requests.txt", true));
            userTextfile.write(request.getUser().getUsername() + "\t" + request.getGroup().getname() +  "\n");
            userTextfile.close();
            boolean result = requests.add(request);
            return result;
        } catch (IOException error) {
            System.out.println("ERROR: could not open requirements.txt");
            return false;
        }
    }

    public void leaveGroup(String groupname,User user) {
        
        Scanner userReader = null;    
        BufferedWriter userWriter = null;
        File file = null;
        String username, groupName, adminstatus;
        
        try {
            file = new File("./FitGroup/Models/memberships.txt");
            userReader = new Scanner(file);
            userWriter = new BufferedWriter(new FileWriter("./FitGroup/Models/memberships2.txt", true));
        } catch (Exception e) {
            System.out.println("ERROR: could not open memberships.txt for update membership");
            System.exit(0);
        }
        
        for (int i = 0; i < memberships.size(); i++)
            if (memberships.get(i).getUser().getUsername().equals(user.getUsername())&&memberships.get(i).getGroup().getname().equals(groupname)) {
                memberships.remove(i);
                break;
            }
            
            
        while (userReader.hasNext()) {
            groupName = userReader.next();    
            username = userReader.next();    
            adminstatus = userReader.next();    
            
            try {
                if(!groupName.equals(groupname) || !username.equals(user.getUsername()))
                    userWriter.write(groupName + "\t" + username + "\t" + adminstatus + "\n");
            } catch (IOException e) {
                System.out.println("ERROR: could not finish updating memberships.txt for update membership");
                System.exit(0);
            }
            
        }
            
        try {
            userReader.close();
            userWriter.close();
        } catch (IOException e) {
            System.out.println("ERROR: could not close files for update membership");
            System.exit(0);
        }
         
        file.delete();
        file = new File("./FitGroup/Models/memberships2.txt");
        file.renameTo(new File("./FitGroup/Models/memberships.txt"));
    }

    public ArrayList<Request> searchRequestsByGroup (String groupName) {
        ArrayList<Request> tempreq = new ArrayList<Request>(0);
        for (int i = 0; i < requests.size(); i++) {
            if (requests.get(i).getGroup().getname().equals(groupName))
                tempreq.add(requests.get(i));
        }
        return tempreq;
    }

    public Membership searchMembership (String groupname, String username) {
        for (int i = 0; i < memberships.size(); i++) {
            if (memberships.get(i).getGroup().getname().equals(groupname) && memberships.get(i).getUser().getUsername().equals(username))
                return memberships.get(i);
        }
        return null;
    }

    public ArrayList<Membership> searchMembershipsByUser (String username) {
        ArrayList<Membership> tempmem = new ArrayList<Membership>(0);
        for (int i = 0; i < memberships.size(); i++) {
            if (memberships.get(i).getUser().getUsername().equals(username))
            {
                tempmem.add(memberships.get(i));
            }             
        }
        return tempmem;
    }

    public ArrayList<Membership> searchMembershipsByGroup (String groupname) {
        ArrayList<Membership> tempmem = new ArrayList<Membership>(0);
        for (int i = 0; i < memberships.size(); i++) {
            if (memberships.get(i).getGroup().getname().equals(groupname))
            {
                tempmem.add(memberships.get(i));
            }             
        }
        return tempmem;
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

    public ArrayList<Group> searchGroupsByUser (String username) {
        ArrayList<Group> tmpgroups = new ArrayList<Group>(0);
        for (int i = 0; i < memberships.size(); i++) {
            if (memberships.get(i).getUser().getUsername().equals(username))
            {
                tmpgroups.add(memberships.get(i).getGroup());
            }             
        }
        return tmpgroups;
    }

    public void deleteRequest (Request request) {
        Scanner requestReader = null;    
        BufferedWriter requestWriter = null;
        File file = null;
        
        try {
            file = new File("./FitGroup/Models/requests.txt");
            requestReader = new Scanner(file);
            requestWriter = new BufferedWriter(new FileWriter("./FitGroup/Models/requests2.txt", true));
        } catch (Exception e) {
            System.out.println("ERROR: could not open requests.txt for delete requests");
            System.exit(0);
        }
        
        for (int i = 0; i < requests.size(); i++)
            if (requests.get(i).getUser().getUsername().equals(request.getUser().getUsername()) && requests.get(i).getGroup().getname().equals(request.getGroup().getname()))
                requests.remove(i);

        String username, groupname;
        while (requestReader.hasNext()) {
            username = requestReader.next();
            groupname = requestReader.next();
            try {
                if (!username.equals(request.getUser().getUsername()) || !groupname.equals(request.getGroup().getname()))
                    requestWriter.write(username + "\t" + groupname + "\n");
            } catch (IOException e) {
                System.out.println("ERROR: could not finish updating requests.txt for delete request");
                System.exit(0);
            }
        }

        try {
            requestReader.close();
            requestWriter.close();
        } catch (IOException e) {
            System.out.println("ERROR: could not close files for update request");
            System.exit(0);
        }

        file.delete();
        file = new File("./FitGroup/Models/requests2.txt");
        file.renameTo(new File("./FitGroup/Models/requests.txt"));
    }
}