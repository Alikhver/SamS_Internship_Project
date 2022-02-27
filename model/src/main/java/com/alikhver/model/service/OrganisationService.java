package com.alikhver.model.service;

import com.alikhver.model.entity.Organisation;

public interface OrganisationService {
    Organisation getOrganisation(long id);

    boolean organisationExistsById(Long id);
}
