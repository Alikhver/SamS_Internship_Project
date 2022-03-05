package com.alikhver.model.service;

import com.alikhver.model.entity.Organisation;

import java.util.List;
import java.util.Optional;

public interface OrganisationService {
    Optional<Organisation> getOrganisation(long id);

    boolean organisationExistsById(Long id);

    List<Organisation> getAllOrganisations();

    boolean organisationExistsByName(String name);

    Organisation createOrganisation(Organisation organisation);

    void updateOrganisation(Organisation organisation);
}
