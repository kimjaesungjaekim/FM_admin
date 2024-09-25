package com.developer.fillme.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyOtpFindIdReq extends SendOtpFindIdReq {
	@Schema(description = "otp")
	private String otp;
}
