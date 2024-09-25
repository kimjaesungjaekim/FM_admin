package com.developer.fillme.response.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetail {
    private Long id;
    private String username;
    private Integer avatar;
    private String name; // fullName
}
