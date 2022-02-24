package com.alikhver.web.facade;

import com.alikhver.model.entity.User;
import com.alikhver.model.service.UserService;
import com.alikhver.web.converter.UserConverter;
import com.alikhver.web.dto.user.request.CreateUserRequest;
import com.alikhver.web.dto.user.response.CreateUserResponse;
import com.alikhver.web.dto.user.response.DeleteUserResponse;
import com.alikhver.web.dto.user.response.GetAllUsersResponse;
import com.alikhver.web.dto.user.response.GetUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;

    private final UserConverter userConverter;

    public GetUserResponse getUser(String id) {
        User user = userService.getUser(id);

        return userConverter.convertUserToGetUserResponse(user);
    }

    public GetAllUsersResponse getAllUsers() {
        List<User> users = userService.getAllUsers();

        return userConverter.convertUsersToGetAllUsersResponse(users);
    }

    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) {
        // TODO validate

        if (userService.userExistsByLogin(request.getLogin())) {
            // TODO implement if user exists
            return null;
        }

        // TODO create if not exists
        User user = userConverter.convertCreateUserResponseToUser(request);
        user = userService.createUser(user);
        return userConverter.convertUserToCreateUserResponse(user);
    }

    public DeleteUserResponse deleteUser(String id) {
        // TODO implement if user does not exist
        userService.deleteUser(id);

        return null;
    }
}
