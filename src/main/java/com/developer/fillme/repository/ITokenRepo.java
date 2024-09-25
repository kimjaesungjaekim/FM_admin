package com.developer.fillme.repository;

import com.developer.fillme.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ITokenRepo extends JpaRepository<TokenEntity, Long> {

    @Modifying
    @Query("DELETE FROM TokenEntity t WHERE t.user.username = :username")
    void deleteTokenByUsername(@Param("username") String username);

    TokenEntity findByRefreshToken(String refreshToken);

    @Modifying
    @Query("DELETE FROM TokenEntity t WHERE t.accessToken = :accessToken")
    void deleteByAccessToken(@Param("accessToken") String accessToken);

    TokenEntity findByAccessToken(String accessToken);
}
