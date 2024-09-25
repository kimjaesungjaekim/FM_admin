package com.developer.fillme.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.developer.fillme.model.BaseResponse;
import com.developer.fillme.request.user.ChangePasswordReq;
import com.developer.fillme.request.user.EditUserReq;
import com.developer.fillme.response.user.EditUserResp;
import com.developer.fillme.service.IUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@Tag(name = "USER", description = "API USER INFO")
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final IUserService userService;

    @Operation(summary = "UPDATE USER INFO")
    @PutMapping("/update-profile")
    public ResponseEntity<BaseResponse<EditUserResp>> updateUser(@RequestBody @Valid EditUserReq input) {
        EditUserResp editUserResp = userService.updateUser(input);
        return ResponseEntity.ok().body(new BaseResponse<>(editUserResp, "Update user info successfully"));
    }

    @Operation(summary = "GET USER INFO")
    @GetMapping("/get-profile")
    public ResponseEntity<BaseResponse<EditUserResp>> getUserInfo() {
        EditUserResp editUserResp = userService.getUserInfo();
        return ResponseEntity.ok().body(new BaseResponse<>(editUserResp));
    }

    @Operation(summary = "CHANGE PASSWORD")
    @PostMapping("/change-password")
    public ResponseEntity<BaseResponse<String>> changePassword(@RequestBody @Valid ChangePasswordReq req) {
        userService.changePassword(req);
        return ResponseEntity.ok().body(new BaseResponse<>("Update password successfully"));
    }

    @Operation(summary = "DELETE USER")
    @DeleteMapping("/delete-account")
    public ResponseEntity<BaseResponse<String>> deleteAccount() {
        userService.deleteAccount();
        return ResponseEntity.ok().body(new BaseResponse<>("Delete successfully"));
    }
}
