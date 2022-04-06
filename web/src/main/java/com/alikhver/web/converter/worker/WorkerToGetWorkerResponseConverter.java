package com.alikhver.web.converter.worker;

import com.alikhver.model.entity.Worker;
import com.alikhver.web.dto.worker.response.GetWorkerResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class WorkerToGetWorkerResponseConverter implements Converter<Worker, GetWorkerResponse> {
    @Override
    public GetWorkerResponse convert(Worker worker) {
        boolean hasUtilities = worker.getUtilities().size() != 0;

        return GetWorkerResponse.builder()
                .id(worker.getId())
                .firstName(worker.getFirstName())
                .lastName(worker.getLastName())
                .description(worker.getDescription())
                .organisationId(worker.getOrganisation().getId())
                .hasUtilities(hasUtilities)
                .build();
    }
}
