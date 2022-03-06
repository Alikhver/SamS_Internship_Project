package com.alikhver.web.converter.organisation;

import com.alikhver.model.entity.Organisation;
import com.alikhver.web.dto.organisation.response.CreateOrganisationResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrganisationToCreateOrganisationResponseConverter implements Converter<Organisation, CreateOrganisationResponse> {
    @Override
    public CreateOrganisationResponse convert(Organisation organisation) {
        return CreateOrganisationResponse.builder()
                .id(organisation.getId())
                .name(organisation.getName())
                .description(organisation.getDescription())
                .address(organisation.getAddress())
                .build();
    }
}
