package com.alikhver.web.converter.organisation;

import com.alikhver.model.entity.Organisation;
import com.alikhver.web.dto.organisation.response.GetOrganisationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrganisationsToListOfGetOrganisationResponseConverter implements Converter<List<Organisation>, List<GetOrganisationResponse>> {
    private final OrganisationToGetOrganisationResponseConverter organisationToGetOrganisationResponseConverter;

    @Override
    public List<GetOrganisationResponse> convert(List<Organisation> organisations) {
        return organisations.stream()
                .map(organisationToGetOrganisationResponseConverter::convert)
                .collect(Collectors.toList());
    }
}
