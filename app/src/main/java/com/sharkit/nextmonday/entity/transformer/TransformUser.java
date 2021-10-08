package com.sharkit.nextmonday.entity.transformer;

import com.sharkit.nextmonday.entity.user.FacebookUserDTO;
import com.sharkit.nextmonday.entity.user.GoogleUserDTO;
import com.sharkit.nextmonday.entity.user.User;
import com.sharkit.nextmonday.entity.user.UserDTO;

public interface TransformUser {
    User transform(GoogleUserDTO googleUserDTO);
    User transform(UserDTO userDTO);
    User transform(FacebookUserDTO facebookUserDTO);

}
