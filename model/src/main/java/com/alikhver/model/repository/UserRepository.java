package com.alikhver.model.repository;

import com.alikhver.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsUserByLogin(String login);
}
