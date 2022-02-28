package com.alikhver.web.facade;

import com.alikhver.web.dto.organisation.request.CreateOrganisationRequest;
import com.alikhver.web.dto.organisation.response.CreateOrganisationResponse;
import com.alikhver.web.dto.organisation.response.GetOrganisationResponse;

public interface OrganisationFacade {
    GetOrganisationResponse getOrganisation(Long id);

    CreateOrganisationResponse createOrganisation(CreateOrganisationRequest request);
}
