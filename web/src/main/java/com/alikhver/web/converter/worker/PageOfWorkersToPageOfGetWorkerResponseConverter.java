package com.alikhver.web.converter.worker;

import com.alikhver.model.entity.Worker;
import com.alikhver.web.dto.worker.response.GetWorkerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PageOfWorkersToPageOfGetWorkerResponseConverter implements Converter<Page<Worker>, Page<GetWorkerResponse>> {
    private final WorkerToGetWorkerResponseConverter workerToGetWorkerResponseConverter;

    @Override
    public Page<GetWorkerResponse> convert(Page<Worker> workers) {
        return workers.map(workerToGetWorkerResponseConverter::convert);
    }
}
