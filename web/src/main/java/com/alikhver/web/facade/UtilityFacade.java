package com.alikhver.web.facade;

import com.alikhver.web.dto.utility.request.CreateUtilityRequest;
import com.alikhver.web.dto.utility.request.UpdateUtilityRequest;
import com.alikhver.web.dto.utility.response.CreateUtilityResponse;
import com.alikhver.web.dto.utility.response.GetUtilityResponse;

public interface UtilityFacade {
    CreateUtilityResponse createUtility(CreateUtilityRequest request);

    GetUtilityResponse getUtility(Long id);

    void updateUtility(Long id, UpdateUtilityRequest request);

    void deleteUtility(Long id);
}
