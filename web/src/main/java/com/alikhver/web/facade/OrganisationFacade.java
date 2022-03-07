package com.alikhver.web.facade;

import com.alikhver.web.dto.organisation.request.CreateOrganisationRequest;
import com.alikhver.web.dto.organisation.request.UpdateOrganisationRequest;
import com.alikhver.web.dto.organisation.response.CreateOrganisationResponse;
import com.alikhver.web.dto.organisation.response.GetOrganisationResponse;
import com.alikhver.web.dto.utility.response.GetUtilityResponse;
import com.alikhver.web.dto.worker.response.GetWorkerResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrganisationFacade {
    GetOrganisationResponse getOrganisation(Long id);

    CreateOrganisationResponse createOrganisation(CreateOrganisationRequest request);

    List<GetOrganisationResponse> getOrganisations();

    void updateOrganisation(Long id, UpdateOrganisationRequest request);

    void deleteOrganisation(Long id);

    void suspendOrganisation(Long id);

    void launchOrganisation(Long id);

    Page<GetWorkerResponse> getWorkers(Long id, int offset, int size);

    List<GetUtilityResponse> getUtilitiesOfOrganisation(Long id);
}
