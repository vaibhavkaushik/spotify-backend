package com.spotify.controller;

import com.spotify.entities.RoleEntity;
import com.spotify.service.role.UserRoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    UserRoleServiceImpl userRoleService;

    @PostMapping("/")
    public RoleEntity addRole(@RequestBody RoleEntity roleEntity) {
        return userRoleService.addRole(roleEntity);
    }

}
