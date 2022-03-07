package com.alikhver.model.service;

import com.alikhver.model.entity.Organisation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrganisationService {
    Optional<Organisation> getOrganisation(long id);

    boolean organisationExistsById(Long id);

    Page<Organisation> getAllOrganisations(Pageable pageable);

    boolean organisationExistsByName(String name);

    Organisation createOrganisation(Organisation organisation);

    void updateOrganisation(Organisation organisation);

    void deleteOrganisation(Long id);
}
