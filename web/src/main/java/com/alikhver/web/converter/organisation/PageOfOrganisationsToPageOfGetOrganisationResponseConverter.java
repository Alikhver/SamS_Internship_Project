package com.alikhver.web.converter.organisation;

import com.alikhver.model.entity.Organisation;
import com.alikhver.web.dto.organisation.response.GetOrganisationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PageOfOrganisationsToPageOfGetOrganisationResponseConverter implements Converter<Page<Organisation>, Page<GetOrganisationResponse>> {
    private final OrganisationToGetOrganisationResponseConverter organisationToGetOrganisationResponseConverter;

    @Override
    public Page<GetOrganisationResponse> convert(Page<Organisation> organisations) {
        return organisations
                .map(organisationToGetOrganisationResponseConverter::convert);
    }
}
