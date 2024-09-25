package com.developer.fillme.service.impl;

import static com.developer.fillme.constant.EException.INVALID_REFRESH_TOKEN;
import static com.developer.fillme.constant.EException.INVALID_USER;
import static com.developer.fillme.constant.EException.PHONE_NUMBER_EXISTS;
import static com.developer.fillme.constant.EException.USER_ALREADY_EXISTS;
import static com.developer.fillme.constant.EException.USER_NOT_EXIST;
import static com.developer.fillme.constant.EException.USER_NOT_FOUND;
import static com.developer.fillme.constant.EException.WRONG_PASSWORD;
import static com.developer.fillme.constant.EFromType.KAKAO;
import static com.developer.fillme.constant.EFromType.MANUAL;
import static com.developer.fillme.constant.EFromType.NAVER;
import static com.developer.fillme.constant.EGender.fromString;
import static com.developer.fillme.constant.ERegisteredType.FACEBOOK;
import static com.developer.fillme.constant.EStatusUser.ACTIVE;
import static com.developer.fillme.constant.EStatusUser.INACTIVE;

import java.time.LocalDateTime;
import java.util.Objects;

import com.developer.fillme.client.ISSOClient;
import com.developer.fillme.client.response.KakaoUserRepData;
import com.developer.fillme.client.response.NaverUserRepData;
import com.developer.fillme.request.auth.SendOtpFindIdReq;
import com.developer.fillme.request.auth.SendOtpFindPwReq;
import com.developer.fillme.request.auth.VerifyOtpFindIdReq;
import com.developer.fillme.request.auth.VerifyOtpFindPwReq;
import com.developer.fillme.response.auth.VerifyOtpFindIdResp;
import com.developer.fillme.client.response.FBUserRepData;
import com.developer.fillme.constant.EStatusUser;
import com.developer.fillme.request.auth.LoginSSOReq;
import com.developer.fillme.response.auth.VerifyOtpFindPwResp;
import com.developer.fillme.utils.DateUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.developer.fillme.config.jwt.JwtUtil;
import com.developer.fillme.constant.EUserType;
import com.developer.fillme.entity.TokenEntity;
import com.developer.fillme.entity.UserEntity;
import com.developer.fillme.entity.UserInfoEntity;
import com.developer.fillme.exception.BaseException;
import com.developer.fillme.model.GenerateTokenInfo;
import com.developer.fillme.repository.ITokenRepo;
import com.developer.fillme.repository.IUserInfoRepo;
import com.developer.fillme.repository.IUserRepo;
import com.developer.fillme.request.auth.LoginReq;
import com.developer.fillme.request.auth.RefreshTokenReq;
import com.developer.fillme.request.auth.SignupReq;
import com.developer.fillme.response.auth.LoginResp;
import com.developer.fillme.response.auth.RefreshTokenResp;
import com.developer.fillme.response.auth.SignupResp;
import com.developer.fillme.response.user.EditUserResp;
import com.developer.fillme.service.IAuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IAuthServiceImpl implements IAuthService {
    private final JwtUtil jwtUtil;
    private final IUserRepo userRepo;
    private final ITokenRepo tokenRepo;
    private final ModelMapper modelMapper;
    private final ISSOClient ssoClient;
    private final PasswordEncoder passwordEncoder;
    private final IUserInfoRepo userInfoRepo;

    @Value("${spring.security.refresh-token-time}")
    private long refreshExpiryTime;

    @Override
    @Transactional
    public SignupResp register(SignupReq request) {
        if (userRepo.existsUserEntityByUsername(request.getUsername())) {
            throw new BaseException(USER_ALREADY_EXISTS);
        }

        if (userRepo.existsUserEntityByPhone(request.getPhone())) {
            throw new BaseException(PHONE_NUMBER_EXISTS);
        }

        UserEntity userEntity = modelMapper.map(request, UserEntity.class);
        userEntity.setType(EUserType.ROLE_USER);
        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        userEntity.setRegisteredFrom(MANUAL.name());
        userEntity.setFirstLogin(true);
        userEntity.setCreatedAt(LocalDateTime.now());
        userEntity.setStatus(ACTIVE.name());
        userRepo.save(userEntity);

        return new SignupResp("Register Success");
    }

    @Override
    @Transactional
    public LoginResp login(LoginReq loginReq) {
        UserEntity user = userRepo.findByUsername(loginReq.getUsername());
        if (Objects.isNull(user)) {
            throw new BaseException(INVALID_USER);
        }

        if (user.getStatus().equals(INACTIVE.name())) {
            throw new BaseException(USER_NOT_FOUND);
        }

        if (!passwordEncoder.matches(loginReq.getPassword(), user.getPassword())) {
            throw new BaseException(WRONG_PASSWORD);
        }

        // Generate token info
        GenerateTokenInfo generateTokenInfo = GenerateTokenInfo.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .build();

        // Gen access token and refresh token
        String accessToken = jwtUtil.generateToken(generateTokenInfo);
        String refreshToken = jwtUtil.refreshToken(loginReq.getUsername());

        // Save token
        saveToken(accessToken, refreshToken, user);
        UserInfoEntity userInfo = userInfoRepo.findByUser(user).orElse(null);

        return LoginResp.builder()
                .tokenType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .data(new EditUserResp(user, userInfo))
                .build();
    }

    @Override
    @Transactional
    public LoginResp loginWithFacebook(LoginSSOReq req) {
        LoginResp userResponse;
        FBUserRepData userInfo = ssoClient.getFBUserInfo(req.getAccessToken());
        UserEntity userFaceBook = userRepo.findByUsername(userInfo.getEmail());
        if (userFaceBook != null) {
            userResponse = updateExistingUser(userFaceBook);
        } else {
            userResponse = createNewFacebookUser(userInfo);
        }
        return userResponse;
    }

    @Override
    @Transactional
    public LoginResp loginWithKakao(LoginSSOReq req) {
        LoginResp userResponse;
        KakaoUserRepData userInfo = ssoClient.getKakaoUserInfo(req.getAccessToken());
        UserEntity userKakao = userRepo.findByUsername(userInfo.getId() + "");
        if (userKakao != null) {
            userResponse = updateExistingUserKakao(userKakao);
        } else {
            userResponse = createNewKakaoUser(userInfo);
        }
        return userResponse;
    }

    @Override
    @Transactional
    public LoginResp loginWithNaver(LoginSSOReq req) {
        LoginResp userResponse;
        NaverUserRepData userInfo = ssoClient.getNaverUserInfo(req.getAccessToken());
        UserEntity userNaver = userRepo.findByUsername(userInfo.getResponse().getId());
        if (userNaver != null) {
            userResponse = updateExistingUserNaver(userNaver);
        } else {
            userResponse = createNewNaverUser(userInfo);
        }
        return userResponse;
    }

    @Override
    @Transactional
    public RefreshTokenResp refreshToken(RefreshTokenReq request) {
        if (jwtUtil.isReFreshTokenValid(request.getRefreshToken())) {
            TokenEntity tokenInfo = tokenRepo.findByRefreshToken(request.getRefreshToken());
            if (tokenInfo == null) {
                throw new BaseException(INVALID_REFRESH_TOKEN);
            }

            GenerateTokenInfo generateTokenInfo = GenerateTokenInfo.builder()
                    .username(tokenInfo.getUser().getUsername())
                    .email(tokenInfo.getUser().getEmail())
                    .build();

            // Gen new accessToken
            String accessToken = jwtUtil.generateToken(generateTokenInfo);
            tokenInfo.setAccessToken(accessToken);

            // Update accessToken
            tokenRepo.save(tokenInfo);
            return RefreshTokenResp.builder()
                    .accessToken(accessToken)
                    .refreshToken(tokenInfo.getRefreshToken())
                    .build();
        }
        return null;
    }

    private void saveToken(String accessToken, String refreshToken, UserEntity user) {
        long daysInMillis = 86400000L; // 1 day
        long totalDay = (refreshExpiryTime / daysInMillis);
        LocalDateTime expiryDate = LocalDateTime.now().plusDays(totalDay);
        TokenEntity token = TokenEntity.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiryDate(expiryDate)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();
        tokenRepo.save(token);
    }

    @Override
    @Transactional
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        response.setStatus(HttpStatus.OK.value());
        authentication.setAuthenticated(false);
        SecurityContextHolder.clearContext();
        String assetToken = jwtUtil.parseJwt(request);
        tokenRepo.deleteByAccessToken(assetToken);
    }

    @Override
    public UserEntity sendOtpFindId(SendOtpFindIdReq sendOtpReq) {
        UserEntity user = userRepo.findUserEntityByNicknameContainingIgnoreCaseAndBirthdayAndPhone(sendOtpReq.getNickname(), sendOtpReq.getBirthday(), sendOtpReq.getPhone());
        if (user == null) {
            throw new BaseException(USER_NOT_EXIST);
        }
        return user;
    }

    @Override
    public UserEntity sendOtpFindPw(SendOtpFindPwReq sendOtpReq) {
        UserEntity user = userRepo.findUserEntityByEmailAndNicknameContainingIgnoreCaseAndBirthdayAndPhone(sendOtpReq.getEmail(), sendOtpReq.getNickname(), sendOtpReq.getBirthday(),
                sendOtpReq.getPhone());
        if (user == null) {
            throw new BaseException(USER_NOT_EXIST);
        }
        return user;
    }

    @Override
    public VerifyOtpFindIdResp verifyOtpFindId(VerifyOtpFindIdReq verifyOtpReq) {
        UserEntity user = sendOtpFindId(verifyOtpReq);

        //todo

        return VerifyOtpFindIdResp.builder().email(maskEmail(user.getEmail())).build();
    }

    @Override
    public VerifyOtpFindPwResp verifyOtpFindPw(VerifyOtpFindPwReq verifyOtpReq) {
        UserEntity user = sendOtpFindPw(verifyOtpReq);

        //todo

        if (Objects.isNull(user)) {
            throw new BaseException(INVALID_USER);
        }

        if (user.getStatus().equals(INACTIVE.name())) {
            throw new BaseException(USER_NOT_FOUND);
        }

        // Generate token info
        GenerateTokenInfo generateTokenInfo = GenerateTokenInfo.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .build();

        // Gen access token and refresh token
        String accessToken = jwtUtil.generateToken(generateTokenInfo, 180);
        String refreshToken = jwtUtil.refreshToken(user.getUsername());

        // Save token
        saveToken(accessToken, refreshToken, user);

        return VerifyOtpFindPwResp.builder().token(accessToken).build();
    }

    public static String maskEmail(String email) {
            int atIndex = email.indexOf("@");
            if (atIndex > 3) {
                return email.substring(0, atIndex - 3) + "***" + email.substring(atIndex);
            }
            return email;
        }

    public LoginResp createNewFacebookUser(FBUserRepData auth2User) {
        UserEntity newUser = UserEntity.builder()
                .email(auth2User.getEmail())
                .username(auth2User.getEmail()) // Use email as username
                .name(auth2User.getName())
                .birthday(DateUtils.parseDate(auth2User.getBirthday()))
                .gender(fromString(auth2User.getGender()))
                .registeredFrom(FACEBOOK.name())
                .type(EUserType.ROLE_USER)
                .status(EStatusUser.ACTIVE.name())
                .createdAt(LocalDateTime.now())
                .firstLogin(true)
                .build();
        UserEntity user = userRepo.save(newUser);

        GenerateTokenInfo generateTokenInfo = GenerateTokenInfo.builder()
                .username(auth2User.getEmail())
                .email(auth2User.getEmail())
                .build();

        // Gen access token and refresh token
        String accessToken = jwtUtil.generateToken(generateTokenInfo);
        String refreshToken = jwtUtil.refreshToken(auth2User.getEmail());
        // Save token
        saveToken(accessToken, refreshToken, user);
        UserInfoEntity userInfo = userInfoRepo.findById(user.getId()).orElse(new UserInfoEntity());
        return LoginResp.builder()
                .tokenType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .data(new EditUserResp(user, userInfo))
                .build();
    }

    public LoginResp createNewKakaoUser(KakaoUserRepData repData) {
        String usernameKakao = String.valueOf(repData.getId());
        UserEntity newUser = UserEntity.builder()
                .email(null)
                .username(usernameKakao) // Kakao Id as username
                .name(null)
                .birthday(null)
                .gender(null)
                .registeredFrom(KAKAO.name())
                .type(EUserType.ROLE_USER)
                .status(EStatusUser.ACTIVE.name())
                .createdAt(LocalDateTime.now())
                .firstLogin(true)
                .build();
        UserEntity user = userRepo.save(newUser);

        GenerateTokenInfo generateTokenInfo = GenerateTokenInfo.builder()
                .username(usernameKakao)
                .email("")
                .build();

        // Gen access token and refresh token
        String accessToken = jwtUtil.generateToken(generateTokenInfo);
        String refreshToken = jwtUtil.refreshToken(usernameKakao);
        // Save token
        saveToken(accessToken, refreshToken, user);
        UserInfoEntity userInfo = userInfoRepo.findById(user.getId()).orElse(new UserInfoEntity());
        return LoginResp.builder()
                .tokenType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .data(new EditUserResp(user, userInfo))
                .build();
    }

    private LoginResp updateExistingUser(UserEntity user) {
        // Generate token info
        GenerateTokenInfo tokenInfo = GenerateTokenInfo.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .build();

        // Delete old token
        tokenRepo.deleteTokenByUsername(user.getUsername());

        // Generate new tokens
        String accessToken = jwtUtil.generateToken(tokenInfo);
        String refreshToken = jwtUtil.refreshToken(user.getUsername());

        // Save new tokens and respond
        saveToken(accessToken, refreshToken, user);
        UserInfoEntity userInfo = userInfoRepo.findByUser(user).orElse(null);
        return LoginResp.builder()
                .tokenType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .data(new EditUserResp(user, userInfo))
                .build();
    }

    private LoginResp updateExistingUserKakao(UserEntity user) {
        // Generate token info
        GenerateTokenInfo tokenInfo = GenerateTokenInfo.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .build();

        // Delete old token
        tokenRepo.deleteTokenByUsername(user.getUsername());

        // Generate new tokens
        String accessToken = jwtUtil.generateToken(tokenInfo);
        String refreshToken = jwtUtil.refreshToken(user.getUsername());

        // Save new tokens and respond
        saveToken(accessToken, refreshToken, user);
        UserInfoEntity userInfo = userInfoRepo.findById(user.getId())
                .orElse(new UserInfoEntity());

        return LoginResp.builder()
                .tokenType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .data(new EditUserResp(user, userInfo))
                .build();
    }

    public LoginResp createNewNaverUser(NaverUserRepData repData) {
        String usernameNaver = repData.getResponse().getId();
        UserEntity newUser = UserEntity.builder()
                .email(repData.getResponse().getEmail())
                .username(usernameNaver) //  Naver Id as username
                .name(repData.getResponse().getName())
                .birthday(null)
                .gender(null)
                .registeredFrom(NAVER.name())
                .type(EUserType.ROLE_USER)
                .status(EStatusUser.ACTIVE.name())
                .createdAt(LocalDateTime.now())
                .firstLogin(true)
                .build();
        UserEntity user = userRepo.save(newUser);

        GenerateTokenInfo generateTokenInfo = GenerateTokenInfo.builder()
                .username(usernameNaver)
                .email("")
                .build();

        // Gen access token and refresh token
        String accessToken = jwtUtil.generateToken(generateTokenInfo);
        String refreshToken = jwtUtil.refreshToken(usernameNaver);
        // Save token
        saveToken(accessToken, refreshToken, user);
        UserInfoEntity userInfo = userInfoRepo.findById(user.getId()).orElse(new UserInfoEntity());
        return LoginResp.builder()
                .tokenType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .data(new EditUserResp(user, userInfo))
                .build();
    }

    private LoginResp updateExistingUserNaver(UserEntity user) {
        // Generate token info
        GenerateTokenInfo tokenInfo = GenerateTokenInfo.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .build();

        // Delete old token
        tokenRepo.deleteTokenByUsername(user.getUsername());

        // Generate new tokens
        String accessToken = jwtUtil.generateToken(tokenInfo);
        String refreshToken = jwtUtil.refreshToken(user.getUsername());

        // Save new tokens and respond
        saveToken(accessToken, refreshToken, user);
        UserInfoEntity userInfo = userInfoRepo.findById(user.getId())
                .orElse(new UserInfoEntity());

        return LoginResp.builder()
                .tokenType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .data(new EditUserResp(user, userInfo))
                .build();
    }
}
