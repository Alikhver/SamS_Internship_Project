package com.alikhver.model.service;

import com.alikhver.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getUser(Long id);

    List<User> getAllUsers();

    boolean userExistsById(Long id);

    boolean userExistsByLogin(String id);

    User createUser(User user);

    void deleteUser(Long id);

    void updateUser(User user);
}
