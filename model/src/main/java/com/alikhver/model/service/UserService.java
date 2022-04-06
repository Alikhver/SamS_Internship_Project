package com.alikhver.model.service;

import com.alikhver.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getUser(Long userId);

    Page<User> getUsers(Pageable pageable);

    List<User> getUsers();

    boolean existsUserById(Long userId);

    boolean existsUserByLogin(String login);

    User saveUser(User user);

    void deleteUser(Long userId);

    void deleteAll();
}
