package com.alikhver.web.facade;

import com.alikhver.model.entity.Profile;
import com.alikhver.model.entity.User;
import com.alikhver.model.service.ProfileService;
import com.alikhver.model.service.UserService;
import com.alikhver.web.converter.ProfileConverter;
import com.alikhver.web.dto.profile.request.CreateProfileRequest;
import com.alikhver.web.dto.profile.response.CreateProfileResponse;
import com.alikhver.web.dto.profile.response.GetProfileResponse;
import com.alikhver.web.exeption.profile.NoProfileFoundException;
import com.alikhver.web.exeption.user.NoUserFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProfileFacadeImpl implements ProfileFacade{
    private final ProfileService profileService;
    private final UserService userService;
    private final ProfileConverter profileConverter;

    @Transactional
    @Override
    public CreateProfileResponse createProfile(CreateProfileRequest request) throws NoUserFoundException {
        Objects.requireNonNull(request.getUserId());
        Objects.requireNonNull(request.getFirstName());
        Objects.requireNonNull(request.getLastName());
        Objects.requireNonNull(request.getEmail());

        User user;
        if (userService.userExistsById(request.getUserId())) {
            user = userService.getUser(request.getUserId());
        } else {
            throw new NoUserFoundException(
              "No user with id = " + request.getUserId() + " found"
            );
        }

        Profile profile = profileConverter.convertCreateProfileRequestToProfile(request);
        profile.setUser(user);

        profileService.createProfile(profile);

        return profileConverter.convertProfileToCreateProfileResponse(profile);
    }

    @Override
    public GetProfileResponse getProfile(Long id) throws NoProfileFoundException {
        if (profileService.profileExistsById(id)) {
            Profile profile = profileService.getProfile(id);
            return profileConverter.convertProfileToGetProfileResponse(profile);
        } else {
            throw new NoProfileFoundException(
                    "No Profile with id = " + id + " found"
            );
        }
    }
}
