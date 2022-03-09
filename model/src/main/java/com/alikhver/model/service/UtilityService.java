package com.alikhver.model.service;

import com.alikhver.model.entity.Utility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UtilityService {
    void save(Utility utility);

    boolean exists(Utility utility);

    boolean exists(Long utilityId);

    Optional<Utility> get(Long utilityId);

    Page<Utility> getAllUtilitiesOfOrganisation(Long organisationId, Pageable pageable);

    void delete(Long utilityId);
}
