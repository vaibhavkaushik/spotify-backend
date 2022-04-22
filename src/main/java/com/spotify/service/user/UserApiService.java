package com.spotify.service.user;

import com.spotify.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserApiService {
    public ResponseEntity<Object> createUser(User user);

    public ResponseEntity<Object> getUser(Long userid);

    public ResponseEntity<Object> getUsers();
    public ResponseEntity<Object> removeUser(Long userid);
    public ResponseEntity<Object> updateUser(Long userid, User user);
}
