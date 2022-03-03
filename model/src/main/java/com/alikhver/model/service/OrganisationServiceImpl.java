package com.alikhver.model.service;

import com.alikhver.model.entity.Organisation;
import com.alikhver.model.repository.OrganisationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrganisationServiceImpl implements OrganisationService {
    private final OrganisationRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Organisation> getOrganisation(long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean organisationExistsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public List<Organisation> getOrganisations() {
        return repository.findAll();
    }

    @Override
    public boolean organisationExistsByName(String name) {
        return repository.existsOrganisationByName(name);
    }
}
