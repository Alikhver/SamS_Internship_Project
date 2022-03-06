package com.alikhver.model.service;

import com.alikhver.model.entity.Utility;

public interface UtilityService {
    void save(Utility utility);

    boolean utilityExists(Utility utility);
}
