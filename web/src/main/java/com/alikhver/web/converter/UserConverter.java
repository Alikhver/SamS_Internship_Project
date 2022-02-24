package com.alikhver.web.converter;

import com.alikhver.model.entity.User;
import com.alikhver.model.entity.UserRole;
import com.alikhver.web.dto.user.request.CreateUserRequest;
import com.alikhver.web.dto.user.response.CreateUserResponse;
import com.alikhver.web.dto.user.response.GetAllUsersResponse;
import com.alikhver.web.dto.user.response.GetUserResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter {

    public GetUserResponse convertUserToGetUserResponse(User user) {
        return GetUserResponse.builder()
                .login(user.getLogin())
                .password(user.getPassword())
                .role(user.getRole().name())
                .build();
    }

    public GetAllUsersResponse convertUsersToGetAllUsersResponse(List<User> users) {

        return GetAllUsersResponse.builder()
                .users(users.stream()
                        .map(this::convertUserToGetUserResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    public User convertCreateUserResponseToUser(CreateUserRequest request) {
        return User.builder()
                .login(request.getLogin())
                .password(request.getPassword())
                .role(UserRole.valueOf(request.getRole().toUpperCase()))
                .build();
    }

    public CreateUserResponse convertUserToCreateUserResponse(User user) {
        return CreateUserResponse.builder()
                .login(user.getLogin())
                .password(user.getPassword())
                .role(user.getRole().toString())
                .build();
    }
}
