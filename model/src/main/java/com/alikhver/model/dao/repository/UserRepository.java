package com.alikhver.model.dao.repository;

import com.alikhver.model.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
