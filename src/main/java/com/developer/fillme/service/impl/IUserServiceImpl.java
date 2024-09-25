package com.developer.fillme.service.impl;

import static com.developer.fillme.constant.EException.INVALID_ID;
import static com.developer.fillme.constant.EException.PW_NOT_MATCH;
import static com.developer.fillme.constant.EException.USER_NOT_FOUND;
import static com.developer.fillme.constant.EStatusUser.INACTIVE;

import java.util.Objects;
import java.util.Optional;

import com.developer.fillme.response.auth.SignupResp;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.developer.fillme.config.jwt.JwtUtil;
import com.developer.fillme.entity.UserEntity;
import com.developer.fillme.entity.UserInfoEntity;
import com.developer.fillme.exception.BaseException;
import com.developer.fillme.repository.IUserInfoRepo;
import com.developer.fillme.repository.IUserRepo;
import com.developer.fillme.request.user.ChangePasswordReq;
import com.developer.fillme.request.user.EditUserReq;
import com.developer.fillme.response.user.EditUserResp;
import com.developer.fillme.service.IUserService;

import lombok.RequiredArgsConstructor;

import javax.security.sasl.AuthenticationException;

@Service
@RequiredArgsConstructor
public class IUserServiceImpl implements IUserService {
    private final IUserInfoRepo userInfoRepo;
    private final IUserRepo userRepo;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    @Override
    @Transactional
    public EditUserResp updateUser(EditUserReq editUserReq) {
        UserEntity userEntity = getCurrentUser();
        editUserReq.biddingData(userEntity);
        userRepo.save(userEntity);
        Optional<UserInfoEntity> userInfoEntityOptional = userInfoRepo.findByUser(userEntity);
        UserInfoEntity userInfoEntity;
        if (userInfoEntityOptional.isPresent()) {
            userInfoEntity = userInfoEntityOptional.get();

        } else {
            userInfoEntity = new UserInfoEntity();
            userInfoEntity.setUser(userEntity);
        }
        userInfoEntity.setWeight(editUserReq.getWeight());
        userInfoEntity.setHeight(editUserReq.getHeight());
        userInfoEntity.setHealthInfo(editUserReq.getHealthInfo());
        userInfoRepo.save(userInfoEntity);
        return new EditUserResp(userEntity, userInfoEntity);
    }

    @Override
    public EditUserResp getUserInfo() {
        UserEntity userEntity = getCurrentUser();
        UserInfoEntity userInfoEntity = userInfoRepo.findByUser(userEntity).orElse(null);
        return new EditUserResp(userEntity, userInfoEntity);
    }

    @Override
    public void changePassword(ChangePasswordReq req) {
        UserEntity userEntity = getCurrentUser();
        if (!passwordEncoder.matches(req.getOldPassword(), userEntity.getPassword())) {
            throw new BaseException(PW_NOT_MATCH);
        }
        userEntity.setPassword(passwordEncoder.encode(req.getNewPassword()));
        userRepo.save(userEntity);
    }

    private UserEntity getCurrentUser() {
        String userName = jwtUtil.usernameByContext();
        if (Objects.isNull(userName)) {
            throw new BaseException(INVALID_ID);
        }
        UserEntity userEntity = userRepo.findByUsername(userName);
        if (Objects.isNull(userEntity)) {
            throw new BaseException(USER_NOT_FOUND);
        }
        return userEntity;
    }

    @Override
    public void deleteAccount() {
        String userName = jwtUtil.usernameByContext();
        if (Objects.isNull(userName)) {
            throw new BaseException(INVALID_ID);
        }
        UserEntity userEntity = userRepo.findByUsername(userName);
        if (Objects.isNull(userEntity)) {
            throw new BaseException(USER_NOT_FOUND);
        }
        userEntity.setStatus(INACTIVE.name());
        userRepo.save(userEntity);
    }
}
