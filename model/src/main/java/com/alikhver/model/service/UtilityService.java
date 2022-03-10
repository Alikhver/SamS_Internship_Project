package com.alikhver.model.service;

import com.alikhver.model.entity.Utility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UtilityService {
    void saveUtility(Utility utility);

    boolean existsUtility(Utility utility);

    boolean existsUtility(Long utilityId);

    Optional<Utility> getUtility(Long utilityId);

    Page<Utility> getAllUtilitiesOfOrganisation(Long organisationId, Pageable pageable);

    void deleteUtility(Long utilityId);
}
