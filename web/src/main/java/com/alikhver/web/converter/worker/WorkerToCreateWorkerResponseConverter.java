package com.alikhver.web.converter.worker;

import com.alikhver.model.entity.Worker;
import com.alikhver.web.dto.worker.response.CreateWorkerResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class WorkerToCreateWorkerResponseConverter implements Converter<Worker, CreateWorkerResponse> {
    @Override
    public CreateWorkerResponse convert(Worker worker) {
        return CreateWorkerResponse.builder()
                .id(worker.getId())
                .firstName(worker.getFirstName())
                .lastName(worker.getLastName())
                .description(worker.getDescription())
                .organisationId(worker.getOrganisation().getId())
                .build();
    }
}
