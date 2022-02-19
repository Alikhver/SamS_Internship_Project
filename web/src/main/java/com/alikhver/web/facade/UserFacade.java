package com.alikhver.web.facade;

import com.alikhver.model.entity.User;
import com.alikhver.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserFacade {

    @Autowired
    private UserService userService;

    public User getUser(String id) {
        User user = userService.getUser(id);
        return user;
    }
}
