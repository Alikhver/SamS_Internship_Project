package com.alikhver.model.service;

import com.alikhver.model.entity.Organisation;
import com.alikhver.model.repository.OrganisationRepository;
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
public class OrganisationServiceImpl implements OrganisationService {
    private final OrganisationRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Organisation> get(Long organisationId) {
        if (organisationId > 0) {
            return repository.findById(organisationId);
        } else {
            throw new IllegalArgumentException(
                    "Illegal argument: organisationId <= 0"
            );
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long organisationId) {
        if (organisationId > 0) {
            return repository.existsById(organisationId);
        } else {
            throw new IllegalArgumentException(
                "Illegal argument: organisationId <= 0"
            );
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Organisation> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        Objects.requireNonNull(name);
        return repository.existsOrganisationByName(name);
    }

    @Override
    public Organisation save(Organisation organisation) {
        Objects.requireNonNull(organisation.getName());
        Objects.requireNonNull(organisation.getDescription());
        Objects.requireNonNull(organisation.getRedactor());
        if (organisation.getDateCreated() == null) organisation.setDateCreated(new Date());

        return repository.save(organisation);
    }

    @Override
    public void delete(Long organisationId) {
        if (organisationId > 0) {
            repository.deleteById(organisationId);
        } else {
            throw new IllegalArgumentException(
                    "Illegal argument: organisationId <= 0"
            );
        }
    }
}
