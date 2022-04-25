package com.spotify.service.role;

import com.spotify.entities.RoleEntity;
import com.spotify.model.Role;
import org.springframework.http.ResponseEntity;

public interface UserRoleService {

    public ResponseEntity<Object> createRole(Role role);
    public ResponseEntity<Object> getRole(Long roleid);
    public ResponseEntity<Object> getRoles();
    public ResponseEntity<Object> removeRole(Long roleid);
    public ResponseEntity<Object> updateRole(Long roleid, Role role);
}
