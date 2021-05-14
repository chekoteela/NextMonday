package com.sharkit.nextmonday.Users;

public class MessageFromUsers {
    private String mail, name, text_massage;

    public MessageFromUsers() {}

    public MessageFromUsers(String mail, String name, String text_massage) {
        this.mail = mail;
        this.name = name;
        this.text_massage = text_massage;
    }

    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getText_massage() {
        return text_massage;
    }
    public void setText_massage(String text_massage) {
        this.text_massage = text_massage;
    }
}
