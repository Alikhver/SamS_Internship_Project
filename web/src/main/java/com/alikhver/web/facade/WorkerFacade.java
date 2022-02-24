package com.alikhver.web.facade;

import com.alikhver.model.service.WorkerService;
import com.alikhver.web.converter.WorkerConverter;
import com.alikhver.web.dto.worker.response.GetWorkerResponse;
import org.springframework.stereotype.Service;

@Service
public class WorkerFacade {

    private WorkerService workerService;

    private WorkerConverter workerConverter;

    public GetWorkerResponse getWorker(String id) {

        return null;
    }
}
