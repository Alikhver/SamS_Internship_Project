package com.alikhver.web.facade;

import com.alikhver.model.entity.User;
import com.alikhver.model.service.UserService;
import com.alikhver.web.converter.UserConverter;
import com.alikhver.web.dto.user.response.GetAllUsersResponse;
import com.alikhver.web.dto.user.response.GetUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserFacade {

    @Autowired
    private UserService userService;

    public GetUserResponse getUser(String id) {
        User user = userService.getUser(id);

        return UserConverter.convertUserToUserResponse(user);
    }

    public GetAllUsersResponse getAllUsers() {
        List<User> users = userService.getAllUsers();

        return UserConverter.convertUsersToGetAllUsersResponse(users);
    }
}
