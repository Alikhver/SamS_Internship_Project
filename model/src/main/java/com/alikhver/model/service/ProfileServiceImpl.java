package com.alikhver.model.service;

import com.alikhver.model.entity.Profile;
import com.alikhver.model.entity.User;
import com.alikhver.model.repository.ProfileRepository;
import com.alikhver.model.util.ValidationHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository repository;
    private final ValidationHelper validationHelper;

    @Override
    public void save(Profile profile) {
        log.info("saveProfile -> start");

        validateProfile(profile);

        repository.save(profile);
        log.info("saveProfile -> done");
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsProfileById(Long profileId) {
        log.info("existsProfileById -> start");

        validationHelper.validateForCorrectId(profileId, "ProfileId");
        var exists = repository.existsById(profileId);

        log.info("existsProfileById -> done");
        return exists;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Profile> getProfile(Long profileId) {
        log.info("getProfile -> start");

        validationHelper.validateForCorrectId(profileId, "ProfileId");
        var profile = repository.findById(profileId);

        log.info("getProfile -> done");
        return profile;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Profile> getAllProfiles(Pageable pageable) {
        log.info("getAllProfiles -> start");

        var profiles = repository.findAll(pageable);

        log.info("getAllProfiles -> done");
        return profiles;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isUserAlreadyBounded(Long userId) {
        log.info("isUserAlreadyBounded -> start");

        validationHelper.validateForCorrectId(userId, "UserId");
        boolean isBounded = repository.existsProfileByUserId(userId);

        log.info("isUserAlreadyBounded -> done");
        return isBounded;
    }

    private void validateProfile(Profile profile) {
        log.info("validateProfile -> start");

        validationHelper.validateForCorrectString(profile.getFirstName(), "Profile First Name");
        validationHelper.validateForCorrectString(profile.getLastName(), "Profile Last Name");
        validationHelper.validateForCorrectString(profile.getEmail(), "Profile Email");
        validationHelper.validateForCorrectString(profile.getPhoneNumber(), "Profile phone number");
        validateUser(profile.getUser());
        if (profile.getDateCreated() == null) profile.setDateCreated(new Date());

        log.info("validateProfile -> done");
    }

    private void validateUser(User user) {
        log.info("validateUser -> start");

        validationHelper.validateForCorrectString(user.getLogin(), "User Login");
        validationHelper.validateForCorrectString(user.getPassword(), "User Password");
        Objects.requireNonNull(user.getRole());

        log.info("validateUser -> done");
    }
}
