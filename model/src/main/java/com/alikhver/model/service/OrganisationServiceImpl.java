package com.alikhver.model.service;

import com.alikhver.model.entity.Organisation;
import com.alikhver.model.repository.OrganisationRepository;
import com.alikhver.model.service.util.ServiceValidationHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrganisationServiceImpl implements OrganisationService {
    private final OrganisationRepository repository;
    private final ServiceValidationHelper validationHelper;

    @Override
    @Transactional(readOnly = true)
    public Optional<Organisation> get(Long organisationId) {
        validationHelper.validateForCorrectId(organisationId, "OrganisationId");
        return repository.findById(organisationId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long organisationId) {
        validationHelper.validateForCorrectId(organisationId, "OrganisationId");
        return repository.existsById(organisationId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Organisation> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        validationHelper.validateForCorrectString(name, "Organisation Name");
        return repository.existsOrganisationByName(name);
    }

    @Override
    public Organisation save(Organisation organisation) {
        validationHelper.validateOrganisation(organisation);
        return repository.save(organisation);
    }

    @Override
    public void delete(Long organisationId) {
        validationHelper.validateForCorrectId(organisationId, "OrganisationId");
        repository.deleteById(organisationId);
    }

    //TODO domain validation
}
