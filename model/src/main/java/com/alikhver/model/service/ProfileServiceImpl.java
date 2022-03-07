package com.alikhver.model.service;

import com.alikhver.model.entity.Profile;
import com.alikhver.model.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;

    @Override
    public void createProfile(Profile profile) {
        profileRepository.save(profile);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean profileExistsById(Long id) {
        return profileRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Profile> getProfile(Long id) {
        return profileRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Profile> getProfiles(Pageable pageable) {
        return profileRepository.findAll(pageable);
    }

    @Override
    public void updateProfile(Profile profile) {
        profileRepository.save(profile);
    }
}
