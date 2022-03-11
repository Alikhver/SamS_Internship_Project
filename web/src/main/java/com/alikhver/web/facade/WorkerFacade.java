package com.alikhver.web.facade;

import com.alikhver.web.dto.worker.request.CreateWorkerRequest;
import com.alikhver.web.dto.worker.request.UpdateWorkerRequest;
import com.alikhver.web.dto.worker.response.CreateWorkerResponse;
import com.alikhver.web.dto.worker.response.GetWorkerResponse;

public interface WorkerFacade {
    CreateWorkerResponse createWorker(CreateWorkerRequest request);
    GetWorkerResponse getWorkerById(Long id);
    void updateWorker(Long id, UpdateWorkerRequest request);
    void deleteWorker(Long id);
    void addUtility(Long id, Long utilityId);
}
