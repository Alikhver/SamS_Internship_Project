package com.alikhver.web.facade;

import com.alikhver.model.entity.User;
import com.alikhver.model.service.UserService;
import com.alikhver.web.dto.user.response.GetUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserFacade {

    @Autowired
    private UserService userService;

    public GetUserResponse getUser(String id) {
        User user = userService.getUser(id);

        return GetUserResponse.builder()
                .login(user.getLogin())
                .password(user.getPassword())
                .role(user.getRole().name())
                .build();
    }
}
