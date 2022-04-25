package com.spotify.repository;

import com.spotify.entities.RoleEntity;
import com.spotify.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleRepository extends CrudRepository<RoleEntity, Long> {

    List<RoleEntity> findAll();
}
