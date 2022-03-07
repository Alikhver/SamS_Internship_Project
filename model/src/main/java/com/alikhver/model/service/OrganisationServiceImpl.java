package com.alikhver.model.service;

import com.alikhver.model.entity.Organisation;
import com.alikhver.model.repository.OrganisationRepository;
import lombok.RequiredArgsConstructor;
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
    @Transactional(readOnly = true)
    public Page<Organisation> getAllOrganisations(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean organisationExistsByName(String name) {
        return repository.existsOrganisationByName(name);
    }

    @Override
    public Organisation createOrganisation(Organisation organisation) {
        Objects.requireNonNull(organisation.getName());
        Objects.requireNonNull(organisation.getDescription());
        Objects.requireNonNull(organisation.getRedactor());
        organisation.setDateCreated(new Date());
        return repository.save(organisation);
    }

    @Override
    public void updateOrganisation(Organisation organisation) {
        repository.save(organisation);
    }

    @Override
    public void deleteOrganisation(Long id) {
        repository.deleteById(id);
    }
}
