package com.alikhver.web.converter.worker;

import com.alikhver.model.entity.Worker;
import com.alikhver.web.dto.worker.request.CreateWorkerRequest;
import com.alikhver.web.dto.worker.response.CreateWorkerResponse;
import com.alikhver.web.dto.worker.response.GetWorkerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkerConverter {
    private final CreateWorkerRequestToWorkerConverter createWorkerRequestToWorkerConverter;
    private final WorkerToCreateWorkerResponseConverter workerToCreateWorkerResponseConverter;
    private final WorkerToGetWorkerResponseConverter workerToGetWorkerResponseConverter;
    private final PageOfWorkersToPageOfGetWorkerResponseConverter pageOfWorkersToPageOfGetWorkerResponseConverter;

    public Worker mapToWorker(CreateWorkerRequest request) {
        return createWorkerRequestToWorkerConverter.convert(request);
    }

    public CreateWorkerResponse mapToCreateWorkerResponse(Worker worker) {
        return workerToCreateWorkerResponseConverter.convert(worker);
    }

    public GetWorkerResponse mapToGetWorkerResponse(Worker worker) {
        return workerToGetWorkerResponseConverter.convert(worker);
    }

    public Page<GetWorkerResponse> mapToPageOfGetWorkerResponse(Page<Worker> workers) {
        return pageOfWorkersToPageOfGetWorkerResponseConverter.convert(workers);
    }
}
