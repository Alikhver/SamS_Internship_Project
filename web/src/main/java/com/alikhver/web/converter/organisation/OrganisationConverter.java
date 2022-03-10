package com.alikhver.web.converter.organisation;

import com.alikhver.model.entity.Organisation;
import com.alikhver.model.entity.User;
import com.alikhver.web.dto.organisation.request.CreateOrganisationRequest;
import com.alikhver.web.dto.organisation.response.CreateOrganisationResponse;
import com.alikhver.web.dto.organisation.response.GetOrganisationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrganisationConverter {
    private final OrganisationToGetOrganisationResponseConverter organisationToGetOrganisationResponseConverter;
    private final PageOfOrganisationsToPageOfGetOrganisationResponseConverter pageOfOrganisationsToPageOfGetOrganisationResponseConverter;
    private final CreateOrganisationRequestToOrganisationConverter createOrganisationRequestToOrganisationConverter;
    private final CreateOrganisationRequestToRedactorConverter createOrganisationRequestToRedactorConverter;
    private final OrganisationToCreateOrganisationResponseConverter organisationToCreateOrganisationResponseConverter;

    public GetOrganisationResponse mapToGetOrganisationResponse(Organisation organisation) {
        log.info("mapToGetOrganisationResponse -> start");

        var response = organisationToGetOrganisationResponseConverter.convert(organisation);

        log.info("mapToGetOrganisationResponse -> done");
        return response;
    }

    public Page<GetOrganisationResponse> mapToPageOfGetOrganisationResponse(Page<Organisation> organisations) {
        log.info("mapToPageOfGetOrganisationResponse -> start");

        var response = pageOfOrganisationsToPageOfGetOrganisationResponseConverter.convert(organisations);

        log.info("mapToPageOfGetOrganisationResponse -> done");
        return response;
    }

    public Organisation mapToOrganisation(CreateOrganisationRequest request) {
        log.info("mapToOrganisation -> start");

        var response = createOrganisationRequestToOrganisationConverter.convert(request);

        log.info("mapToOrganisation -> done");
        return response;
    }

    public User mapToRedactor(CreateOrganisationRequest request) {
        log.info("mapToRedactor -> start");

        var response = createOrganisationRequestToRedactorConverter.convert(request);

        log.info("mapToRedactor -> done");
        return response;
    }

    public CreateOrganisationResponse mapToCreateOrganisationResponse(Organisation organisation) {
        log.info("mapToCreateOrganisationResponse -> start");

        var response = organisationToCreateOrganisationResponseConverter.convert(organisation);

        log.info("mapToCreateOrganisationResponse -> done");
        return response;
    }
}
