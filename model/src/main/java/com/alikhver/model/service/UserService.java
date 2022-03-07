package com.alikhver.model.service;

import com.alikhver.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
    Optional<User> getUser(Long id);

    Page<User> getUsers(Pageable pageable);

    boolean userExistsById(Long id);

    boolean userExistsByLogin(String id);

    User createUser(User user);

    void deleteUser(Long id);

    void updateUser(User user);
}
