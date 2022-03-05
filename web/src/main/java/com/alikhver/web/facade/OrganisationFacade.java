package com.alikhver.web.facade;

import com.alikhver.web.dto.organisation.request.CreateOrganisationRequest;
import com.alikhver.web.dto.organisation.request.UpdateOrganisationRequest;
import com.alikhver.web.dto.organisation.response.CreateOrganisationResponse;
import com.alikhver.web.dto.organisation.response.GetOrganisationResponse;

import java.util.List;

public interface OrganisationFacade {
    GetOrganisationResponse getOrganisation(Long id);

    CreateOrganisationResponse createOrganisation(CreateOrganisationRequest request);

    List<GetOrganisationResponse> getOrganisations();

    void updateOrganisation(Long id, UpdateOrganisationRequest request);
}
