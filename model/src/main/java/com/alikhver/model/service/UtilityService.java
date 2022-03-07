package com.alikhver.model.service;

import com.alikhver.model.entity.Utility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UtilityService {
    void save(Utility utility);

    boolean utilityExists(Utility utility);

    boolean utilityExists(Long id);

    Optional<Utility> getUtility(Long id);

    Page<Utility> getAllUtilitiesOfOrganisation(Long organisationId, Pageable pageable);

    void deleteUtility(Long id);
}
