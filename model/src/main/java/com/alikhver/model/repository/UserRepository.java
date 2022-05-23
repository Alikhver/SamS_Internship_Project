package com.alikhver.model.repository;

import com.alikhver.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsUserByLogin(String login);

    Optional<User> findUserByLogin(String login);
}
