package com.alikhver.model.service;

import com.alikhver.model.repository.OrganisationRepository;
import com.alikhver.model.entity.Organisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganisationServiceImpl implements OrganisationService {

    @Autowired
    private OrganisationRepository repository;

    @Override
    public Organisation getOrganisation(long id) {
        return repository.getById(id);
    }
}
