package com.alikhver.web.converter.user;

import com.alikhver.model.entity.User;
import com.alikhver.web.dto.user.response.CreateUserResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToCreateUserResponseConverter implements Converter<User, CreateUserResponse> {
    @Override
    public CreateUserResponse convert(User user) {
        return CreateUserResponse.builder()
                .id(user.getId())
                .login(user.getLogin())
                .role(user.getRole().toString())
                .build();
    }
}
