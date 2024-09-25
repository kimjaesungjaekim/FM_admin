package com.developer.fillme.request.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordReq {
	@Schema(name = "oldPassword", example = "oldPassword")
	@NotEmpty(message = "Old password is required")
	private String oldPassword;

	@Schema(name = "newPassword", example = "newPassword")
	@NotEmpty(message = "New password is required")
	private String newPassword;
}
