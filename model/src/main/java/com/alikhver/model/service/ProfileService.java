package com.alikhver.model.service;

import com.alikhver.model.entity.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProfileService {

    void createProfile(Profile profile);

    boolean profileExistsById(Long id);

    Optional<Profile> getProfile(Long id);

    Page<Profile> getProfiles(Pageable pageable);

    void updateProfile(Profile profile);
}
