package com.alikhver.web.converter.worker;

import com.alikhver.model.entity.Worker;
import com.alikhver.web.dto.worker.request.CreateWorkerRequest;
import com.alikhver.web.dto.worker.response.CreateWorkerResponse;
import com.alikhver.web.dto.worker.response.GetWorkerResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class WorkerConverter {
    private final CreateWorkerRequestToWorkerConverter createWorkerRequestToWorkerConverter;
    private final WorkerToCreateWorkerResponseConverter workerToCreateWorkerResponseConverter;
    private final WorkerToGetWorkerResponseConverter workerToGetWorkerResponseConverter;
    private final PageOfWorkersToPageOfGetWorkerResponseConverter pageOfWorkersToPageOfGetWorkerResponseConverter;

    public Worker mapToWorker(CreateWorkerRequest request) {
        log.info("mapToWorker -> start");

        var response = createWorkerRequestToWorkerConverter.convert(request);

        log.info("mapToWorker -> done");
        return response;
    }

    public CreateWorkerResponse mapToCreateWorkerResponse(Worker worker) {
        log.info("mapToCreateWorkerResponse -> start");

        var response = workerToCreateWorkerResponseConverter.convert(worker);

        log.info("mapToCreateWorkerResponse -> done");
        return response;
    }

    public GetWorkerResponse mapToGetWorkerResponse(Worker worker) {
        log.info("mapToGetWorkerResponse -> start");

        var response = workerToGetWorkerResponseConverter.convert(worker);

        log.info("mapToGetWorkerResponse -> done");
        return response;
    }

    public Page<GetWorkerResponse> mapToPageOfGetWorkerResponse(Page<Worker> workers) {
        log.info("mapToPageOfGetWorkerResponse -> start");

        var response = pageOfWorkersToPageOfGetWorkerResponseConverter.convert(workers);

        log.info("mapToPageOfGetWorkerResponse -> done");
        return response;
    }
}
