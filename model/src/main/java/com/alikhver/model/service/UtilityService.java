package com.alikhver.model.service;

import com.alikhver.model.entity.Utility;

import java.util.List;
import java.util.Optional;

public interface UtilityService {
    void save(Utility utility);

    boolean utilityExists(Utility utility);

    Optional<Utility> getUtility(Long id);

    List<Utility> getAllUtilitiesOfOrganisation(Long id);
}
