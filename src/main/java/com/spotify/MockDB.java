package com.spotify;

import com.spotify.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.logging.Logger;
import java.util.stream.Collectors;

public class  MockDB {

    private static final Logger logger = Logger.getLogger(String.valueOf(MockDB.class));

    public static Map<Long,User> DB = new HashMap<>();

    public static int userCount = 0;

    public static Map<Long,User> getDB() {
        return DB;
    }

    public static User insertUser(User user) {

        userCount+=1;
        int id = userCount;
        logger.info("Inserted data with "+id);
        DB.put((long)id,user);

        return user;
    }

    public static User fetchUser(Long id) {

        User user = DB.get(id.longValue());

        return user;
    }

    public static List<User> fetchUsers() {

        List<User> users =  DB.values().stream().collect(Collectors.toList());
        return users;
    }

    //TODO change specific field
    public static User updateUser(Long id,User user) {

        DB.put(id.longValue(),user);
        return user;
    }

    public static void deleteUser(Long id) {
        DB.remove(id.longValue());
    }

}
