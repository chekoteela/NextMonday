package com.sharkit.nextmonday.Users;

public class Users {
    private String Name,LastName,Email,Password;
    private static boolean firebaseUser;
    private static String UID;
    private  String moderator;
    public Users(){}

    public String getModerator() {
        return moderator;
    }

    public void setModerator(String moderator) {
        this.moderator = moderator;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public static boolean isFirebaseUser() {
        return firebaseUser;
    }

    public static void setFirebaseUser(boolean firebaseUser) {
        Users.firebaseUser = firebaseUser;
    }

    public static String getUID() {
        return UID;
    }

    public static void setUID(String UID) {
        Users.UID = UID;
    }
}
