package com.sharkit.nextmonday.entity.user;

import com.sharkit.nextmonday.entity.enums.Role;

public class GoogleUserDTO {

    private String name;
    private String email;
    private Enum<Role> role;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Enum<Role> getRole() {
        return role;
    }

    public void setRole(Enum<Role> role) {
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
