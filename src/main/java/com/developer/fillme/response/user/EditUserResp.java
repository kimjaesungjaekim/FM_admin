package com.developer.fillme.response.user;

import com.developer.fillme.constant.EGender;
import com.developer.fillme.entity.UserEntity;
import com.developer.fillme.entity.UserInfoEntity;
import com.developer.fillme.utils.DateUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EditUserResp {
	private Long id;
	private String nickname;
	private String email;
	private String phone;
	private String birthday;
	private EGender gender;
	private String avatar;
	private String name;
	private String referralCode;
	private String address;
	private Float latitude;
	private Float longitude;
	private Boolean firstLogin;
	private Integer height;
	private Integer weight;
	private Integer point;
	private Integer coupon;
	private String healthInfo;

	public EditUserResp(UserEntity userEntity, UserInfoEntity userInfoEntity) {
		this.id = userEntity.getId();
		this.nickname = userEntity.getNickname();
		this.email = userEntity.getEmail();
		this.phone = userEntity.getPhone();
		this.birthday = DateUtils.localDateToString(userEntity.getBirthday(), DateUtils.DatePattern.YYYY_MM_DD);
		this.gender = userEntity.getGender();
		this.avatar = userEntity.getAvatar();
		this.name = userEntity.getName();
		this.referralCode = userEntity.getReferralCode();
		this.address = userEntity.getAddress();
		this.latitude = userEntity.getLatitude();
		this.longitude = userEntity.getLongitude();
		this.firstLogin = userEntity.getFirstLogin();

		if(userInfoEntity != null) {
			this.height = userInfoEntity.getHeight();
			this.weight = userInfoEntity.getWeight();
			this.point = userInfoEntity.getPoint();
			this.coupon = userInfoEntity.getCoupon();
			this.healthInfo = userInfoEntity.getHealthInfo();
		}

	}
}
