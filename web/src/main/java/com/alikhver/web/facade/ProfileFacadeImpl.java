package com.alikhver.web.facade;

import com.alikhver.model.entity.Profile;
import com.alikhver.model.entity.User;
import com.alikhver.model.service.ProfileService;
import com.alikhver.model.service.UserService;
import com.alikhver.web.converter.profile.ProfileConverter;
import com.alikhver.web.dto.profile.request.CreateProfileRequest;
import com.alikhver.web.dto.profile.request.UpdateProfileRequest;
import com.alikhver.web.dto.profile.response.CreateProfileResponse;
import com.alikhver.web.dto.profile.response.GetProfileResponse;
import com.alikhver.web.exeption.profile.NoProfileFoundException;
import com.alikhver.web.exeption.user.NoUserFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileFacadeImpl implements ProfileFacade{
    private final ProfileService profileService;
    private final UserService userService;
    private final ProfileConverter profileConverter;

    @Override
    @Transactional
    public CreateProfileResponse createProfile(CreateProfileRequest request) throws NoUserFoundException {
        Objects.requireNonNull(request.getUserId());
        Objects.requireNonNull(request.getFirstName());
        Objects.requireNonNull(request.getLastName());
        Objects.requireNonNull(request.getEmail());

        Optional<User> optionalUser = userService.get(request.getUserId());
        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            throw new NoUserFoundException(
              "No user with id = " + request.getUserId() + " found"
            );
        }

        Profile profile = profileConverter.mapToCreateProfileRequest(request);
        profile.setUser(user);

        profileService.createProfile(profile);

        return profileConverter.mapToCreateProfileResponse(profile);
    }

    @Override
    public GetProfileResponse getProfile(Long id) throws NoProfileFoundException {
        Optional<Profile> optionalProfile = profileService.getProfile(id);
        if (optionalProfile.isPresent()) {
            Profile profile = optionalProfile.get();
            return profileConverter.mapToGetProfileResponse(profile);
        } else {
            throw new NoProfileFoundException(
                    "No Profile with id = " + id + " found"
            );
        }
    }

    @Override
    public Page<GetProfileResponse> getProfiles(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Profile> profiles = profileService.getProfiles(pageable);
        return profileConverter.mapToListOfGetProfileResponse(profiles);
    }

    @Override
    @Transactional
    public void updateProfile(Long id, UpdateProfileRequest request) {
        Optional<Profile> optionalProfile = profileService.getProfile(id);
        Profile profile;
        if (optionalProfile.isPresent()) {
            profile = optionalProfile.get();
        } else {
            throw new NoProfileFoundException(
              "No profile with id = " + id + "found"
            );
        }

        Optional.ofNullable(request.getFirstName()).ifPresent(profile::setFirstName);
        Optional.ofNullable(request.getLastName()).ifPresent(profile::setLastName);
        Optional.ofNullable(request.getPhoneNumber()).ifPresent(profile::setPhoneNumber);
        Optional.ofNullable(request.getEmail()).ifPresent(profile::setEmail);

        profileService.updateProfile(profile);
    }
}
