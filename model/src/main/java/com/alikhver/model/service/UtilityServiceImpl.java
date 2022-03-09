package com.alikhver.model.service;

import com.alikhver.model.entity.Utility;
import com.alikhver.model.repository.UtilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UtilityServiceImpl implements UtilityService {
    private final UtilityRepository repository;

    @Override
    public void save(Utility utility) {
        Objects.requireNonNull(utility.getName());
        Objects.requireNonNull(utility.getPrice());
        Objects.requireNonNull(utility.getDescription());
        Objects.requireNonNull(utility.getOrganisation());

        repository.save(utility);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(Utility utility) {
        Objects.requireNonNull(utility.getName());
        Objects.requireNonNull(utility.getPrice());
        Objects.requireNonNull(utility.getDescription());
        Objects.requireNonNull(utility.getOrganisation());

        return repository.existsByNameAndDescriptionAndPriceAndOrganisationId(
                utility.getName(),
                utility.getDescription(),
                utility.getPrice(),
                utility.getOrganisation().getId()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(Long utilityId) {
        if (utilityId > 0) {
            return repository.existsById(utilityId);
        } else {
            throw new IllegalArgumentException(
                    "Illegal Argument: utilityId <= 0"
            );
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Utility> get(Long utilityId) {
        if (utilityId > 0) {
            return repository.findById(utilityId);
        } else {
            throw new IllegalArgumentException(
                    "Illegal Argument: utilityId <= 0"
            );
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Utility> getAllUtilitiesOfOrganisation(Long organisationId, Pageable pageable) {
        if (organisationId > 0) {
            return repository.findAllByOrganisationId(organisationId, pageable);
        } else {
            throw new IllegalArgumentException(
                    "Illegal Argument: organisationId <= 0"
            );
        }
    }

    @Override
    public void delete(Long utilityId) {
        if (utilityId > 0) {
            repository.deleteById(utilityId);
        } else {
            throw new IllegalArgumentException(
                    "Illegal Argument: utilityId <= 0"
            );
        }
    }
}
