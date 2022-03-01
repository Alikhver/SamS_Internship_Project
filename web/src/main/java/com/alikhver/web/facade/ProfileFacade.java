package com.alikhver.web.facade;

import com.alikhver.web.dto.profile.request.CreateProfileRequest;
import com.alikhver.web.dto.profile.request.UpdateProfileRequest;
import com.alikhver.web.dto.profile.response.CreateProfileResponse;
import com.alikhver.web.dto.profile.response.GetProfileResponse;

import java.util.List;

public interface ProfileFacade {
    CreateProfileResponse createProfile(CreateProfileRequest request);

    GetProfileResponse getProfile(Long id);

    List<GetProfileResponse> getProfiles();

    void updateProfile(Long id, UpdateProfileRequest request);
}
