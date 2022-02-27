package com.alikhver.web.converter;

import com.alikhver.model.entity.Profile;
import com.alikhver.web.dto.profile.request.CreateProfileRequest;
import com.alikhver.web.dto.profile.response.CreateProfileResponse;
import com.alikhver.web.dto.profile.response.GetProfileResponse;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ProfileConverter {

    public Profile convertCreateProfileRequestToProfile(CreateProfileRequest request) {
        return Profile.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .dateCreated(new Date())
                .build();
    }

    public CreateProfileResponse convertProfileToCreateProfileResponse(Profile profile) {
        return CreateProfileResponse.builder()
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .email(profile.getEmail())
                .userId(profile.getUser().getId())
                .build();
    }

    public GetProfileResponse convertProfileToGetProfileResponse(Profile profile) {
        return GetProfileResponse.builder()
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .email(profile.getEmail())
                .userId(profile.getUser().getId())
                .build();
    }
}
