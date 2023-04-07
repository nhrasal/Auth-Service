package com.app.auth.users.repositories;

import com.app.auth.base.BaseRepository;
import com.app.auth.users.entitites.RolePermission;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionRepository extends BaseRepository<RolePermission> {
}
