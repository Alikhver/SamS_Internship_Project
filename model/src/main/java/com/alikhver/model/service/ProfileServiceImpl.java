package com.alikhver.model.service;

import com.alikhver.model.entity.Profile;
import com.alikhver.model.repository.ProfileRepository;
import com.alikhver.model.service.util.ServiceValidationHelper;
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
    private final ProfileRepository repository;
    private final ServiceValidationHelper validationHelper;

    @Override
    public void save(Profile profile) {
        validationHelper.validateProfile(profile);
        repository.save(profile);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long profileId) {

        if (profileId > 0) {
            return repository.existsById(profileId);
        } else {
            throw new IllegalArgumentException(
                    "Illegal argument: profileId <= 0"
            );
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Profile> get(Long profileId) {
        if (profileId > 0) {
            return repository.findById(profileId);
        } else {
            throw new IllegalArgumentException(
                    "Illegal argument: organisationId <= 0"
            );
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Profile> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
