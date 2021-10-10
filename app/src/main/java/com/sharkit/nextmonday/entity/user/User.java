package com.sharkit.nextmonday.entity.user;

import com.sharkit.nextmonday.entity.enums.Role;
import com.sharkit.nextmonday.entity.transformer.TransformToUser;
import com.sharkit.nextmonday.entity.transformer.Transformer;

public class User extends Transformer implements TransformToUser {
    private String name;
    private String email;
    private Enum<Role> role;
    private String id;
    private String password;
    private String lastName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Enum<Role> getRole() {
        return role;
    }

    public void setRole(Enum<Role> role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public User transform(GoogleUserDTO googleUserDTO) {
        return transformer(googleUserDTO);
    }

    @Override
    public User transform(UserDTO userDTO) {
        return transformer(userDTO);
    }

    @Override
    public User transform(FacebookUserDTO facebookUserDTO) {
        return transformer(facebookUserDTO);
    }
}
