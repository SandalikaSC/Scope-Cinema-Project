package com.cinema.authservice.repository;

import com.cinema.authservice.entity.ForgetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ForgetPwTokenRepository extends JpaRepository<ForgetPasswordToken,Long> {
    Optional<ForgetPasswordToken> findByToken(String token);
    Optional<ForgetPasswordToken> findTopByTokenOrderByExpiryTimeDesc(String token);
}
