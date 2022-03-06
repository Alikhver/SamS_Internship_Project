package com.alikhver.web.converter.worker;

import com.alikhver.model.entity.Worker;
import com.alikhver.web.dto.worker.request.CreateWorkerRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CreateWorkerRequestToWorkerConverter implements Converter<CreateWorkerRequest, Worker> {
    @Override
    public Worker convert(CreateWorkerRequest request) {
        return Worker.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .description(request.getDescription())
                .build();
    }
}
