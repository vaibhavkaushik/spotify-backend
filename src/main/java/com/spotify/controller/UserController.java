package com.spotify.controller;

import com.spotify.UsersApi;
import com.spotify.model.User;
import com.spotify.service.user.UserApiServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
@Validated
@RestController
public class UserController implements UsersApi{

    @Autowired
    UserApiServiceImpl userApiServiceImpl;

    @Override
    public ResponseEntity<Object> createUser(@Valid User user) {
        return userApiServiceImpl.createUser(user);
    }

    @Override
    public ResponseEntity<Object> getUser(Long userid) {
        return userApiServiceImpl.getUser(userid);
    }

    @Override
    public ResponseEntity<Object> getUsers() {
        return userApiServiceImpl.getUsers();
    }

    @Override
    public ResponseEntity<Object> removeUser(Long userid) {
        return userApiServiceImpl.removeUser(userid);
    }

    @Override
    public ResponseEntity<Object> updateUser(Long userid, User user) { return userApiServiceImpl.updateUser(userid, user); }

}
