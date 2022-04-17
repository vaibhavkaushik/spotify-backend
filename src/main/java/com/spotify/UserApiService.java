package com.spotify;

import com.spotify.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserApiService implements UsersApiDelegate{


    @Override
    public ResponseEntity<User> createUser(User user) {

       User insertedUser = MockDB.insertUser(user);
       return new ResponseEntity<>(insertedUser, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<User> getUser(Long userid) {

        User fetchedUser = MockDB.fetchUser(userid);
        return new ResponseEntity<>(fetchedUser, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<User>> getUsers() {

        List<User> fetchUsersList = MockDB.fetchUsers();
        return new ResponseEntity<>(fetchUsersList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> removeUser(Long userid) {

        MockDB.deleteUser(userid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<User> updateUser(Long userid, User user) {

        User updatedUser = MockDB.updateUser(userid,user);
        return new ResponseEntity<>(updatedUser,HttpStatus.OK);
    }
}
