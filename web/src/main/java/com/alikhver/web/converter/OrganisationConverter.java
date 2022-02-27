package com.alikhver.web.converter;

import com.alikhver.model.entity.Organisation;
import com.alikhver.web.dto.organisation.response.GetOrganisationResponse;
import org.springframework.stereotype.Component;

@Component
public class OrganisationConverter {
    public GetOrganisationResponse convertOrganisationToGetOrganisationResponse(Organisation organisation) {
        return GetOrganisationResponse.builder()
                .name(organisation.getName())
                .description(organisation.getDescription())
                .address(organisation.getAddress())
                .utilities(organisation.getUtilities())
                .workers(organisation.getWorkers())
                .build();
    }
}
