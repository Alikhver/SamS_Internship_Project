package com.alikhver.web.converter.profile;

import com.alikhver.model.entity.Profile;
import com.alikhver.web.dto.profile.response.CreateProfileResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProfileToCreateProfileResponseConverter implements Converter<Profile, CreateProfileResponse> {
    @Override
    public CreateProfileResponse convert(Profile profile) {
        return CreateProfileResponse.builder()
                .id(profile.getId())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .email(profile.getEmail())
                .userId(profile.getUser().getId())
                .phoneNumber(profile.getPhoneNumber())
                .build();
    }
}
