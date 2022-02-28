package com.alikhver.web.facade;

import com.alikhver.model.entity.Organisation;
import com.alikhver.model.service.OrganisationService;
import com.alikhver.web.converter.OrganisationConverter;
import com.alikhver.web.dto.organisation.request.CreateOrganisationRequest;
import com.alikhver.web.dto.organisation.response.CreateOrganisationResponse;
import com.alikhver.web.dto.organisation.response.GetOrganisationResponse;
import com.alikhver.web.exeption.organisation.NoOrganisationFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganisationFacadeImpl implements OrganisationFacade {
    private final OrganisationService organisationService;
    private final OrganisationConverter organisationConverter;

    @Override
    public GetOrganisationResponse getOrganisation(Long id) throws NoOrganisationFoundException {
        if (organisationService.organisationExistsById(id)) {
            Organisation organisation = organisationService.getOrganisation(id);
            return organisationConverter.convertOrganisationToGetOrganisationResponse(organisation);
        } {
            throw new NoOrganisationFoundException(
                    "No Organisation with id = " + id + " found"
            );
        }
    }

    @Override
    public CreateOrganisationResponse createOrganisation(CreateOrganisationRequest request) {
        // TODO create user ?
        return null;
    }
}
