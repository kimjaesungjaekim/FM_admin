package com.developer.fillme.service;

import com.developer.fillme.entity.UserEntity;
import com.developer.fillme.request.auth.LoginReq;
import com.developer.fillme.request.auth.LoginSSOReq;
import com.developer.fillme.request.auth.RefreshTokenReq;
import com.developer.fillme.request.auth.SendOtpFindIdReq;
import com.developer.fillme.request.auth.SendOtpFindPwReq;
import com.developer.fillme.request.auth.SignupReq;
import com.developer.fillme.request.auth.VerifyOtpFindIdReq;
import com.developer.fillme.request.auth.VerifyOtpFindPwReq;
import com.developer.fillme.response.auth.LoginResp;
import com.developer.fillme.response.auth.RefreshTokenResp;
import com.developer.fillme.response.auth.VerifyOtpFindIdResp;
import com.developer.fillme.response.auth.SignupResp;
import com.developer.fillme.response.auth.VerifyOtpFindPwResp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

public interface IAuthService extends LogoutHandler {
    SignupResp register(SignupReq signupReq);

    LoginResp login(LoginReq loginRequest);

    LoginResp loginWithFacebook(LoginSSOReq req);

    LoginResp loginWithKakao(LoginSSOReq req);

    LoginResp loginWithNaver(LoginSSOReq req);

    RefreshTokenResp refreshToken(RefreshTokenReq refreshTokenReq);

    void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication);
    VerifyOtpFindIdResp verifyOtpFindId(VerifyOtpFindIdReq verifyOtpReq);
    VerifyOtpFindPwResp verifyOtpFindPw(VerifyOtpFindPwReq verifyOtpReq);
    UserEntity sendOtpFindId(SendOtpFindIdReq sendOtpReq);
    UserEntity sendOtpFindPw(SendOtpFindPwReq sendOtpReq);
}
