package com.alikhver.web.converter;

import com.alikhver.model.entity.Worker;
import com.alikhver.web.dto.worker.request.CreateWorkerRequest;
import com.alikhver.web.dto.worker.response.CreateWorkerResponse;
import org.springframework.stereotype.Component;

@Component
public class WorkerConverter {
    public Worker mapToWorker(CreateWorkerRequest request) {
        return Worker.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .description(request.getDescription())

                .build();
    }

    public CreateWorkerResponse mapToCreateWorkerResponse(Worker worker) {
        return CreateWorkerResponse.builder()
                .id(worker.getId())
                .firstName(worker.getFirstName())
                .lastName(worker.getLastName())
                .description(worker.getDescription())
                .organisationId(worker.getOrganisation().getId())
                .build();
    }
}
