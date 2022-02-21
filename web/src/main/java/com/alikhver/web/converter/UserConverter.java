package com.alikhver.web.converter;

import com.alikhver.model.entity.User;
import com.alikhver.web.dto.user.response.GetAllUsersResponse;
import com.alikhver.web.dto.user.response.GetUserResponse;

import java.util.List;
import java.util.stream.Collectors;

public class UserConverter {

    public static GetUserResponse convertUserToUserResponse(User user) {
        return GetUserResponse.builder()
                .login(user.getLogin())
                .password(user.getPassword())
                .role(user.getRole().name())
                .build();
    }

    public static GetAllUsersResponse convertUsersToGetAllUsersResponse(List<User> users) {

        return GetAllUsersResponse.builder()
                .users(users.stream()
                        .map(UserConverter::convertUserToUserResponse)
                        .collect(Collectors.toList()))
                .build();
    }
}
