package com.alikhver.model.service;

import com.alikhver.model.entity.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProfileService {

    void save(Profile profile);

    boolean existsProfileById(Long id);

    Optional<Profile> getProfile(Long id);

    Page<Profile> getAllProfiles(Pageable pageable);
}
