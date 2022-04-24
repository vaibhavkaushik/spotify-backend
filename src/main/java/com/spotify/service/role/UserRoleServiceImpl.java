package com.spotify.service.role;

import com.spotify.entities.RoleEntity;
import com.spotify.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRoleServiceImpl implements UserRoleService{

    @Autowired
    RoleRepository roleRepository;

    @Override
    public RoleEntity addRole(RoleEntity role) {
        roleRepository.save(role);
        return role;
    }

    @Override
    public RoleEntity getRole(Long id) {
        Optional<RoleEntity> roleEntity = roleRepository.findById(id);
        return roleEntity.get();
    }

    @Override
    public RoleEntity updateRole(RoleEntity role, Long id) {
        Optional<RoleEntity> roleEntity = roleRepository.findById(id);
        if(roleEntity.isPresent()) {
            role.setId(id);
            roleRepository.save(role);
        }

        return role;
    }

    @Override
    public void deleteRole(Long id) {

        Optional<RoleEntity> roleEntity = roleRepository.findById(id);
        if(roleEntity.isPresent()) {
            roleRepository.delete(roleEntity.get());
        }
    }
}
