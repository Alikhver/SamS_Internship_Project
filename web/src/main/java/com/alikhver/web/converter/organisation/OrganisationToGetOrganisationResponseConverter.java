package com.alikhver.web.converter.organisation;

import com.alikhver.model.entity.Organisation;
import com.alikhver.web.converter.utility.UtilityConverter;
import com.alikhver.web.converter.worker.WorkerConverter;
import com.alikhver.web.dto.organisation.response.GetOrganisationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrganisationToGetOrganisationResponseConverter implements Converter<Organisation, GetOrganisationResponse> {
    private final UtilityConverter utilityConverter;
    private final WorkerConverter workerConverter;

    @Override
    public GetOrganisationResponse convert(Organisation organisation) {
        return GetOrganisationResponse.builder()
                .id(organisation.getId())
                .name(organisation.getName())
                .description(organisation.getDescription())
                .address(organisation.getAddress())
                .utilities(organisation.getUtilities().stream()
                        .map(utilityConverter::mapToGetUtilityResponse)
                        .collect(Collectors.toList()))
                .workers(organisation.getWorkers().stream()
                        .map(workerConverter::mapToGetWorkerResponse).
                        collect(Collectors.toList()))
                .isActive(organisation.isActive())
                .dateCreated(organisation.getDateCreated())
                .build();
    }
}
