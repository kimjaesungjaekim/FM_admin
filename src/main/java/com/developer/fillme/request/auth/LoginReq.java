package com.developer.fillme.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginReq {
    @Schema(description = "username", example = "noname")
    private String username;

    @Schema(description = "password", example = "noname")
    private String password;
}
