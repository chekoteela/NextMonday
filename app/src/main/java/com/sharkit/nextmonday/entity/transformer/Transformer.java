package com.sharkit.nextmonday.entity.transformer;

import com.sharkit.nextmonday.entity.user.FacebookUserDTO;
import com.sharkit.nextmonday.entity.user.GoogleUserDTO;
import com.sharkit.nextmonday.entity.user.User;
import com.sharkit.nextmonday.entity.user.UserDTO;

public class Transformer {

    protected User transformer(UserDTO userDTO){
        User user = new User();
        user.setRole(userDTO.getRole());
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        return user;
    }
    protected User transformer(GoogleUserDTO googleUserDTO){
        User user = new User();
        user.setRole(googleUserDTO.getRole());
        user.setId(googleUserDTO.getId());
        user.setName(googleUserDTO.getName());
        user.setEmail(googleUserDTO.getEmail());
        return user;
    }
    protected User transformer(FacebookUserDTO facebookUserDTO){
        User user = new User();
        user.setRole(facebookUserDTO.getRole());
        user.setId(facebookUserDTO.getId());
        user.setName(facebookUserDTO.getName());
        user.setEmail(facebookUserDTO.getEmail());
        return user;
    }
}
