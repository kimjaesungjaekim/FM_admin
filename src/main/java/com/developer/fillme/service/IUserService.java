package com.developer.fillme.service;

import com.developer.fillme.request.user.ChangePasswordReq;
import com.developer.fillme.request.user.EditUserReq;
import com.developer.fillme.response.auth.SignupResp;
import com.developer.fillme.response.user.EditUserResp;

import javax.security.sasl.AuthenticationException;

public interface IUserService {
	EditUserResp updateUser(EditUserReq editUserReq);
	EditUserResp getUserInfo();
	void changePassword(ChangePasswordReq req);
	void deleteAccount();
}
