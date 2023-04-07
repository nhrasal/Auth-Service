package com.app.auth.users.repositories;

import com.app.auth.base.BaseRepository;
import com.app.auth.users.entitites.Permission;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends BaseRepository<Permission> {

}
