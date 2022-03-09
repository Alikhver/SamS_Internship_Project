package com.alikhver.model.service;

import com.alikhver.model.entity.Profile;
import com.alikhver.model.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
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
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository repository;

    @Override
    public void save(Profile profile) {
        Objects.requireNonNull(profile.getFirstName());
        Objects.requireNonNull(profile.getLastName());
        Objects.requireNonNull(profile.getEmail());
        Objects.requireNonNull(profile.getPhoneNumber());
        Objects.requireNonNull(profile.getUser());
        if (profile.getDateCreated() == null) profile.setDateCreated(new Date());
        repository.save(profile);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long profileId) {
        assert (profileId > 0);
        return repository.existsById(profileId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Profile> get(Long profileId) {
        assert (profileId > 0);
        return repository.findById(profileId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Profile> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
