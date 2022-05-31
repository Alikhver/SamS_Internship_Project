package com.alikhver.web.converter.profile;

import com.alikhver.model.entity.Profile;
import com.alikhver.web.dto.profile.request.CreateProfileRequest;
import com.alikhver.web.dto.profile.request.CreateUserAndProfileRequest;
import com.alikhver.web.dto.profile.response.CreateProfileResponse;
import com.alikhver.web.dto.profile.response.GetProfileResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProfileConverter {
    private final CreateProfileRequestToProfileConverter createProfileRequestToProfileConverter;
    private final ProfileToCreateProfileResponseConverter profileToCreateProfileResponseConverter;
    private final ProfileToGetProfileResponseConverter profileToGetProfileResponseConverter;
    private final PageOfProfilesToPageOfGetProfileResponseConverter pageOfProfilesToPageOfGetProfileResponseConverter;
    private final CreateUserAndProfileRequestToProfileConverter createUserAndProfileRequestToProfileConverter;

    public Profile mapToCreateProfileRequest(CreateProfileRequest request) {
        log.info("mapToCreateProfileRequest -> start");

        var response = createProfileRequestToProfileConverter.convert(request);

        log.info("mapToCreateProfileRequest -> done");
        return response;
    }

    public CreateProfileResponse mapToCreateProfileResponse(Profile profile) {
        log.info("mapToCreateProfileResponse -> start");

        var response = profileToCreateProfileResponseConverter.convert(profile);

        log.info("mapToCreateProfileResponse -> done");
        return response;
    }

    public GetProfileResponse mapToGetProfileResponse(Profile profile) {
        log.info("mapToGetProfileResponse -> start");

        var response = profileToGetProfileResponseConverter.convert(profile);

        log.info("mapToGetProfileResponse -> done");
        return response;
    }

    public Page<GetProfileResponse> mapToListOfGetProfileResponse(Page<Profile> profiles) {
        log.info("mapToListOfGetProfileResponse -> start");

        var response = pageOfProfilesToPageOfGetProfileResponseConverter.convert(profiles);

        log.info("mapToListOfGetProfileResponse -> done");
        return response;
    }

    public Profile mapToProfile(CreateUserAndProfileRequest request) {
        log.info("mapToProfile -> start");

        var response = createUserAndProfileRequestToProfileConverter.convert(request);

        log.info("mapToProfile -> done");
        return response;
    }
}
