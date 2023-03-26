package com.app.auth.users.repositories;

import com.app.auth.base.BaseRepository;
import com.app.auth.users.entitites.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;



@Repository
public interface UserRepository extends BaseRepository<User>{
    Optional<User> findByPhone(String phone);

    Optional<User> findByEmail(String email);
    Optional<User> findFirstByPasswordResetToken(String passwordResetToken);

    Optional<User> findByPhoneOrEmail(String phone, String email);

    // List<User> findByStatus(String status);

    Boolean existsByPhone(String phone);

    Boolean existsByEmail(String email);
}
