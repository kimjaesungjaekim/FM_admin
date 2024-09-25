package com.developer.fillme.request.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import static com.developer.fillme.utils.RegexUtils.REGEX_DATE_YYYY_MM_DD;
import static com.developer.fillme.utils.RegexUtils.REGEX_EMAIL_REGEX;

@Getter
@Setter
public class SignupReq {
        @NotBlank(message = "username is not empty")
        @Schema(description = "Username ID", example = "noname")
        @Pattern(regexp = REGEX_EMAIL_REGEX, message = "username is not valid format")
        private String username;

        @NotBlank(message = "password is not empty")
        @Schema(description = "Password", example = "noname")
        private String password;

        @NotBlank(message = "name is not empty")
        @Schema(description = "name", example = "Full Name")
        private String name;

        @NotBlank(message = "phone is not empty")
        @Schema(description = "Số điện thoại", example = "0902288686")
        private String phone;

        @NotBlank(message = "gender is not empty")
        @Schema(description = "gender", example = "MALE")
        @Pattern(regexp = "MALE|FEMALE", message = "gender is not valid example: MALE|FEMALE")
        private String gender;

        @Schema(description = "birthday", example = "1999-01-01")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        @Pattern(regexp = REGEX_DATE_YYYY_MM_DD, message = "birthday is not valid format yyyy-MM-dd")
        private String birthday;

        @Schema(description = "address", example = "Korea")
        private String address;

        @Schema(description = "referral Code", example = "ABC12345")
        private String referralCode;
}

