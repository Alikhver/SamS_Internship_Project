package com.alikhver.model.service;

import com.alikhver.model.entity.Profile;

import java.util.List;
import java.util.Optional;

public interface ProfileService {

    void createProfile(Profile profile);

    boolean profileExistsById(Long id);

    Optional<Profile> getProfile(Long id);

    List<Profile> getProfiles();

    void updateProfile(Profile profile);
}
