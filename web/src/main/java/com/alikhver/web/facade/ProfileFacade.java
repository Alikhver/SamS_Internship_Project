package com.alikhver.web.facade;

import com.alikhver.web.dto.profile.request.CreateProfileRequest;
import com.alikhver.web.dto.profile.request.UpdateProfileRequest;
import com.alikhver.web.dto.profile.response.CreateProfileResponse;
import com.alikhver.web.dto.profile.response.GetProfileResponse;
import org.springframework.data.domain.Page;

public interface ProfileFacade {
    CreateProfileResponse createProfile(CreateProfileRequest request);

    GetProfileResponse getProfile(Long id);

    Page<GetProfileResponse> getProfiles(int page, int size);

    void updateProfile(Long id, UpdateProfileRequest request);
}
