package com.spotify.service;

import com.spotify.UsersApiDelegate;
import com.spotify.entities.UserEntity;
import com.spotify.model.User;
import com.spotify.repository.UserRepository;
import com.spotify.utilities.ApiErrorResponse;
import com.spotify.utilities.Constants;
import com.spotify.utilities.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class  UserApiService implements UsersApiDelegate {

    @Override
    public ResponseEntity<Object> createUser(User user) {
        try {
            UserEntity userEntity = ObjectMapperUtils.map(user, UserEntity.class);
            UserEntity savedUserEntity = userRepository.save(userEntity);
            User userCreated = ObjectMapperUtils.map(savedUserEntity, User.class);

            return new ResponseEntity<>(userCreated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> getUser(Long userid) {
        try {
            Optional<UserEntity> fetchedUserEntity = userRepository.findById(userid);
            if(fetchedUserEntity.isPresent()) {
                User fetchedUser = ObjectMapperUtils.map(fetchedUserEntity.get(), User.class);
                return new ResponseEntity<>(fetchedUser, HttpStatus.OK);
            }
            return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,Constants.USER_NOT_FOUND), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> getUsers() {
        try {
            List<UserEntity> fetchedUserEntityList = userRepository.findAll();
            List<User> fetchedUserList = ObjectMapperUtils.mapAll(fetchedUserEntityList, User.class);

            return new ResponseEntity<>(fetchedUserList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> removeUser(Long userid) {
        try {
            Optional<UserEntity> fetchedUserEntity = userRepository.findById(userid);
            if(fetchedUserEntity.isPresent()) {
                userRepository.deleteById(userid);
                return new ResponseEntity<>(Constants.USER_DELETE_SUCCESS, HttpStatus.OK);
            }
            return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,Constants.USER_NOT_FOUND), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR,Constants.USER_DELETE_FAIL + "Error : " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> updateUser(Long userid, User user) {

        try {
            Optional<UserEntity> fetchedUserEntity = userRepository.findById(userid);
            if(fetchedUserEntity.isPresent()) {
                user.setId(userid);
                UserEntity updatedUser = ObjectMapperUtils.map(user, UserEntity.class);
                userRepository.save(updatedUser);
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
            return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,Constants.USER_NOT_FOUND), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR,Constants.USER_UPDATE_FAIL + "Error : " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Autowired
    UserRepository userRepository;


    //
//    @Override
//    public ResponseEntity<User> updateUser(Long userid, User user) {
//
//    }
}