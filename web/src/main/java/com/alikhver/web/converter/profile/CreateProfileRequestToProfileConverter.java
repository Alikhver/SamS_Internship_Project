package com.alikhver.web.converter.profile;

import com.alikhver.model.entity.Profile;
import com.alikhver.web.dto.profile.request.CreateProfileRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CreateProfileRequestToProfileConverter implements Converter<CreateProfileRequest, Profile> {
    @Override
    public Profile convert(CreateProfileRequest request) {
        return Profile.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .dateCreated(new Date())
                .phoneNumber(request.getPhoneNumber())
                .build();
    }
}
