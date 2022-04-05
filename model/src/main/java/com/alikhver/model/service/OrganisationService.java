package com.alikhver.model.service;

import com.alikhver.model.entity.Organisation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrganisationService {
    Optional<Organisation> getOrganisation(Long organisationId);

    boolean existsById(Long organisationId);

    Page<Organisation> getAll(Pageable pageable);

    List<Organisation> getAll();

    boolean existsOrganisationByName(String name);

    Organisation saveOrganisation(Organisation organisation);

    void deleteOrganisation(Long organisationId);

    void deleteAll();
}
