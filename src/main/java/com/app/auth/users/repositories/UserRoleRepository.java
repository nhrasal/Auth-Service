package com.app.auth.users.repositories;

import com.app.auth.base.BaseRepository;
import com.app.auth.users.entitites.User;
import com.app.auth.users.entitites.UserRole;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends BaseRepository<UserRole> {

}
