package com.alikhver.model.service;

import com.alikhver.model.entity.User;
import com.alikhver.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUser(String id) {
        return userRepository.getById(Long.parseLong(id));
    }
}
