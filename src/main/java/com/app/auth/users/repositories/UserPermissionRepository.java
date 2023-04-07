package com.app.auth.users.repositories;

import com.app.auth.base.BaseRepository;
import com.app.auth.users.entitites.UserPermission;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPermissionRepository extends BaseRepository<UserPermission> {
}
