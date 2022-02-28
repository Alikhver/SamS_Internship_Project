package com.alikhver.model.service;

import com.alikhver.model.entity.Organisation;

import java.util.Optional;

public interface OrganisationService {
    Optional<Organisation> getOrganisation(long id);

    boolean organisationExistsById(Long id);
}
