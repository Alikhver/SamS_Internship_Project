package com.alikhver.web.converter;

import com.alikhver.model.entity.Profile;
import com.alikhver.web.dto.profile.request.CreateProfileRequest;
import com.alikhver.web.dto.profile.response.CreateProfileResponse;
import com.alikhver.web.dto.profile.response.GetProfileResponse;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProfileConverter {

    public Profile mapToCreateProfileRequest(CreateProfileRequest request) {
        return Profile.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .dateCreated(new Date())
                .phoneNumber(request.getPhoneNumber())
                .build();
    }

    public CreateProfileResponse mapToCreateProfileResponse(Profile profile) {
        return CreateProfileResponse.builder()
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .email(profile.getEmail())
                .userId(profile.getUser().getId())
                .phoneNumber(profile.getPhoneNumber())
                .build();
    }

    public GetProfileResponse mapToGetProfileResponse(Profile profile) {
        return GetProfileResponse.builder()
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .email(profile.getEmail())
                .userId(profile.getUser().getId())
                .phoneNumber(profile.getPhoneNumber())
                .build();
    }

    public List<GetProfileResponse> mapToListOfGetProfileResponse(List<Profile> profiles) {
        return profiles.stream()
                .map(this::mapToGetProfileResponse)
                .collect(Collectors.toList());
    }
}
