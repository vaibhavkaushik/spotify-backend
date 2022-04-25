package com.spotify.service.role;

import com.spotify.entities.RoleEntity;
import com.spotify.entities.RoleEntity;
import com.spotify.model.Role;
import com.spotify.model.Role;
import com.spotify.repository.RoleRepository;
import com.spotify.repository.RoleRepository;
import com.spotify.utilities.ApiErrorResponse;
import com.spotify.utilities.ApiSuccessResponse;
import com.spotify.utilities.Constants;
import com.spotify.utilities.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserRoleServiceImpl implements UserRoleService{

    @Autowired
    RoleRepository roleRepository;

    @Override
    public ResponseEntity<Object> createRole(Role role) {

            try {
                RoleEntity roleEntity = ObjectMapperUtils.map(role, RoleEntity.class);
                RoleEntity savedRoleEntity = roleRepository.save(roleEntity);
                Role roleCreated = ObjectMapperUtils.map(savedRoleEntity, Role.class);

                return new ResponseEntity<>(roleCreated, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
            }

    }

    @Override
    public ResponseEntity<Object> getRole(Long roleid) {
        try {
            Optional<RoleEntity> fetchedRoleEntity = roleRepository.findById(roleid);
            if(fetchedRoleEntity.isPresent()) {
                Role fetchedRole = ObjectMapperUtils.map(fetchedRoleEntity.get(), Role.class);
                return new ResponseEntity<>(fetchedRole, HttpStatus.OK);
            }
            return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, Constants.USER_NOT_FOUND), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> getRoles() {
        try {
            List<RoleEntity> fetchedRoleEntityList = roleRepository.findAll();
            List<Role> fetchedRoleList = ObjectMapperUtils.mapAll(fetchedRoleEntityList, Role.class);

            return new ResponseEntity<>(fetchedRoleList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> removeRole(Long roleid) {
        try {
            Optional<RoleEntity> fetchedRoleEntity = roleRepository.findById(roleid);
            if(fetchedRoleEntity.isPresent()) {
                roleRepository.deleteById(roleid);
                return new ResponseEntity<>(new ApiSuccessResponse(HttpStatus.OK.value(), HttpStatus.OK, Constants.USER_DELETE_SUCCESS), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,Constants.USER_NOT_FOUND), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR,Constants.USER_DELETE_FAIL + "Error : " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> updateRole(Long roleid, Role role) {

            try {
                Optional<RoleEntity> fetchedRoleEntity = roleRepository.findById(roleid);
                if (fetchedRoleEntity.isPresent()) {
                    role.setId(roleid);
                    RoleEntity updatedRole = ObjectMapperUtils.map(role, RoleEntity.class);
                    roleRepository.save(updatedRole);
                    return new ResponseEntity<>(role, HttpStatus.OK);
                }
                return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, Constants.USER_NOT_FOUND), HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                System.out.println("In catch");
                return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, Constants.USER_UPDATE_FAIL + "Error : " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }
    
}
