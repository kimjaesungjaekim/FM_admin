package com.developer.fillme.repository;

import com.developer.fillme.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface IUserRepo extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);

    boolean existsUserEntityByUsername(String username);

    boolean existsUserEntityByPhone(String email);
    UserEntity findUserEntityByNicknameContainingIgnoreCaseAndBirthdayAndPhone(String nickName, LocalDate birthday, String phone);
    UserEntity findUserEntityByEmailAndNicknameContainingIgnoreCaseAndBirthdayAndPhone(String email, String nickName, LocalDate birthday, String phone);
}
