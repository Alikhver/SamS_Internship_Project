package com.alikhver.web.converter.organisation;

import com.alikhver.model.entity.Organisation;
import com.alikhver.model.entity.User;
import com.alikhver.web.dto.organisation.request.CreateOrganisationRequest;
import com.alikhver.web.dto.organisation.response.CreateOrganisationResponse;
import com.alikhver.web.dto.organisation.response.GetOrganisationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrganisationConverter {
    private final OrganisationToGetOrganisationResponseConverter organisationToGetOrganisationResponseConverter;
    private final OrganisationsToListOfGetOrganisationResponseConverter organisationsToListOfGetOrganisationResponseConverter;
    private final CreateOrganisationRequestToOrganisationConverter createOrganisationRequestToOrganisationConverter;
    private final CreateOrganisationRequestToRedactorConverter createOrganisationRequestToRedactorConverter;
    private final OrganisationToCreateOrganisationResponseConverter organisationToCreateOrganisationResponseConverter;

    public GetOrganisationResponse mapToGetOrganisationResponse(Organisation organisation) {
        return organisationToGetOrganisationResponseConverter.convert(organisation);
    }

    public List<GetOrganisationResponse> mapToListOfGetOrganisationResponse(List<Organisation> organisations) {
        return organisationsToListOfGetOrganisationResponseConverter.convert(organisations);
    }

    public Organisation mapToOrganisation(CreateOrganisationRequest request) {
        return createOrganisationRequestToOrganisationConverter.convert(request);
    }

    public User mapToRedactor(CreateOrganisationRequest request) {
        return createOrganisationRequestToRedactorConverter.convert(request);
    }

    public CreateOrganisationResponse mapToCreateOrganisationResponse(Organisation organisation) {
        return organisationToCreateOrganisationResponseConverter.convert(organisation);
    }
}
