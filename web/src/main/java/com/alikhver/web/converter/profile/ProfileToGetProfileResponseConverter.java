package com.alikhver.web.converter.profile;

import com.alikhver.model.entity.Profile;
import com.alikhver.web.dto.profile.response.GetProfileResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProfileToGetProfileResponseConverter implements Converter<Profile, GetProfileResponse> {
    @Override
    public GetProfileResponse convert(Profile profile) {
        return GetProfileResponse.builder()
                .id(profile.getId())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .email(profile.getEmail())
                .userId(profile.getUser().getId())
                .phoneNumber(profile.getPhoneNumber())
                .build();
    }
}
