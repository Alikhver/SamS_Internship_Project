package com.alikhver.web.facade;

import com.alikhver.model.entity.Profile;
import com.alikhver.model.entity.User;
import com.alikhver.model.entity.UserRole;
import com.alikhver.model.service.ProfileService;
import com.alikhver.model.service.UserService;
import com.alikhver.model.util.ValidationHelper;
import com.alikhver.web.converter.profile.ProfileConverter;
import com.alikhver.web.converter.user.UserConverter;
import com.alikhver.web.dto.profile.request.CreateProfileRequest;
import com.alikhver.web.dto.profile.request.CreateUserAndProfileRequest;
import com.alikhver.web.dto.profile.request.UpdateProfileRequest;
import com.alikhver.web.dto.profile.response.CreateProfileResponse;
import com.alikhver.web.dto.profile.response.GetProfileResponse;
import com.alikhver.web.exception.profile.AttemptToAssignProfileToUserWithWrongRole;
import com.alikhver.web.exception.profile.NoProfileFoundException;
import com.alikhver.web.exception.profile.ProfileAlreadyExistsException;
import com.alikhver.web.exception.profile.UserIsAlreadyBoundedProfileException;
import com.alikhver.web.exception.user.NoUserFoundException;
import com.alikhver.web.exception.user.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileFacadeImpl implements ProfileFacade {
    private final ProfileService profileService;
    private final UserService userService;
    private final ProfileConverter profileConverter;
    private final UserConverter userConverter;
    private final ValidationHelper validationHelper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public CreateProfileResponse createProfile(CreateProfileRequest request) {
        log.info("createProfile -> start");

        Optional<User> optionalUser = userService.getUser(request.getUserId());
        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            log.warn("NoUserFoundException is thrown");
            throw new NoUserFoundException(
                    "No user with id = " + request.getUserId() + " found"
            );
        }
        if (user.getRole() != UserRole.USER) {
            log.warn("");
            throw new AttemptToAssignProfileToUserWithWrongRole(
                    "Provided user with id = " + user.getId() + " has not role of UserRole.User"
            );
        }

        if (profileService.isUserAlreadyBounded(user.getId())) {
            log.warn("UserIsAlreadyBoundedProfileException is thrown");
            throw new UserIsAlreadyBoundedProfileException(
                    "User with id = " + user.getId() + " is already bounded"
            );
        }

        Profile profile = profileConverter.mapToCreateProfileRequest(request);
        profile.setUser(user);

        profileService.save(profile);

        var response = profileConverter.mapToCreateProfileResponse(profile);

        log.info("createProfile -> done");
        return response;
    }

    @Override
    public GetProfileResponse getProfileByUserLogin(Long id) throws NoProfileFoundException {
        log.info("getProfile -> start");

        validationHelper.validateForCorrectId(id, "ProfileId");

        Optional<Profile> optionalProfile = profileService.getProfile(id);
        if (optionalProfile.isPresent()) {
            Profile profile = optionalProfile.get();

            var response = profileConverter.mapToGetProfileResponse(profile);

            log.info("getProfile -> done");
            return response;
        } else {
            log.warn("NoProfileFoundException is thrown");
            throw new NoProfileFoundException(
                    "No Profile with id = " + id + " found"
            );
        }
    }

    @Override
    public Page<GetProfileResponse> getProfiles(int page, int size) {
        log.info("getProfiles -> start");

        Pageable pageable = PageRequest.of(page, size);
        Page<Profile> profiles = profileService.getAllProfiles(pageable);

        var response = profileConverter.mapToListOfGetProfileResponse(profiles);

        log.info("getProfiles -> done");
        return response;
    }

    @Override
    @Transactional
    public void updateProfile(Long id, UpdateProfileRequest request) {
        log.info("updateProfile -> start");

        validationHelper.validateForCorrectId(id, "ProfileId");

        Optional<Profile> optionalProfile = profileService.getProfile(id);
        Profile profile;
        if (optionalProfile.isPresent()) {
            profile = optionalProfile.get();
        } else {
            log.warn("NoProfileFoundException is thrown");
            throw new NoProfileFoundException(
                    "No profile with id = " + id + "found"
            );
        }

        Optional.ofNullable(request.getFirstName()).ifPresent(profile::setFirstName);
        Optional.ofNullable(request.getLastName()).ifPresent(profile::setLastName);
        Optional.ofNullable(request.getPhoneNumber()).ifPresent(profile::setPhoneNumber);
        Optional.ofNullable(request.getEmail()).ifPresent(profile::setEmail);

        profileService.save(profile);
        log.info("updateProfile -> done");
    }

    @Override
    public void deleteProfile(Long id) {
        log.info("deleteProfile -> start");

        validationHelper.validateForCorrectId(id, "ProfileId");

        Profile profile = profileService.getProfile(id).orElseThrow(() -> {
            log.warn("NoProfileFoundException is thrown");
            throw new NoProfileFoundException(
                    "No Profile with id = " + id + " found"
            );
        });

        User user = profile.getUser();

        profileService.delete(id);

//        userService.deleteUser(user.getId());

        log.info("deleteProfile -> done");
    }

    @Override
    @Transactional
    public void createUserAndProfile(CreateUserAndProfileRequest request) {
        log.info("createUserAndProfile -> start");

        User user = userConverter.mapToUser(request);

        if (userService.existsUserByLogin(user.getLogin())) {
            log.warn("UserAlreadyExistsException is thrown");
            throw new UserAlreadyExistsException(
                    "User with login=" + user.getLogin() + " already exists"
            );
        }

        Profile profile = profileConverter.mapToProfile(request);

        if (profileService.existsProfileByEmail(profile.getEmail())) {
            log.warn("ProfileAlreadyExistsException is thrown");
            throw new ProfileAlreadyExistsException(
                    "Profile with email = " + profile.getEmail() + " already exists"
            );
        }

        if (profileService.phoneNumberExists(profile.getPhoneNumber())) {
            log.warn("ProfileAlreadyExistsException is thrown");
            throw new ProfileAlreadyExistsException(
                    "Profile with phoneNumber=" + profile.getPhoneNumber() + " already exists"
            );
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        profile.setUser(user);

        profileService.save(profile);

        log.info("createUserAndProfile -> done");
    }

    @Override
    public boolean emailExists(String email) {
        log.info("emailExists -> start");

        boolean exists = profileService.existsProfileByEmail(email);

        log.info("emailExists -> done");
        return exists;
    }

    @Override
    public boolean phoneNumberExists(String phoneNumber) {
        log.info("phoneNumberExists -> start");

        boolean exists = profileService.phoneNumberExists(phoneNumber);

        log.info("phoneNumberExists -> done");
        return exists;
    }

    @Override
    public Profile getProfileByUserLogin(String login) {
        log.info("getProfile -> start");

        User user = userService.findUserByLogin(login).orElseThrow(() -> {
            log.warn("NoProfileFoundException is thrown");
            throw new NoUserFoundException(
                    "No User with login = " + login + " found"
            );
        });

        Profile profile = profileService.getProfileByUserId(user.getId()).orElseThrow(() -> {
            log.warn("NoProfileFoundException is thrown");
            throw new NoProfileFoundException(
                    "No Profile with userId = " + user.getId() + " found"
            );
        });

        log.info("getProfile -> done");
        return profile;
    }
}
