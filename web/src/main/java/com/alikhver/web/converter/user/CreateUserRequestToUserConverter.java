package com.alikhver.web.converter.user;

import com.alikhver.model.entity.User;
import com.alikhver.model.entity.UserRole;
import com.alikhver.web.dto.user.request.CreateUserRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CreateUserRequestToUserConverter implements Converter<CreateUserRequest, User> {
    @Override
    public User convert(CreateUserRequest request) {
        return User.builder()
                .login(request.getLogin())
                .password(request.getPassword())
                .role(UserRole.USER)
                .build();
    }
}
