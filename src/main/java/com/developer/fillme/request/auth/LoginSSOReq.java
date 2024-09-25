package com.developer.fillme.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginSSOReq {
    @Schema(description = "Access token from Facebook")
    @NotBlank(message = "Access token is required")
    private String accessToken;
}
