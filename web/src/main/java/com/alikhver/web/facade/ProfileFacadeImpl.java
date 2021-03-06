package com.alikhver.web.facade;

import com.alikhver.model.entity.Profile;
import com.alikhver.model.entity.ScheduleRecord;
import com.alikhver.model.entity.User;
import com.alikhver.model.entity.UserRole;
import com.alikhver.model.entity.Utility;
import com.alikhver.model.entity.Worker;
import com.alikhver.model.service.ProfileService;
import com.alikhver.model.service.UserService;
import com.alikhver.model.util.ValidationHelper;
import com.alikhver.web.converter.profile.ProfileConverter;
import com.alikhver.web.converter.scheduleRecord.ScheduleRecordConverter;
import com.alikhver.web.converter.user.UserConverter;
import com.alikhver.web.converter.utility.UtilityConverter;
import com.alikhver.web.converter.worker.WorkerConverter;
import com.alikhver.web.dto.profile.request.CreateProfileRequest;
import com.alikhver.web.dto.profile.request.CreateUserAndProfileRequest;
import com.alikhver.web.dto.profile.request.UpdateProfileRequest;
import com.alikhver.web.dto.profile.response.CreateProfileResponse;
import com.alikhver.web.dto.profile.response.GetProfileResponse;
import com.alikhver.web.dto.record.response.GetRecordUtilityWorkerResponse;
import com.alikhver.web.exception.profile.AttemptToAssignProfileToUserWithWrongRole;
import com.alikhver.web.exception.profile.NoProfileFoundException;
import com.alikhver.web.exception.profile.ProfileAlreadyExistsException;
import com.alikhver.web.exception.profile.UserIsAlreadyBoundedProfileException;
import com.alikhver.web.exception.user.NoUserFoundException;
import com.alikhver.web.exception.user.UserAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProfileFacadeImpl implements ProfileFacade {
    @Autowired
    private ProfileService profileService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProfileConverter profileConverter;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private ValidationHelper validationHelper;
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UtilityConverter utilityConverter;
    @Autowired
    private WorkerConverter workerConverter;
    @Autowired
    private ScheduleRecordConverter recordConverter;

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
            throw new NoUserFoundException(request.getUserId());
        }
        if (user.getRole() != UserRole.USER) {
            log.warn("AttemptToAssignProfileToUserWithWrongRole is thrown");
            throw new AttemptToAssignProfileToUserWithWrongRole(user.getId());
        }

        if (profileService.isUserAlreadyBounded(user.getId())) {
            log.warn("UserIsAlreadyBoundedProfileException is thrown");
            throw new UserIsAlreadyBoundedProfileException(user.getId());
        }

        Profile profile = profileConverter.mapToCreateProfileRequest(request);
        profile.setUser(user);

        profileService.save(profile);

        var response = profileConverter.mapToCreateProfileResponse(profile);

        log.info("createProfile -> done");
        return response;
    }

    @Override
    public GetProfileResponse getProfileById(Long id) throws NoProfileFoundException {
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
            throw new NoProfileFoundException(id);
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
            throw new NoProfileFoundException(id);
        }

        User user = profile.getUser();

        Optional.ofNullable(request.getPassword()).ifPresent(password -> user.setPassword(passwordEncoder.encode(password)));
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
            throw new NoProfileFoundException(id);
        });


        profileService.delete(id);

        log.info("deleteProfile -> done");
    }

    @Override
    @Transactional
    public void createUserAndProfile(CreateUserAndProfileRequest request) {
        log.info("createUserAndProfile -> start");

        User user = userConverter.mapToUser(request);

        if (userService.existsUserByLogin(user.getLogin())) {
            log.warn("UserAlreadyExistsException is thrown");
            throw new UserAlreadyExistsException(user.getLogin());
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
    @Transactional(readOnly = true)
    public Profile getProfileByLogin(String login) {
        log.info("getProfile -> start");

        User user = userService.findUserByLogin(login).orElseThrow(() -> {
            log.warn("NoProfileFoundException is thrown");
            throw new NoUserFoundException(login);
        });

        Profile profile = profileService.getProfileByUserId(user.getId()).orElseThrow(() -> {
            log.warn("NoProfileFoundException is thrown");
            throw new NoProfileFoundException(user.getId());
        });

        log.info("getProfile -> done");
        return profile;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GetRecordUtilityWorkerResponse> getRecordDataOfProfile(String login) {
        log.info("getRecordDataOfProfile -> start");

        Profile profile = getProfileByLogin(login);

        List<ScheduleRecord> records = profile.getRecords();

        List<GetRecordUtilityWorkerResponse> response = records.stream().map(record -> {
                    Utility utility = record.getUtility();
                    Worker worker = record.getWorker();

                    return GetRecordUtilityWorkerResponse.builder()
                            .utility(utilityConverter.mapToGetUtilityResponse(utility))
                            .record(recordConverter.mapToGetRecordResponse(record))
                            .worker(workerConverter.mapToGetWorkerResponse(worker))
                            .build();
                }).sorted(Comparator.comparing(o -> o.getRecord().getDate()))
                .collect(Collectors.toList());


        log.info("getRecordDataOfProfile -> done");
        return response;
    }
}
