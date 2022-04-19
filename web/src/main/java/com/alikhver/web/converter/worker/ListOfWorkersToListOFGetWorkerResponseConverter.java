package com.alikhver.web.converter.worker;

import com.alikhver.model.entity.Worker;
import com.alikhver.web.dto.worker.response.GetWorkerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ListOfWorkersToListOFGetWorkerResponseConverter implements Converter<List<Worker>, List<GetWorkerResponse>> {
    private final WorkerToGetWorkerResponseConverter workerToGetWorkerResponseConverter;

    @Override
    public List<GetWorkerResponse> convert(List<Worker> source) {
        return source.stream().map(workerToGetWorkerResponseConverter::convert).collect(Collectors.toList());
    }
}
