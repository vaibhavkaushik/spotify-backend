package com.spotify.repository;

import com.spotify.entities.SongEntity;
import org.springframework.data.repository.CrudRepository;

public interface SongRepository extends CrudRepository<SongEntity, Long> {



}
