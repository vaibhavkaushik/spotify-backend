package com.spotify.service;

import com.spotify.UsersApiDelegate;
import com.spotify.entities.UserEntity;
import com.spotify.model.User;
import com.spotify.repository.UserRepository;
import com.spotify.utilities.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserApiService implements UsersApiDelegate {


    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseEntity<User> createUser(User user) {


        UserEntity userEntity = ObjectMapperUtils.map(user,UserEntity.class);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        User userCreated = ObjectMapperUtils.map(savedUserEntity,User.class);

        return new ResponseEntity<>(userCreated, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<User> getUser(Long userid) {

        Optional<UserEntity> fetchedUserEntity = userRepository.findById(userid);
        if(fetchedUserEntity.isPresent()) {
            User fetchedUser = ObjectMapperUtils.map(fetchedUserEntity.get(), User.class);
            return new ResponseEntity<>(fetchedUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<List<User>> getUsers() {

       List<UserEntity> fetchedUserEntityList = userRepository.findAll();
       List<User> fetchedUserList = ObjectMapperUtils.mapAll(fetchedUserEntityList,User.class);

       return new ResponseEntity<>(fetchedUserList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> removeUser(Long userid) {

        userRepository.deleteById(userid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<User> updateUser(Long userid, User user) {

        user.setId(userid);
        UserEntity updatedUser = ObjectMapperUtils.map(user,UserEntity.class);
        userRepository.save(updatedUser);

        return new ResponseEntity<>(user,HttpStatus.OK);
    }
}
