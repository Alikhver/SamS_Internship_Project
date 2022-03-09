package com.alikhver.model.service;

import com.alikhver.model.entity.Utility;
import com.alikhver.model.repository.UtilityRepository;
import com.alikhver.model.service.service_validation_helper.ServiceValidationHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UtilityServiceImpl implements UtilityService {
    private final UtilityRepository repository;
    private final ServiceValidationHelper validationHelper;

    @Override
    public void save(Utility utility) {
        validationHelper.validateUtility(utility);
        repository.save(utility);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(Utility utility) {
        validationHelper.validateUtility(utility);
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
        validationHelper.validateForCorrectId(utilityId, "UtilityId");
        return repository.existsById(utilityId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Utility> get(Long utilityId) {
        validationHelper.validateForCorrectId(utilityId, "UtilityId");
        return repository.findById(utilityId);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<Utility> getAllUtilitiesOfOrganisation(Long organisationId, Pageable pageable) {
        validationHelper.validateForCorrectId(organisationId, "OrganisationId");
        return repository.findAllByOrganisationId(organisationId, pageable);
    }

    @Override
    public void delete(Long utilityId) {
        validationHelper.validateForCorrectId(utilityId, "UtilityId");
        repository.deleteById(utilityId);

    }
}
