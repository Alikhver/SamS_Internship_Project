package com.alikhver.web.facade;

import com.alikhver.model.service.WorkerService;
import com.alikhver.web.converter.WorkerConverter;
import com.alikhver.web.dto.worker.response.GetWorkerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkerFacade {

    private final WorkerService workerService;

    private final WorkerConverter workerConverter;

    public GetWorkerResponse getWorker(Long id) {

        return null;
    }
}
