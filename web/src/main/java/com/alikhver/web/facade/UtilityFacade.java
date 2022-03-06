package com.alikhver.web.facade;

import com.alikhver.web.dto.utility.request.CreateUtilityRequest;
import com.alikhver.web.dto.utility.response.CreateUtilityResponse;

public interface UtilityFacade {
    CreateUtilityResponse createUtility(CreateUtilityRequest request);
}
