package com.developer.fillme.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenReq {
    @NotBlank(message = "Not Blank ")
    private String refreshToken;
}
