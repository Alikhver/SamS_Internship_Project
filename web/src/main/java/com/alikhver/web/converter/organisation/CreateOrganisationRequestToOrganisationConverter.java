package com.alikhver.web.converter.organisation;

import com.alikhver.model.entity.Organisation;
import com.alikhver.web.dto.organisation.request.CreateOrganisationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateOrganisationRequestToOrganisationConverter implements Converter<CreateOrganisationRequest, Organisation> {
    @Override
    public Organisation convert(CreateOrganisationRequest request) {
        return Organisation.builder()
                .name(request.getName())
                .address(request.getAddress())
                .description(request.getDescription())
                .build();
    }
}
