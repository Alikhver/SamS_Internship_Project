package com.alikhver.web.converter.user;

import com.alikhver.model.entity.User;
import com.alikhver.model.entity.UserRole;
import com.alikhver.web.dto.user.request.CreateUserRequest;
import com.alikhver.web.dto.user.response.CreateUserResponse;
import com.alikhver.web.dto.user.response.GetUserResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter {

    public GetUserResponse mapToGetUserResponse(User user) {
        return GetUserResponse.builder()
                .id(user.getId())
                .login(user.getLogin())
                .password(user.getPassword())
                .role(user.getRole().name())
                .build();
    }

    public List<GetUserResponse> mapToListOfGetUserResponse(List<User> users) {
        return users.stream()
                .map(this::mapToGetUserResponse)
                .collect(Collectors.toList());
    }

    public User mapToUser(CreateUserRequest request) {
        return User.builder()
                .login(request.getLogin())
                .password(request.getPassword())
                .role(UserRole.USER)
                .build();
    }

    public CreateUserResponse mapToCreateUserResponse(User user) {
        return CreateUserResponse.builder()
                .id(user.getId())
                .login(user.getLogin())
                .password(user.getPassword())
                .role(user.getRole().toString())
                .build();
    }
}
