package com.developer.fillme.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyOtpFindPwReq extends SendOtpFindPwReq {
	@Schema(description = "otp")
	private String otp;
}
