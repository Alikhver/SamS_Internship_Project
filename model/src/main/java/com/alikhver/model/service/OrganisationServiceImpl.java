package com.alikhver.model.service;

import com.alikhver.model.entity.Organisation;
import com.alikhver.model.repository.OrganisationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrganisationServiceImpl implements OrganisationService {

    private OrganisationRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Organisation getOrganisation(long id) {
        return repository.getById(id);
    }
}
