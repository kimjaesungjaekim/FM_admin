package com.developer.fillme.entity;

import com.developer.fillme.constant.EGender;
import com.developer.fillme.constant.EUserType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class UserEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", length = 100, nullable = false, unique = true)
    private String username;

    @Column(name = "password", length = 300)
    private String password;

    @Column(name = "nickname", length = 100)
    private String nickname;

    @Column(name = "full_name", length = 100)
    private String name;

    @Column(name = "email", length = 100, unique = true)
    private String email;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "gender", length = 10)
    @Enumerated(EnumType.STRING)
    private EGender gender;

    @Column(name = "avatar", length = 50)
    private String avatar;

    @Column(name = "type", length = 10)
    @Enumerated(EnumType.STRING)
    private EUserType type;

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "address_detail", length = 100)
    private String addressDetail;

    @Column(name = "latitude")
    private Float latitude;

    @Column(name = "longitude")
    private Float longitude;

    @Column(name = "referral_code", length = 10)
    private String referralCode;

    @Column(name = "status", length = 10)
    private String status;

    @Column(name = "registered_from", length = 15, nullable = false)
    private String registeredFrom;

    @Column(name = "first_login")
    private Boolean firstLogin;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user")
    private List<TokenEntity> tokens;

}
