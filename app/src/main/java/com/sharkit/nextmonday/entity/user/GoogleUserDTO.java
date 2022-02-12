package com.sharkit.nextmonday.entity.user;

import com.sharkit.nextmonday.entity.enums.Role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GoogleUserDTO {

    private String name;
    private String email;
    private Enum<Role> role;
    private String id;

}
