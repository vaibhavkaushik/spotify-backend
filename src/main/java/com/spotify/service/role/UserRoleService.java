package com.spotify.service.role;

import com.spotify.entities.RoleEntity;

public interface UserRoleService {

    RoleEntity addRole(RoleEntity role);
    RoleEntity getRole(Long id);
    RoleEntity updateRole(RoleEntity role, Long id);
    void deleteRole(Long id);
}
