package com.XAUS.Repositories.Auth;

import com.XAUS.Models.Auth.PasswordResetToken;
import com.XAUS.Models.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PasswordResetRepository extends JpaRepository<PasswordResetToken, Long> {

    @Query(value = "SELECT * FROM password_tokens WHERE token = :token", nativeQuery = true)
    public PasswordResetToken findByToken (@Param("token") String token);
}
