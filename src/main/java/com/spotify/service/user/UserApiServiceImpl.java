package com.spotify.service.user;

import com.spotify.entities.UserEntity;
import com.spotify.model.User;
import com.spotify.repository.UserRepository;
import com.spotify.utilities.ApiErrorResponse;
import com.spotify.utilities.ApiSuccessResponse;
import com.spotify.utilities.Constants;
import com.spotify.utilities.ObjectMapperUtils;
import com.spotify.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserApiServiceImpl implements UserApiService {

    UserValidation userValidation;

    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseEntity<Object> createUser(User user) {
        userValidation = new UserValidation(true, "");
        if(userValidation.isValid(user)) {
            try {
                //Set Passwd, can this be saved automatically ?
                user.setPassword(new BCryptPasswordEncoder(10).encode(user.getPassword()));
                UserEntity userEntity = ObjectMapperUtils.map(user, UserEntity.class);
                UserEntity savedUserEntity = userRepository.save(userEntity);
                User userCreated = ObjectMapperUtils.map(savedUserEntity, User.class);

                return new ResponseEntity<>(savedUserEntity, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else {
            return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, userValidation.getErrorMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Object> getUser(Long userid) {
        try {
            Optional<UserEntity> fetchedUserEntity = userRepository.findById(userid);
            if(fetchedUserEntity.isPresent()) {
                User fetchedUser = ObjectMapperUtils.map(fetchedUserEntity.get(), User.class);
                return new ResponseEntity<>(fetchedUserEntity, HttpStatus.OK);
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

            return new ResponseEntity<>(fetchedUserEntityList, HttpStatus.OK);
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
                return new ResponseEntity<>(new ApiSuccessResponse(HttpStatus.OK.value(), HttpStatus.OK, Constants.USER_DELETE_SUCCESS), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,Constants.USER_NOT_FOUND), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR,Constants.USER_DELETE_FAIL + "Error : " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> updateUser(Long userid, User user) {
        userValidation = new UserValidation(true, "");
        if(userValidation.isValid(user)) {
            try {
                Optional<UserEntity> fetchedUserEntity = userRepository.findById(userid);
                if (fetchedUserEntity.isPresent()) {
                    user.setId(userid);
                    UserEntity updatedUser = ObjectMapperUtils.map(user, UserEntity.class);
                    userRepository.save(updatedUser);
                    return new ResponseEntity<>(user, HttpStatus.OK);
                }
                return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, Constants.USER_NOT_FOUND), HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                System.out.println("In catch");
                return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, Constants.USER_UPDATE_FAIL + "Error : " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else {
            return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, userValidation.getErrorMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}