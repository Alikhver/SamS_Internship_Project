package com.alikhver.model.service;

import com.alikhver.model.entity.Organisation;
import com.alikhver.model.entity.User;
import com.alikhver.model.repository.OrganisationRepository;
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
public class OrganisationServiceImpl implements OrganisationService {
    private final OrganisationRepository repository;
    private final ValidationHelper validationHelper;

    @Override
    @Transactional(readOnly = true)
    public Optional<Organisation> getOrganisation(Long organisationId) {
        log.info("organisationService::get -> start");

        validationHelper.validateForCorrectId(organisationId, "OrganisationId");

        log.info("organisationService::get -> done");
        return repository.findById(organisationId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long organisationId) {
        log.info("organisationService::existsById -> start");

        validationHelper.validateForCorrectId(organisationId, "OrganisationId");

        log.info("organisationService::existsById -> done");
        return repository.existsById(organisationId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Organisation> getAll(Pageable pageable) {
        log.info("organisationService::getAll -> start");

        var organisations= repository.findAll(pageable);

        log.info("organisationService::getAll -> done");
        return organisations;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsOrganisationByName(String name) {
        log.info("organisationService::existsOrganisationByName -> start");

        validationHelper.validateForCorrectString(name, "Organisation Name");

        log.info("organisationService::existsOrganisationByName -> done");
        return repository.existsOrganisationByName(name);
    }

    @Override
    public Organisation saveOrganisation(Organisation organisation) {
        log.info("organisationService::saveOrganisation -> start");

        validateOrganisation(organisation);

        log.info("organisationService::saveOrganisation -> done");
        return repository.save(organisation);
    }

    @Override
    public void deleteOrganisation(Long organisationId) {
        log.info("organisationService::deleteOrganisation -> start");

        validationHelper.validateForCorrectId(organisationId, "OrganisationId");
        repository.deleteById(organisationId);

        log.info("organisationService::deleteOrganisation -> done");
    }

    private void validateOrganisation(Organisation organisation) {
        log.info("organisationService::validateOrganisation -> start");

        validationHelper.validateForCorrectString(organisation.getName(), "Organisation Name");
        validationHelper.validateForCorrectString(organisation.getDescription(), "Organisation Description");
        validateUser(organisation.getRedactor());
        if (organisation.getDateCreated() == null) organisation.setDateCreated(new Date());

        log.info("organisationService::validateOrganisation -> done");
    }

    private void validateUser(User user) {
        log.info("organisationService::validateUser -> start");

        validationHelper.validateForCorrectString(user.getLogin(), "User Login");
        validationHelper.validateForCorrectString(user.getPassword(), "User Password");
        Objects.requireNonNull(user.getRole());

        log.info("organisationService::validateUser -> done");
    }
}
