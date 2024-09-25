package com.developer.fillme.response.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class VerifyOtpFindIdResp {
	private String email;
}
