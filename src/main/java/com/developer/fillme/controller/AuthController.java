package com.developer.fillme.controller;

import com.developer.fillme.model.BaseResponse;
import com.developer.fillme.request.auth.LoginReq;
import com.developer.fillme.request.auth.LoginSSOReq;
import com.developer.fillme.request.auth.RefreshTokenReq;
import com.developer.fillme.request.auth.SendOtpFindIdReq;
import com.developer.fillme.request.auth.SendOtpFindPwReq;
import com.developer.fillme.request.auth.VerifyOtpFindIdReq;
import com.developer.fillme.request.auth.SignupReq;
import com.developer.fillme.request.auth.VerifyOtpFindPwReq;
import com.developer.fillme.response.auth.LoginResp;
import com.developer.fillme.response.auth.LogoutResp;
import com.developer.fillme.response.auth.RefreshTokenResp;
import com.developer.fillme.response.auth.VerifyOtpFindIdResp;
import com.developer.fillme.response.auth.SignupResp;
import com.developer.fillme.response.auth.VerifyOtpFindPwResp;
import com.developer.fillme.service.IAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@Tag(name = "AUTHENTICATION & AUTHORIZATION", description = "AUTHENTICATION AND AUTHORIZATION")
@RequestMapping("api/auth")
public class AuthController {
    private final IAuthService authService;

    @Operation(summary = "REGISTER USER")
    @PostMapping("/register")
    public ResponseEntity<BaseResponse<SignupResp>> register(@RequestBody @Valid SignupReq request) {
        var response = authService.register(request);
        return ResponseEntity.status(CREATED).body(new BaseResponse<>(response, "Register successfully"));
    }

    @Operation(summary = "LOGIN USER")
    @PostMapping("/login-with-username")
    public ResponseEntity<BaseResponse<LoginResp>> login(@RequestBody @Valid LoginReq loginReq) {
        LoginResp loginResp = authService.login(loginReq);
        return ResponseEntity.ok().body(new BaseResponse<>(loginResp, "Login successful"));
    }

    @Operation(summary = "LOGIN FACEBOOK")
    @PostMapping("/login-with-facebook")
    public ResponseEntity<BaseResponse<LoginResp>> loginWithFacebook(@RequestBody @Valid LoginSSOReq loginReq) {
        LoginResp loginResp = authService.loginWithFacebook(loginReq);
        return ResponseEntity.ok().body(new BaseResponse<>(loginResp, "Login successful"));
    }

    @Operation(summary = "LOGIN KAKAO")
    @PostMapping("/login-with-kakao")
    public ResponseEntity<BaseResponse<LoginResp>> loginWithKakao(@RequestBody @Valid LoginSSOReq loginReq) {
        LoginResp loginResp = authService.loginWithKakao(loginReq);
        return ResponseEntity.ok().body(new BaseResponse<>(loginResp, "Login successful"));
    }

    @Operation(summary = "LOGIN NAVER")
    @PostMapping("/login-with-naver")
    public ResponseEntity<BaseResponse<LoginResp>> loginWithNaver(@RequestBody @Valid LoginSSOReq loginReq) {
        LoginResp loginResp = authService.loginWithNaver(loginReq);
        return ResponseEntity.ok().body(new BaseResponse<>(loginResp, "Login successful"));
    }

    @Operation(summary = "LOGOUT")
    @PostMapping("/logout")
    public ResponseEntity<LogoutResp> logout(HttpServletRequest request, HttpServletResponse response, Authentication auth) {
        authService.logout(request, response, auth);
        return ResponseEntity.ok().body(new LogoutResp("Logout Successfully"));
    }

    @Operation(summary = "REFRESH TOKEN")
    @PostMapping("/refresh-token")
    public ResponseEntity<BaseResponse<RefreshTokenResp>> refreshToken(@RequestBody @Valid RefreshTokenReq request) {
        RefreshTokenResp response = authService.refreshToken(request);
        return ResponseEntity.ok().body(new BaseResponse<>(response));
    }

    @Operation(summary = "FIND PASSWORD")
    @PostMapping("/find-password")
    public ResponseEntity<RefreshTokenResp> findPassword(@RequestBody @Valid RefreshTokenReq request) {
        RefreshTokenResp response = authService.refreshToken(request);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "FIND ID")
    @PostMapping("/find-id")
    public ResponseEntity<RefreshTokenResp> findId(@RequestBody @Valid RefreshTokenReq request) {
        RefreshTokenResp response = authService.refreshToken(request);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "SEND OTP")
    @PostMapping("/send-otp-find-id")
    public ResponseEntity<BaseResponse<Void>> sendOtpFindId(@RequestBody @Valid SendOtpFindIdReq sendOtpReq) {
        authService.sendOtpFindId(sendOtpReq);
        return ResponseEntity.ok().body(new BaseResponse<>(null));
    }

    @Operation(summary = "SEND OTP")
    @PostMapping("/send-otp-find-password")
    public ResponseEntity<BaseResponse<Void>> sendOtpFindPw(@RequestBody @Valid SendOtpFindPwReq sendOtpReq) {
        authService.sendOtpFindPw(sendOtpReq);
        return ResponseEntity.ok().body(new BaseResponse<>(null));
    }

    @Operation(summary = "VERIFY OTP FIND ID")
    @PostMapping("/verify-otp-find-id")
    public ResponseEntity<BaseResponse<VerifyOtpFindIdResp>> verifyOtpFindId(@RequestBody @Valid VerifyOtpFindIdReq verifyOtpReq) {
        VerifyOtpFindIdResp verifyOtpResp = authService.verifyOtpFindId(verifyOtpReq);
        return ResponseEntity.ok().body(new BaseResponse<>(verifyOtpResp));
    }

    @Operation(summary = "VERIFY OTP FIND PASSWORD")
    @PostMapping("/verify-otp-find-password")
    public ResponseEntity<BaseResponse<VerifyOtpFindPwResp>> verifyOtpFindPw(@RequestBody @Valid VerifyOtpFindPwReq verifyOtpReq) {
        VerifyOtpFindPwResp verifyOtpResp = authService.verifyOtpFindPw(verifyOtpReq);
        return ResponseEntity.ok().body(new BaseResponse<>(verifyOtpResp));
    }


}
