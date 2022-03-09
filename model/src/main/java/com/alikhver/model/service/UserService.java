package com.alikhver.model.service;

import com.alikhver.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
    Optional<User> get(Long userId);

    Page<User> getUsers(Pageable pageable);

    boolean userExistsById(Long userId);

    boolean existsByLogin(String login);

    User save(User user);

    void delete(Long userId);
}
