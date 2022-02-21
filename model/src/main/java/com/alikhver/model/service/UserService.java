package com.alikhver.model.service;

import com.alikhver.model.entity.User;

import java.util.List;

public interface UserService {
    User getUser(String id);

    List<User> getAllUsers();
}
