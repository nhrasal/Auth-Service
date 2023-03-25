package com.app.auth.users.repositories;

import com.app.auth.base.BaseRepository;
import com.app.auth.users.entitites.User;
import com.app.auth.users.entitites.Verification;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VerificationRepository extends BaseRepository<Verification> {
    Optional<Verification> findByUserAndIsVerified(User user, Boolean isVerified);
}