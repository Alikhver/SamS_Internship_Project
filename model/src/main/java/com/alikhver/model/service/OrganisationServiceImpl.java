package com.alikhver.model.service;

import com.alikhver.model.entity.Organisation;
import com.alikhver.model.repository.OrganisationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrganisationServiceImpl implements OrganisationService {
    private final OrganisationRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Organisation getOrganisation(long id) {
        return repository.getById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean organisationExistsById(Long id) {
        return repository.existsById(id);
    }
}
