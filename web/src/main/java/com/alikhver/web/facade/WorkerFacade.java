package com.alikhver.web.facade;

import com.alikhver.web.dto.utility.response.GetUtilityResponse;
import com.alikhver.web.dto.worker.request.CreateWorkerRequest;
import com.alikhver.web.dto.worker.request.UpdateWorkerRequest;
import com.alikhver.web.dto.worker.response.CreateWorkerResponse;
import com.alikhver.web.dto.worker.response.GetWorkerResponse;
import org.springframework.data.domain.Page;

public interface WorkerFacade {
    CreateWorkerResponse createWorker(CreateWorkerRequest request);

    GetWorkerResponse getWorkerById(Long id);

    void updateWorker(Long id, UpdateWorkerRequest request);

    void deleteWorker(Long id);

    void addUtility(Long id, Long utilityId);

    void deleteUtility(Long id, Long utilityId);

    Page<GetUtilityResponse> getUtilitiesOfWorker(Long workerId, int page, int size);
}
