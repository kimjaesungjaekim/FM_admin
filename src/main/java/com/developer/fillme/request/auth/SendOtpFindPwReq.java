package com.developer.fillme.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendOtpFindPwReq extends SendOtpFindIdReq {
	@Schema(description = "email")
	private String email;
}
