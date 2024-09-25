package com.developer.fillme.response.auth;

import com.developer.fillme.response.user.EditUserResp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResp {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private EditUserResp data;
}

