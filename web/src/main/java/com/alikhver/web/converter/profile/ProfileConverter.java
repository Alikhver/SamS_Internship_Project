package com.alikhver.web.converter.profile;

import com.alikhver.model.entity.Profile;
import com.alikhver.web.dto.profile.request.CreateProfileRequest;
import com.alikhver.web.dto.profile.response.CreateProfileResponse;
import com.alikhver.web.dto.profile.response.GetProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProfileConverter {
    private final CreateProfileRequestToProfileConverter createProfileRequestToProfileConverter;
    private final ProfileToCreateProfileResponseConverter profileToCreateProfileResponseConverter;
    private final ProfileToGetProfileResponseConverter profileToGetProfileResponseConverter;
    private final ProfilesToListOfGetProfileResponseConverter profilesToListOfGetProfileResponseConverter;

    public Profile mapToCreateProfileRequest(CreateProfileRequest request) {
        return createProfileRequestToProfileConverter.convert(request);
    }

    public CreateProfileResponse mapToCreateProfileResponse(Profile profile) {
        return profileToCreateProfileResponseConverter.convert(profile);
    }

    public GetProfileResponse mapToGetProfileResponse(Profile profile) {
        return profileToGetProfileResponseConverter.convert(profile);
    }

    public List<GetProfileResponse> mapToListOfGetProfileResponse(List<Profile> profiles) {
        return profilesToListOfGetProfileResponseConverter.convert(profiles);
    }
}
