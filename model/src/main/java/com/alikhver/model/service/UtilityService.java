package com.alikhver.model.service;

import com.alikhver.model.entity.Utility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UtilityService {
    void saveUtility(Utility utility);

    boolean existsUtility(Utility utility);

    boolean existsUtility(Long utilityId);

    Optional<Utility> getUtility(Long utilityId);

    Page<Utility> getAllUtilitiesOfOrganisation(Long organisationId, Pageable pageable);

    List<Utility> getUtilitiesOfOrganisation(Long orgId);

    void deleteUtility(Long utilityId);

    boolean utilityAlreadyHasWorker(Long utilityId, Long workerId);

    void deleteAll();

    void deleteAllUtilitiesOfOrganisation(Long orgId);
}
