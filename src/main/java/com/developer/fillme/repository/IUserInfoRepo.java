package com.developer.fillme.repository;

import java.util.Optional;

import com.developer.fillme.entity.UserEntity;
import com.developer.fillme.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserInfoRepo extends JpaRepository<UserInfoEntity, Long> {
    Optional<UserInfoEntity> findByUser(UserEntity user);

    Optional<UserInfoEntity> findById(Long id);
}
