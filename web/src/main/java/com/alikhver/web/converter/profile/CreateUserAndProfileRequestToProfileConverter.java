package com.alikhver.web.converter.profile;

import com.alikhver.model.entity.Profile;
import com.alikhver.web.dto.profile.request.CreateUserAndProfileRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CreateUserAndProfileRequestToProfileConverter implements Converter<CreateUserAndProfileRequest, Profile> {
    @Override
    public Profile convert(CreateUserAndProfileRequest source) {
        Objects.requireNonNull(source.getLogin());
        Objects.requireNonNull(source.getLastName());
        Objects.requireNonNull(source.getPhoneNumber());
        Objects.requireNonNull(source.getEmail());

        return Profile.builder()
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .phoneNumber(source.getPhoneNumber())
                .email(source.getEmail())
                .build();
    }
}
