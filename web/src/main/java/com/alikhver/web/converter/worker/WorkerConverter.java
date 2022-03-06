package com.alikhver.web.converter.worker;

import com.alikhver.model.entity.Worker;
import com.alikhver.web.dto.worker.request.CreateWorkerRequest;
import com.alikhver.web.dto.worker.response.CreateWorkerResponse;
import com.alikhver.web.dto.worker.response.GetWorkerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WorkerConverter {
    private final CreateWorkerRequestToWorkerConverter createWorkerRequestToWorkerConverter;
    private final WorkerToCreateWorkerResponseConverter workerToCreateWorkerResponseConverter;
    private final WorkerToGetWorkerResponseConverter workerToGetWorkerResponseConverter;
    private final WorkersToListOfGetWorkerResponseConverter workersToListOfGetWorkerResponseConverter;

    public Worker mapToWorker(CreateWorkerRequest request) {
        return createWorkerRequestToWorkerConverter.convert(request);
    }

    public CreateWorkerResponse mapToCreateWorkerResponse(Worker worker) {
        return workerToCreateWorkerResponseConverter.convert(worker);
    }

    public GetWorkerResponse mapToGetWorkerResponse(Worker worker) {
        return workerToGetWorkerResponseConverter.convert(worker);
    }

    public List<GetWorkerResponse> mapToListOfGetWorkerResponse(List<Worker> workers) {
        return workersToListOfGetWorkerResponseConverter.convert(workers);
    }
}
