package com.alikhver.model.service;

import com.alikhver.model.entity.User;

import java.util.List;

public interface UserService {
    User getUser(String id);

    List<User> getAllUsers();

    boolean userExistsById(String id);

    boolean userExistsByLogin(String id);

    User createUser(User user);

    void deleteUser(String id);
}
