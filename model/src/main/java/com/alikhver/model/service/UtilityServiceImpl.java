package com.alikhver.model.service;

import com.alikhver.model.entity.Organisation;
import com.alikhver.model.entity.User;
import com.alikhver.model.entity.Utility;
import com.alikhver.model.repository.UtilityRepository;
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

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UtilityServiceImpl implements UtilityService {
    private final UtilityRepository repository;
    private final ValidationHelper validationHelper;

    @Override
    public void saveUtility(Utility utility) {
        log.info("saveUtility -> start");

        validateUtility(utility);

        log.info("saveUtility -> done");
        repository.save(utility);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsUtility(Utility utility) {
        log.info("existsUtility -> start");

        validateUtility(utility);

        log.info("existsUtility -> done");
        return repository.existsByNameAndDescriptionAndPriceAndOrganisationId(
                utility.getName(),
                utility.getDescription(),
                utility.getPrice(),
                utility.getOrganisation().getId()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsUtility(Long utilityId) {
        log.info("existsUtility -> start");

        validationHelper.validateForCorrectId(utilityId, "UtilityId");
        boolean exists = repository.existsById(utilityId);

        log.info("existsUtility -> done");
        return exists;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Utility> getUtility(Long utilityId) {
        log.info("getUtility -> start");

        validationHelper.validateForCorrectId(utilityId, "UtilityId");
        var utility = repository.findById(utilityId);

        log.info("getUtility -> done");
        return utility;

    }

    @Override
    @Transactional(readOnly = true)
    public Page<Utility> getAllUtilitiesOfOrganisation(Long organisationId, Pageable pageable) {
        log.info("getAllUtilitiesOfOrganisation -> start");

        validationHelper.validateForCorrectId(organisationId, "OrganisationId");
        var organisations = repository.findAllByOrganisationId(organisationId, pageable);

        log.info("getAllUtilitiesOfOrganisation -> done");
        return organisations;
    }

    @Override
    public void deleteUtility(Long utilityId) {
        log.info("deleteUtility -> start");

        validationHelper.validateForCorrectId(utilityId, "UtilityId");

        log.info("deleteUtility -> done");
        repository.deleteById(utilityId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean utilityAlreadyHasWorker(Long utilityId, Long workerId) {
        log.info("utilityAlreadyHasWorker -> start");

        boolean response = repository.existsByIdAndWorkersId(utilityId, workerId);

        log.info("utilityAlreadyHasWorker -> done");
        return response;
    }

    private void validateUtility(Utility utility) {
        log.info("validateUtility -> start");

        validationHelper.validateForCorrectString(utility.getName(), "Utility Name");
        validationHelper.validateForCorrectPrice(utility.getPrice(), "Utility Price");
        validationHelper.validateForCorrectString(utility.getDescription(), "Utility Description");
        validateOrganisation(utility.getOrganisation());

        log.info("validateUtility -> done");
    }

    private void validateOrganisation(Organisation organisation) {
        log.info("validateOrganisation -> start");
        validationHelper.validateForCorrectString(organisation.getName(), "Organisation Name");
        validationHelper.validateForCorrectString(organisation.getDescription(), "Organisation Description");
        validateUser(organisation.getRedactor());
        if (organisation.getDateCreated() == null) organisation.setDateCreated(new Date());

        log.info("validateOrganisation -> done");
    }

    private void validateUser(User user) {
        log.info("validateUser -> start");

        validationHelper.validateForCorrectString(user.getLogin(), "User Login");
        validationHelper.validateForCorrectString(user.getPassword(), "User Password");
        Objects.requireNonNull(user.getRole());

        log.info("validateUser -> done");
    }
}
