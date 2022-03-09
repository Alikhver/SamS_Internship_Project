package com.alikhver.model.service;

import com.alikhver.model.entity.Organisation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrganisationService {
    Optional<Organisation> get(Long organisationId);

    boolean existsById(Long organisationId);

    Page<Organisation> getAll(Pageable pageable);

    boolean existsByName(String name);

    Organisation save(Organisation organisation);

    void delete(Long organisationId);
}
