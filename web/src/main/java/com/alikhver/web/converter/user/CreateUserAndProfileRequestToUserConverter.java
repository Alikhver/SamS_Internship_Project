package com.alikhver.web.converter.user;

import com.alikhver.model.entity.User;
import com.alikhver.model.entity.UserRole;
import com.alikhver.web.dto.profile.request.CreateUserAndProfileRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CreateUserAndProfileRequestToUserConverter implements Converter<CreateUserAndProfileRequest, User> {
    @Override
    public User convert(CreateUserAndProfileRequest source) {
        Objects.requireNonNull(source.getLogin());
        Objects.requireNonNull(source.getPassword());

        return User.builder()
                .login(source.getLogin())
                .password(source.getPassword())
                .role(UserRole.USER)
                .build();
    }
}
