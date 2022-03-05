package com.alikhver.web.facade;

import com.alikhver.web.dto.worker.request.CreateWorkerRequest;
import com.alikhver.web.dto.worker.response.CreateWorkerResponse;
import com.alikhver.web.dto.worker.response.GetWorkerResponse;

public interface WorkerFacade {
    CreateWorkerResponse createWorker(CreateWorkerRequest request);

    GetWorkerResponse getWorkerById(Long id);
}
