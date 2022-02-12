package com.sharkit.nextmonday.entity.transformer;

import com.sharkit.nextmonday.entity.user.FacebookUserDTO;
import com.sharkit.nextmonday.entity.user.GoogleUserDTO;
import com.sharkit.nextmonday.entity.user.User;

public class UserTransformer {

    public static User transform(GoogleUserDTO googleUserDTO){
        User user = new User();
        user.setRole(googleUserDTO.getRole());
        user.setId(googleUserDTO.getId());
        user.setName(googleUserDTO.getName());
        user.setEmail(googleUserDTO.getEmail());
        return user;
    }

    public static User transform(FacebookUserDTO facebookUserDTO){
        User user = new User();
        user.setRole(facebookUserDTO.getRole());
        user.setId(facebookUserDTO.getId());
        user.setName(facebookUserDTO.getName());
        user.setEmail(facebookUserDTO.getEmail());
        return user;
    }
}
