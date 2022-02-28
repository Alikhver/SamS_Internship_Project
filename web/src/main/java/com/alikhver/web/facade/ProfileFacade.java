package com.alikhver.web.facade;

import com.alikhver.web.dto.profile.request.CreateProfileRequest;
import com.alikhver.web.dto.profile.response.CreateProfileResponse;
import com.alikhver.web.dto.profile.response.GetProfileResponse;

public interface ProfileFacade {
    CreateProfileResponse createProfile(CreateProfileRequest request);

    GetProfileResponse getProfile(Long id);
}
