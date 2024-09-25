package com.developer.fillme.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class SendOtpFindIdReq {
	@Schema(description = "nickname")
	private String nickname;
	@Schema(description = "birthday")
	private LocalDate birthday;
	@Schema(description = "phone")
	private String phone;
}
