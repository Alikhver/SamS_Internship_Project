package com.alikhver.model.service;

import com.alikhver.model.entity.User;

import java.util.List;

public interface UserService {
    User getUser(Long id);

    List<User> getAllUsers();

    boolean userExistsById(Long id);

    boolean userExistsByLogin(String id);

    User createUser(User user);

    void deleteUser(Long id);

    void updateUser(Long id, User user);
}
