package com.developer.fillme.request.user;

import static com.developer.fillme.utils.RegexUtils.REGEX_DATE_YYYY_MM_DD;

import com.developer.fillme.constant.EGender;
import com.developer.fillme.entity.UserEntity;
import com.developer.fillme.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditUserReq {
	@Schema(description = "name")
	private String name;

	@Schema(description = "nickname")
	private String nickname;

	@Schema(description = "phone")
	private String phone;

	@Schema(description = "gender")
	private EGender gender;

	@Schema(description = "birthday", example = "1999-01-01")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Pattern(regexp = REGEX_DATE_YYYY_MM_DD, message = "birthday is not valid format yyyy-MM-dd")
	private String birthday;

	@Schema(description = "avatar")
	private String avatar;

	@Schema(description = "address")
	private String address;

	@Schema(description = "latitude")
	private Float latitude;

	@Schema(description = "longitude")
	private Float longitude;

	@Schema(description = "firstLogin")
	@NotNull(message = "firstLogin is required")
	private Boolean firstLogin;

	@Schema(description = "height user")
	private Integer height;

	@Schema(description = "weight user")
	private Integer weight;

	@Schema(description = "Health Info")
	private String healthInfo;

	public void biddingData(UserEntity userEntity) {
		if (firstLogin) {
			userEntity.setFirstLogin(false);
		}
		userEntity.setName(name);
		userEntity.setNickname(nickname);
		userEntity.setPhone(phone);
		userEntity.setGender(gender);
		userEntity.setBirthday(DateUtils.toLocalDate(birthday, DateUtils.DatePattern.YYYY_MM_DD));
		userEntity.setAvatar(avatar);
		userEntity.setAddress(address);
		userEntity.setLatitude(latitude);
		userEntity.setLongitude(longitude);
	}
}
