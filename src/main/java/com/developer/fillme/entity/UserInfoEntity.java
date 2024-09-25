package com.developer.fillme.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_info")
public class UserInfoEntity {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "height")
	private Integer height;

	@Column(name = "weight")
	private Integer weight;

	@Column(name = "point")
	private Integer point;

	@Column(name = "coupon")
	private Integer coupon;

	@Column(name = "health_info", columnDefinition = "json")
	private String healthInfo;

	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private UserEntity user;
}
