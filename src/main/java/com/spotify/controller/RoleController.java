package com.spotify.controller;

import com.spotify.RolesApi;
import com.spotify.entities.RoleEntity;
import com.spotify.model.Role;
import com.spotify.service.role.UserRoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController implements RolesApi {

    @Autowired
    UserRoleServiceImpl userRoleService;

    @Override
    public ResponseEntity<Object> createRole(Role role) {
        return userRoleService.createRole(role);
    }

    @Override
    public ResponseEntity<Object> getRole(Long roleid) {
        return userRoleService.getRole(roleid);
    }

    @Override
    public ResponseEntity<Object> getRoles() {
        return userRoleService.getRoles();
    }

    @Override
    public ResponseEntity<Object> removeRole(Long roleid) {
        return userRoleService.removeRole(roleid);
    }

    @Override
    public ResponseEntity<Object> updateRole(Long roleid, Role role) {
        return userRoleService.updateRole(roleid,role);
    }
}
