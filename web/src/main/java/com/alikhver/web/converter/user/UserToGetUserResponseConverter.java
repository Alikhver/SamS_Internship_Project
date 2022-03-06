package com.alikhver.web.converter.user;

import com.alikhver.model.entity.User;
import com.alikhver.web.dto.user.response.GetUserResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToGetUserResponseConverter implements Converter<User, GetUserResponse> {
    @Override
    public GetUserResponse convert(User user) {
        return GetUserResponse.builder()
                .id(user.getId())
                .login(user.getLogin())
                .password(user.getPassword())
                .role(user.getRole().name())
                .build();
    }
}
