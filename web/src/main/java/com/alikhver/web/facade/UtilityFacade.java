package com.alikhver.web.facade;

import com.alikhver.web.dto.utility.request.CreateUtilityRequest;
import com.alikhver.web.dto.utility.request.UpdateUtilityRequest;
import com.alikhver.web.dto.utility.response.CreateUtilityResponse;
import com.alikhver.web.dto.utility.response.GetUtilityResponse;
import com.alikhver.web.dto.worker.response.GetWorkerResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UtilityFacade {
    CreateUtilityResponse createUtility(CreateUtilityRequest request);

    GetUtilityResponse getUtility(Long id);

    void updateUtility(Long id, UpdateUtilityRequest request);

    void deleteUtility(Long id);

    Page<GetWorkerResponse> getWorkersOfUtility(Long utilityId, int page, int size);

    List<GetWorkerResponse> getWorkersOfUtility(Long utilityId);
}
